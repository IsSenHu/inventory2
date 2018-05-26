package com.husen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.husen.dao.*;
import com.husen.enums.OrderStatu;
import com.husen.model.*;
import com.husen.service.OrderService;
import com.husen.service.rabbitmq.AmqpService;
import com.husen.util.GlobalVar;
import com.husen.vo.FindOrder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 11785
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);
    @Autowired
    private StoresDao storesDao;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private LogisCompanyDao logisCompanyDao;
    @Autowired
    private LogisticsDao logisticsDao;
    @Autowired
    private LogisticsRecordDao logisticsRecordDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private AmqpService amqpService;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;
    @Value("${rabbitmq.push.deliver.key}")
    private String deliverQueueKey;

    @Override
    public Page<Order> findOrder1(Integer currentPage, Boss boss, FindOrder findOrder) {
        Stores stores = null;
        if(boss.getStores() == null){
            stores = storesDao.findByBossBossId(boss.getBossId());
        }else {
            stores = boss.getStores();
        }
        Specification<Order> specification = where(findOrder, stores.getStoresId());
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "orderId");
        Sort sort = new Sort(order);
        //page:index是从0开始的，不是从1开始的
        Pageable pageable = new PageRequest(currentPage - 1, 10, sort);
        Page<Order> page = orderDao.findAll(specification, pageable);
        page.getContent().stream().forEach(x -> {
            List<Item> items = itemRepository.findAllByOrder_OrderId(x.getOrderId());
            x.setItems(items);
        });
        return page;
    }

    @Override
    public String deleteOrder(Integer orderId) {
        Order order = orderDao.findById(orderId).get();
        order.setDel(GlobalVar.ORDER_ISDELETE);
        orderDao.save(order);
        return order.getId();
    }

    @Override
    public String deliver(Integer orderId) {
        Order order = orderDao.findById(orderId).get();
        amqpService.sendQueue(exchangeName, deliverQueueKey, JSONObject.toJSONString(order));
        order.setStatu(OrderStatu.SURED.getValue());
        orderDao.save(order);
        return order.getId();
    }

    @Override
    public void then(Message message) {
        /**
         * 1，生成物流信息，为已出库（加入快递目前只支持顺丰）
         * 2，修改订单状态为已发货
         * */
        String orderId = new String(message.getBody());
        LOGGER.info("接收到发货的订单ID为:{}", orderId);
        Order order = orderDao.findById(orderId);
        Logistics logistics = new Logistics();
        //地址
        logistics.setAddress(addressDao.findById(order.getAddress().getAddressId()).get());
        //物流公司
        logistics.setCompany(logisCompanyDao.findById(1).get());
        //快递单号
        logistics.setExpressNumber(expressNumber());
        //物流状态
        logistics.setStatu(GlobalVar.LOGISTICS_START);
        //物流记录
        LogisticsRecord logisticsRecord = new LogisticsRecord();
        //时间
        logisticsRecord.setTime(new Date());
        //内容
        logisticsRecord.setContent("物品已出库，等待揽件员揽收");
        //保存物流信息
        logistics = logisticsDao.save(logistics);
        logisticsRecord.setLogistics(logistics);
        //保存物流记录
        logisticsRecordDao.save(logisticsRecord);
        //修改订单状态
        order.setStatu(OrderStatu.PAYED_AND_DELIVERED.getValue());
        //订单的物流信息
        order.setLogistics(logistics);
        //保存
        orderDao.save(order);
    }

    @Override
    public Order findById(String orderId) {
        return orderDao.findById(orderId);
    }

    private Specification<Order> where(final FindOrder findOrder, final Integer storesId){
        return new Specification<Order>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //订单状态
                if(findOrder.getOrderStatu() != null){
                    predicates.add(criteriaBuilder.equal(root.<Integer>get("statu"), findOrder.getOrderStatu()));
                }
                //订单编号
                if(StringUtils.isNotBlank(findOrder.getOrderId())){
                    predicates.add(criteriaBuilder.equal(root.<String>get("id"), findOrder.getOrderId()));
                }
                //客户id
                if(findOrder.getUserId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Integer>get("uid"), findOrder.getUserId()));
                }
                //必须是本店铺的
                if(storesId != null){
                    predicates.add(criteriaBuilder.equal(root.<Integer>get("storeId"), storesId));
                }
                predicates.add(criteriaBuilder.equal(root.<String>get("del"), GlobalVar.ORDER_NODELETE));
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }
    /**
     * 生成唯一的快递单号
     * @return
     */
    public String expressNumber(){
        return UUID.randomUUID().toString();
    }
}
