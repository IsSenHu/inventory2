package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.DeliverOrder;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.util.Page;
import ecjtu.husen.vo.FindDeliver;
import ecjtu.husen.vo.Result;

/**
 * @author 11785
 */
public interface DeliverService {
    Page<DeliverOrder> find(Integer currentPage, FindDeliver findDeliver);

    Result<Integer> deliverGoods(Integer deliverOrderId, UserPO user);

    Result<Integer> cancelDeliver(Integer deliverOrderId, UserPO user);
}
