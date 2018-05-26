package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DAO.UserRolePO;

import java.util.List;

/**
 * @author 11785
 */
public interface ManagerDao {
    void deleteById(UserPO user);

    int findTotal();

    List<UserPO> page(Integer currentPage, int pageSize);

    UserPO findById(Integer userId);

    void updateStatu(UserPO user);

    List<RolePO> findAllRole();

    List<UserRolePO> findMyUR(Integer userId);

    void deleteUR(Integer userId);

    void addUR(Integer userId, RolePO role);
}
