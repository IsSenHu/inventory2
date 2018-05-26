package ecjtu.husen.vo;

import java.io.Serializable;

/**
 * 查询发货单的条件
 * @author 11785
 */
public class FindDeliver implements Serializable {

    /**
     * 发货单状态
     */
    private String statu;

    /**
     * 发货单号
     */
    private Integer deliverOrderId;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 店铺Id
     */
    private Integer storesId;

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public Integer getDeliverOrderId() {
        return deliverOrderId;
    }

    public void setDeliverOrderId(Integer deliverOrderId) {
        this.deliverOrderId = deliverOrderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoresId() {
        return storesId;
    }

    public void setStoresId(Integer storesId) {
        this.storesId = storesId;
    }

    @Override
    public String toString() {
        return "FindDeliver{" +
                "statu='" + statu + '\'' +
                ", deliverOrderId=" + deliverOrderId +
                ", userId=" + userId +
                ", storesId=" + storesId +
                '}';
    }
}
