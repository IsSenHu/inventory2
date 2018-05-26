package com.husen.service.rabbitmq;
import com.husen.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

/**
 * @author 11785
 */
@Transactional
public class RabbitmqService implements MessageListener {
    private static final Logger LOGGER = LogManager.getLogger(RabbitmqService.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void onMessage(Message message) {
        LOGGER.info(Thread.currentThread().getName());
        taskExecutor.execute(() ->then(message));
    }

    public void then(Message message){
        orderService.then(message);
    }
}
