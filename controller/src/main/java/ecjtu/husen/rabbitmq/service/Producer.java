package ecjtu.husen.rabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 11785
 */
@Service
public class Producer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * convertAndSend 将Java对象转换为消息发送至匹配key的交换机中Exchange
     * @param exchange_key
     * @param queue_key
     * @param object
     */
    public void sendQueue(String exchange_key, String queue_key, Object object) {
        amqpTemplate.convertAndSend(exchange_key, queue_key, object);
    }
}
