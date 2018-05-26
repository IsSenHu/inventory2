package com.husen.vo;

import com.husen.model.Good;
import ecjtu.husen.pojo.DAO.Item;

import java.io.Serializable;

/**
 * 商品的VO
 * @author 11785
 */
public class GoodVO implements Serializable {
    private Good good;

    private Item item;

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public GoodVO(Good good, Item item) {
        super();
        this.good = good;
        this.item = item;
    }

    public GoodVO() {
        super();
    }
}
