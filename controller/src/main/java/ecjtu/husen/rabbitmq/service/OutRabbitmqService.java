package ecjtu.husen.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import ecjtu.husen.dao.OutDao;
import ecjtu.husen.pojo.DAO.DeliverOrder;
import ecjtu.husen.pojo.DAO.OutOrder;
import ecjtu.husen.pojo.shop.Address;
import ecjtu.husen.pojo.shop.Order;
import ecjtu.husen.util.HttpService;
import ecjtu.husen.vo.GlobVar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.List;

public class OutRabbitmqService implements MessageListener {
    private final static Logger logger = LogManager.getLogger(OutRabbitmqService.class);
    @Autowired
    private OutDao outDao;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Override
    public void onMessage(final Message message) {
        logger.info("接收到的消息是:{}", message);
        taskExecutor.execute(() -> {
            calItemStockOut(message);
        });
    }

    /**
     * 重新计算库存并生成发货单
     * @param message
     */
    public void calItemStockOut(Message message){
        String outOrderStr = new String(message.getBody());
        Integer outOrderId = Integer.valueOf(outOrderStr);
        OutOrder outOrder = outDao.findById(outOrderId);
        DeliverOrder deliverOrder = new DeliverOrder();
        /**
         * address，先获得orderId，再获得order，最后获得address
         * userId
         * storesId
         * totalMoney
         * statu（未处理状态）
         * 重新计算库存
         * */
        try {
            String data = HttpService.getData("http://localhost:8081/orderById.do?orderId=" + outOrder.getOrderId());
            logger.info("调用接口查询订单成功：{}", data);
            Order order = JSON.parseObject(data, new TypeReference<Order>(){});
            Address address = order.getAddress();
            deliverOrder.setAddressId(address.getAddressId());
            deliverOrder.setUserId(order.getUid());
            deliverOrder.setStoresId(order.getStoreId());
            deliverOrder.setTotalMoney(outOrder.getTotalMoney());
            deliverOrder.setStatu(GlobVar.DELIVER_NO_DONE);
            deliverOrder.setOrderId(outOrder.getOrderId());
            deliverOrder.setDeliverTime(new Date());
            outDao.createDeliver(outOrderId, deliverOrder);
            outDao.calItemStockOut(outOrderId);
        } catch (Exception e) {
            logger.error("调用接口查询订单失败：{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
