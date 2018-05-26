package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.PermissionUrl;
import ecjtu.husen.pojo.DAO.Url;
import ecjtu.husen.util.Page;

import java.util.List;

/**
 * @author 11785
 */
public interface PermissionService {
    Page<PermissionPO> page(Integer currentPage);

    void deleteById(Integer permissionId);

    void updatePermission(PermissionPO permissionPO);

    PermissionPO findById(Integer permissionId);

    void updatePermissionUrl(Integer permissionId, List<Url> urls);

    void addPermission(PermissionPO permissionPO);

    List<Url> findAllUrls();

    List<PermissionUrl> findAllPU(Integer permissionId);
}
