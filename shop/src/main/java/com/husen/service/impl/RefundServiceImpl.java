package com.husen.service.impl;

import com.husen.dao.*;
import com.husen.enums.OrderStatu;
import com.husen.enums.RefundStatu;
import com.husen.model.*;
import com.husen.service.RefundService;
import com.husen.util.GlobalVar;
import com.husen.util.JsonResult;
import com.husen.util.Page;
import com.husen.vo.RefundVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author husen
 */
@Service
@Transactional
public class RefundServiceImpl implements RefundService {
    @Autowired
    private RefundDao refundDao;
    @Autowired
    private StoresDao storesDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private BalanceDetailDao balanceDetailDao;
    @Autowired
    private UserDao userDao;
    private static final Logger LOGGER = LogManager.getLogger(RefundServiceImpl.class);

    @Override
    public JsonResult refund(Integer refundId) {
        /**
         * 1，修改退款申请的状态
         * 2，将退款存进余额里面
         * 3，若该订单中全是已退款的条目，则将订单置为第7状态
         * 4，否则重新计算该订单的价格
         * */
        Refund refund = refundDao.findById(refundId).get();
        if (refund.getStatu().equals(RefundStatu.SUCCESS.getValue())){
            return new JsonResult("400", "forbidden", null);
        }else {
            //1，修改退款申请的状态
            refund.setStatu(RefundStatu.SUCCESS.getValue());
            refundDao.save(refund);
            //2，将退款存进余额里面
            Integer userId = refund.getUserId();
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
            Sort sort = new Sort(order);
            List<BalanceDetail> details = balanceDetailDao.findAllByUser_UserId(userId, sort);
            if(details != null && details.size() > 0){
                //根据时间进行降序，所以第一个就为最近保存的余额明细
                BalanceDetail balanceDetail = details.get(0);
                //获取现在的余额
                Double balanceMoney = balanceDetail.getBalanceMoney();
                //应该变化的金额
                Double changeMoney = Double.valueOf(refund.getRefundMoney());
                //计算变化过后的金额，保留2位小数，四舍五入
                BigDecimal newMoney = new BigDecimal(balanceMoney).add(new BigDecimal(changeMoney)).setScale(2, BigDecimal.ROUND_HALF_UP);
                //创建一条新的余额明细
                BalanceDetail newDetail = new BalanceDetail();
                newDetail.setBalanceMoney(newMoney.doubleValue());
                newDetail.setChangeMoney(changeMoney);
                newDetail.setContent("退款");
                newDetail.setCreateTime(new Date());
                newDetail.setUser(userDao.findById(userId).get());
                balanceDetailDao.save(newDetail);
            }else {
                //账户的钱包还没有被初始化，直接进行初始化，并且赋值
                BalanceDetail newDetail = new BalanceDetail();
                newDetail.setUser(userDao.findById(userId).get());
                newDetail.setCreateTime(new Date());
                newDetail.setContent("退款");
                newDetail.setChangeMoney(Double.valueOf(refund.getRefundMoney()));
                newDetail.setBalanceMoney(newDetail.getChangeMoney());
                balanceDetailDao.save(newDetail);
            }
            //3，若该订单中全是已退款的条目，则将订单置为第7状态
            List<Refund> refunds = refundDao.findAllByOrderId(refund.getOrderId());
            List<Refund> refundList = refunds.stream().filter(x -> !x.getStatu().equals(RefundStatu.SUCCESS.getValue())).collect(Collectors.toList());
            if(refundList == null || refundList.size() == 0){
                //说明订单中全是已退款的条目
                Order userOrder = orderDao.findById(refund.getOrderId()).get();
                userOrder.setStatu(OrderStatu.REFUND_ALL.getValue());
                orderDao.save(userOrder);
            }else {
                //4，否则重新计算该订单的价格
                Order userOrder = orderDao.findById(refund.getOrderId()).get();
                BigDecimal totalMoney = new BigDecimal(0.00);
                BigDecimal postage = new BigDecimal(0.00);
                for(Refund r : refundList){
                    Integer itemId = r.getItemId();
                    Item item = itemDao.findById(itemId).get();
                    if(item.getIsFreight().equals(GlobalVar.HAVE_POSTAGE)){
                        userOrder.setIsFreight(GlobalVar.HAVE_POSTAGE);
                    }
                    totalMoney = totalMoney.add(new BigDecimal(item.getTotalMoney())).add(new BigDecimal(item.getFreight()));
                    postage = postage.add(new BigDecimal(item.getFreight()));
                }
                userOrder.setTotalMoney(totalMoney.setScale(2).doubleValue());
                userOrder.setFreight(postage.setScale(2).doubleValue());
                orderDao.save(userOrder);
            }
        }
        return new JsonResult("200", "success", null);
    }

    @Override
    public Page<RefundVO> refunds(Boss boss, Integer userId, Integer currentPage) {
        Stores stores = boss.getStores() == null ? storesDao.findByBossBossId(boss.getBossId()) : boss.getStores();
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "refundId");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(currentPage - 1, 12, sort);
        org.springframework.data.domain.Page<Refund> page = null;
        if(userId != null){
            page = refundDao.findAllByStoresIdAndUserIdAndStatu(stores.getStoresId(), userId, RefundStatu.WAIT.getValue(), pageable);
        }else {
            page = refundDao.findAllByStoresIdAndStatu(stores.getStoresId(), RefundStatu.WAIT.getValue(), pageable);
        }
        return voPage(page);
    }

    /**
     * 将po转换为vo
     * @param page
     * @return
     */
    private Page<RefundVO> voPage(org.springframework.data.domain.Page<Refund> page) {
        Page<RefundVO> voPage = new Page<>();
        voPage.setTotalPage(page.getTotalPages());
        voPage.setPageSize(page.getSize());
        voPage.setCurrentPage(page.getNumber() + 1);
        voPage.setRowsTotal(((Long)page.getTotalElements()).intValue());
        List<RefundVO> list = new ArrayList<>();
        page.getContent().stream().forEach(x -> {
            RefundVO refundVO = new RefundVO();
            refundVO.setRefundId(x.getRefundId());
            refundVO.setOrderId(x.getOrderId());
            Order order = orderDao.findById(x.getOrderId()).get();
            refundVO.setId(order.getId());
            refundVO.setDealTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getPayTime()));
            Item item = itemDao.findById(x.getItemId()).get();
            refundVO.setDescription(x.getDescription());
            refundVO.setGoodName(item.getGood().getGoodName());
            refundVO.setItemId(x.getItemId());
            refundVO.setIsPostage(item.getIsFreight());
            refundVO.setMoney(new BigDecimal(item.getUnitPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            refundVO.setNumber(item.getNumber());
            Good good = item.getGood();
            String pisc = good.getPisc();
            String pic = StringUtils.isNotBlank(pisc) ? pisc.split("connectionRegex")[0] : "";
            refundVO.setPic(pic);
            refundVO.setPostage(item.getFreight() != null ? new BigDecimal(item.getFreight()).setScale(2).toString() : "0.00");
            refundVO.setRefundMoney(new BigDecimal(x.getRefundMoney()).setScale(2).toString());
            refundVO.setRefundReason(x.getRefundReason());
            refundVO.setRefundType(x.getRefundType());
            refundVO.setRefundStatu(x.getStatu());
            refundVO.setSmallAmount(new BigDecimal(item.getTotalMoney()).setScale(2).toString());
            refundVO.setTotalMoney(new BigDecimal(item.getTotalMoney()).add(new BigDecimal(refundVO.getPostage())).setScale(2).toString());
            list.add(refundVO);
        });
        voPage.setContent(list);
        list.stream().forEach(LOGGER::info);
        return voPage;
    }
}
