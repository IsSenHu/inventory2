package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DAO.UserRolePO;
import ecjtu.husen.util.Page;

import java.util.List;

/**
 * @author 11785
 */
public interface ManagerService {
    Page<UserPO> page(Integer currentPage);

    void deleteById(Integer userId);

    void updateStatu(Integer userId);

    List<RolePO> findAllRole();

    List<UserRolePO> findMyRP(Integer userId);

    void updateUserRole(Integer userId, List<RolePO> roles);
}
