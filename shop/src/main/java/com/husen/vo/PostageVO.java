package com.husen.vo;

import java.io.Serializable;

/**
 * 是否包邮的VO
 */
public class PostageVO implements Serializable {
    private String isPostage;
    private Double postageMoney;

    public String getIsPostage() {
        return isPostage;
    }

    public void setIsPostage(String isPostage) {
        this.isPostage = isPostage;
    }

    public Double getPostageMoney() {
        return postageMoney;
    }

    public void setPostageMoney(Double postageMoney) {
        this.postageMoney = postageMoney;
    }

    @Override
    public String toString() {
        return "PostageVO{" +
                "isPostage='" + isPostage + '\'' +
                ", postageMoney=" + postageMoney +
                '}';
    }

    public PostageVO() {
        super();
    }

    public PostageVO(String isPostage, Double postageMoney) {
        super();
        this.isPostage = isPostage;
        this.postageMoney = postageMoney;
    }
}
