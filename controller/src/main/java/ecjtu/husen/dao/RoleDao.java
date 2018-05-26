package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.RolePermission;

import java.util.List;

public interface RoleDao {
    int findTotal();

    List<RolePO> page(Integer currentPage, int pageSize);

    void deleteById(Integer roleId);

    RolePO findById(Integer roleId);

    void updateRole(RolePO rolePO);

    void addRole(RolePO role);

    void deleteRolePermissionById(Integer roleId);

    void addRolePermission(Integer roleId, List<PermissionPO> permissions);

    void deleteUserRole(Integer roleId);

    void deleteRolePermissionByRoleId(Integer roleId);

    List<RolePermission> findMyPermission(Integer roleId);

    List<PermissionPO> findAllPermission();
}
