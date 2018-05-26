package com.husen.vo;

import java.io.Serializable;

/**
 * 优惠券的VO
 * @author husen
 */
public class CouponVO implements Serializable {
    private Double need;
    private Double free;
    private Integer expiryTimeLong;
    private String expiryTimeStr;
    private String goodName;
    private Integer id;

    public String getExpiryTimeStr() {
        return expiryTimeStr;
    }

    public void setExpiryTimeStr(String expiryTimeStr) {
        this.expiryTimeStr = expiryTimeStr;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNeed() {
        return need;
    }

    public void setNeed(Double need) {
        this.need = need;
    }

    public Double getFree() {
        return free;
    }

    public void setFree(Double free) {
        this.free = free;
    }

    public Integer getExpiryTimeLong() {
        return expiryTimeLong;
    }

    public void setExpiryTimeLong(Integer expiryTimeLong) {
        this.expiryTimeLong = expiryTimeLong;
    }

    @Override
    public String toString() {
        return "CouponVO{" +
                "need=" + need +
                ", free=" + free +
                ", expiryTimeLong=" + expiryTimeLong +
                ", expiryTimeStr='" + expiryTimeStr + '\'' +
                ", goodName='" + goodName + '\'' +
                ", id=" + id +
                '}';
    }
}
