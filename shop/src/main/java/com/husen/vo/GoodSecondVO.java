package com.husen.vo;

public class GoodSecondVO {
    private Integer goodId;
    private String goodName;
    private Double originalPrice;
    private Double salePrice;

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "GoodSecondVO{" +
                "goodId=" + goodId +
                ", goodName='" + goodName + '\'' +
                ", originalPrice=" + originalPrice +
                ", salePrice=" + salePrice +
                '}';
    }
}
