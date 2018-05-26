package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.PermissionUrl;
import ecjtu.husen.pojo.DAO.RolePermission;
import ecjtu.husen.pojo.DAO.Url;

import java.util.List;

/**
 * @author 11785
 */
public interface PermissionDao {
    int findTotal();

    List<PermissionPO> page(Integer currentPage, int pageSize);

    void deleteById(Integer permissionId);

    void update(PermissionPO permissionPO);

    PermissionPO findById(Integer permissionId);

    void add(PermissionPO permissionPO);

    void deletePermissionUrl(Integer permissionId);

    void addPermissionUrl(Integer permissionId, Url url);

    void deletePermissionUrlByPermissionId(Integer permissionId);

    void deleteRolePermission(Integer permissionId);

    List<Url> findAllUrls();

    List<PermissionUrl> findAllPU(Integer permissionId);
}
