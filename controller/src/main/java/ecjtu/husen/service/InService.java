package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.InOrder;
import ecjtu.husen.pojo.DAO.Item;
import ecjtu.husen.pojo.DAO.Sport;
import ecjtu.husen.util.Page;

import java.util.List;

/**
 * @author 11785
 */
public interface InService {
    List<Item> findAllItemBySportId(Integer sportId);

    List<Sport> findAllSport();

    void addInOrder(InOrder inOrder);

    Page<InOrder> pageIn(Integer currentPage);

    void deleteIn(Integer inOrderId);

    Page<InOrder> findIn(InOrder inOrderCondition, Integer currentPage);

    Page<InOrder> pageNoVerfy(Integer currentPage);

    Page<InOrder> findVerfyIn(InOrder inOrderCondition, Integer currentPage);

    void access(Integer userId, Integer inOrderId);

    void faile(Integer userId, Integer inOrderId);
}
