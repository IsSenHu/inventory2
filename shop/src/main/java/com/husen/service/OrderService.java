package com.husen.service;

import com.husen.model.Boss;
import com.husen.model.Order;
import com.husen.vo.FindOrder;
import org.springframework.amqp.core.Message;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<Order> findOrder1(Integer currentPage, Boss boss, FindOrder findOrder);

    String deleteOrder(Integer orderId);

    String deliver(Integer orderId);

    Order findById(String orderId);

    void then(Message message);
}
