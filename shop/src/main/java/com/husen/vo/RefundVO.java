package com.husen.vo;

import java.io.Serializable;

/**
 * @author husen
 */
public class RefundVO implements Serializable {
    private Integer refundId;
    private Integer orderId;
    private Integer itemId;
    //订单ID
    private String id;
    //商品名称
    private String goodName;
    //商品图片
    private String pic;
    //单价
    private String money;
    //数量
    private Integer number;
    //包邮不包邮
    private String isPostage;
    //邮费
    private String postage;
    //合计=小计+邮费
    private String totalMoney;
    //小计=单价*数量
    private String smallAmount;
    //交易时间
    private String dealTime;
    //退货状态
    private Integer refundStatu;
    //退款类型
    private Integer refundType;
    //退款原因
    private Integer refundReason;
    //应该退的金额
    private String refundMoney;
    //详细说明（可以不填）
    private String description;

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Integer getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(Integer refundReason) {
        this.refundReason = refundReason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmallAmount() {
        return smallAmount;
    }

    public void setSmallAmount(String smallAmount) {
        this.smallAmount = smallAmount;
    }

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIsPostage() {
        return isPostage;
    }

    public void setIsPostage(String isPostage) {
        this.isPostage = isPostage;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public Integer getRefundStatu() {
        return refundStatu;
    }

    public void setRefundStatu(Integer refundStatu) {
        this.refundStatu = refundStatu;
    }

    public String getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(String refundMoney) {
        this.refundMoney = refundMoney;
    }

    @Override
    public String toString() {
        return "RefundVO{" +
                "refundId=" + refundId +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", id='" + id + '\'' +
                ", goodName='" + goodName + '\'' +
                ", pic='" + pic + '\'' +
                ", money='" + money + '\'' +
                ", number=" + number +
                ", isPostage='" + isPostage + '\'' +
                ", postage='" + postage + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", smallAmount='" + smallAmount + '\'' +
                ", dealTime='" + dealTime + '\'' +
                ", refundStatu=" + refundStatu +
                ", refundMoney='" + refundMoney + '\'' +
                '}';
    }
}
