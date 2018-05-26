package com.husen.vo;

import java.io.Serializable;

/**
 * 折扣的vo
 * @author 11785
 */
public class PromotionVO implements Serializable {
    private Integer spId;
    private Integer number;
    private Double discount;
    private String goodName;

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    @Override
    public String toString() {
        return "PromotionVO{" +
                "spId=" + spId +
                ", number=" + number +
                ", discount=" + discount +
                ", goodName='" + goodName + '\'' +
                '}';
    }
}
