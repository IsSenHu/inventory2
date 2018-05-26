package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.UserPO;

import java.util.List;

/**
 * @author 11785
 */
public interface UserDao {
    void userRegist(UserPO userPO) throws Exception;
    UserPO findUserByUsername(String username) throws Exception;

    int findTotal();

    List<UserPO> page(Integer currentPage, int pageSize);

    UserPO update(UserPO userPO);

    List<PermissionPO> findPermissionByRoleId(Integer roleId);

    List<RolePO> findRoleByUserId(Integer userId);
}
