package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.RolePermission;
import ecjtu.husen.util.Page;

import java.util.List;

/**
 * @author 11785
 */
public interface RoleService {
    Page<RolePO> page(Integer currentPage);

    void deleteById(Integer roleId);

    RolePO findById(Integer roleId);

    void updateRole(RolePO rolePO);

    void updateRolePermission(Integer roleId, List<PermissionPO> permissions);

    void addRole(RolePO role);

    List<PermissionPO> findAllPermission();

    List<RolePermission> findMyPermission(Integer roleId);
}
