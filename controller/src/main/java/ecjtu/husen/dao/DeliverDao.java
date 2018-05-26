package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.DeliverOrder;
import ecjtu.husen.vo.FindDeliver;

import java.util.List;

/**
 * @author 11785
 */
public interface DeliverDao {
    int findTotal(FindDeliver findDeliver);

    List<DeliverOrder> page(Integer currentPage, int pageSize, FindDeliver findDeliver);

    DeliverOrder findById(Integer deliverOrderId);

    void deliverGoods(Integer deliverOrderId);

    void save(DeliverOrder deliverOrder);
}
