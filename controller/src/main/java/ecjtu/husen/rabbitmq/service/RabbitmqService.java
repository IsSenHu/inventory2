package ecjtu.husen.rabbitmq.service;
import ecjtu.husen.dao.InDao;
import ecjtu.husen.dao.ItemDao;
import ecjtu.husen.dao.OutDao;
import ecjtu.husen.pojo.DAO.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author 11785
 */
public class RabbitmqService implements MessageListener {
    private final static Logger logger = LogManager.getLogger(RabbitmqService.class);
    @Autowired
    private InDao inDao;
    @Autowired
    private OutDao outDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Override
    public void onMessage(final Message message) {
        taskExecutor.execute(() -> calItemStock(message));
    }

    /**
     * 重新计算库存
     * @param message
     */
    public void calItemStock(Message message){
        logger.info("message:{}", message);
        String inOrderIdStr = new String(message.getBody());
        Integer inOrderId = Integer.valueOf(inOrderIdStr);
        InOrder inOrder = inDao.findById(inOrderId);
        Item oldItem = inOrder.getItem();
        Integer addNumber = inOrder.getItmeNumber();
        Integer newInventory = oldItem.getCurrentInventory() + addNumber;
        itemDao.claItemStock(oldItem.getItemId(), newInventory);
    }
}
