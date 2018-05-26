package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.InOrder;

import java.util.List;

/**
 * @author 11785
 */
public interface InDao {
    void addInOrder(InOrder inOrder);

    int findTotal();

    List<InOrder> page(Integer currentPage, int pageSize);

    InOrder findById(Integer inOrderId);

    void deleteIn(Integer inOrder);

    int findTotalWhen(InOrder inOrderCondition);

    List<InOrder> pageFind(Integer currentPage, int pageSize, InOrder inOrderCondition);

    int findVerfyTotal();

    List<InOrder> pageVerfy(Integer currentPage, int pageSize);

    int findTotalVerfyWhen(InOrder inOrderCondition);

    List<InOrder> pageFindVerfy(Integer currentPage, int pageSize, InOrder inOrderCondition);

    void access(Integer inOrderId);

    void faile(Integer inOrderId);

    void noteVerfy(Integer userId, Integer id);

    void noteVerfyFaile(Integer userId, Integer inOrderId);
}
