package com.husen.vo;

import java.io.Serializable;

/**
 * 订单的查询条件
 * @author 11785
 */
public class FindOrder implements Serializable {
    /**
     * 订单状态
     */
    private Integer orderStatu;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 客户ID
     */
    private Integer userId;

    public Integer getOrderStatu() {
        return orderStatu;
    }

    public void setOrderStatu(Integer orderStatu) {
        this.orderStatu = orderStatu;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FindOrder{" +
                "orderStatu=" + orderStatu +
                ", orderId='" + orderId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
