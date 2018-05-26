package ecjtu.husen.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import ecjtu.husen.dao.ItemDao;
import ecjtu.husen.dao.OutDao;
import ecjtu.husen.pojo.DAO.Item;
import ecjtu.husen.pojo.DAO.OutOrder;
import ecjtu.husen.pojo.DAO.Row;
import ecjtu.husen.pojo.DTO.InStatu;
import ecjtu.husen.pojo.shop.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author husen
 */
public class OrderRabbitmqService implements MessageListener {
    private final static Logger LOGGER = LogManager.getLogger(OrderRabbitmqService.class);
    @Autowired
    private OutDao outDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void onMessage(final Message message) {
        taskExecutor.execute(() -> {
            LOGGER.info(Thread.currentThread().getName());
            createOutOrder(message);
        });
    }

    /**
     * 根据订单生成出库单
     * @param message
     */
    private void createOutOrder(Message message) {
        String orderStr = null;
        try {
            orderStr = new String(message.getBody(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("解析失败:{}", e.getMessage());
        }
        Order order = JSON.parseObject(orderStr, new TypeReference<Order>(){});
        LOGGER.info("接收到的订单数据为:{}", order);
        List<ecjtu.husen.pojo.shop.Item> items = order.getItems();
        OutOrder outOrder = new OutOrder();
        outOrder.setInStatu(InStatu.notReviewde);
        outOrder.setOrderId(order.getId());
        outOrder.setOutTime(new Date());
        List<Row> rows = new ArrayList<>();
        //出库的货物总金额
        Double totalMoney = 0.0;
        for (ecjtu.husen.pojo.shop.Item item : items){
            Row row = new Row();
            Item it = itemDao.findById(item.getGood().getItemId());
            row.setItem(it);
            row.setMoney(item.getUnitPrice());
            row.setNumber(item.getNumber());
            row.setRowMoney(item.getTotalMoney());
            totalMoney += item.getTotalMoney();
            rows.add(row);
        }
        outOrder.setTotalMoney(totalMoney);
        outDao.save(rows, outOrder);
    }
}
