package ecjtu.husen.vo;

import ecjtu.husen.pojo.DAO.DeliverOrder;
import ecjtu.husen.pojo.shop.Address;

import java.io.Serializable;

/**
 * 发货单的vo
 * @author 11785
 */
public class DeliverVO implements Serializable {
    private DeliverOrder deliverOrder;
    private Address address;

    public DeliverVO(DeliverOrder deliverOrder, Address address) {
        super();
        this.deliverOrder = deliverOrder;
        this.address = address;
    }

    public DeliverVO() {
        super();
    }

    public DeliverOrder getDeliverOrder() {
        return deliverOrder;
    }

    public void setDeliverOrder(DeliverOrder deliverOrder) {
        this.deliverOrder = deliverOrder;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "DeliverVO{" +
                "deliverOrder=" + deliverOrder +
                ", address=" + address +
                '}';
    }
}
