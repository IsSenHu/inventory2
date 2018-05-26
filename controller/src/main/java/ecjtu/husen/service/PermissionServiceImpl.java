package ecjtu.husen.service;

import ecjtu.husen.dao.PermissionDao;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.PermissionUrl;
import ecjtu.husen.pojo.DAO.Url;
import ecjtu.husen.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 11785
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Page<PermissionPO> page(Integer currentPage) {
        Page<PermissionPO> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = permissionDao.findTotal();
        page.setRowsTotal(rowsTotal);
        /*
        * 开始计算总共有多少页并进行设置
        * */
        if(rowsTotal % page.getPageSize() == 0){
            page.setTotalPage(rowsTotal / page.getPageSize());
        }else {
            page.setTotalPage(rowsTotal / page.getPageSize() + 1);
        }
        /*
        * 判断传入的当前页是否合法
        * */
        if(currentPage <= 0){
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }else if (currentPage > page.getTotalPage()){
            currentPage = page.getTotalPage();
            page.setCurrentPage(currentPage);
        }
        List<PermissionPO> permissionPOS = permissionDao.page(currentPage, page.getPageSize());
        page.setContent(permissionPOS);
        return page;
    }

    @Override
    public void deleteById(Integer permissionId) {

        /*
        * 删除权限要删除权限url关系
        * 删除角色权限关系
        * 再删除该权限
        * */
        permissionDao.deletePermissionUrlByPermissionId(permissionId);
        permissionDao.deleteRolePermission(permissionId);
        permissionDao.deleteById(permissionId);
    }

    @Override
    public void updatePermission(PermissionPO permissionPO) {
        permissionDao.update(permissionPO);
    }

    @Override
    public PermissionPO findById(Integer permissionId) {
        return permissionDao.findById(permissionId);
    }

    @Override
    public void updatePermissionUrl(Integer permissionId, List<Url> urls) {
        /*
        *
        * 再将新的地址关系保存进去
        * */
        permissionDao.deletePermissionUrl(permissionId);
        for(Url url : urls){
            permissionDao.addPermissionUrl(permissionId, url);
        }
    }

    @Override
    public void addPermission(PermissionPO permissionPO) {
        permissionDao.add(permissionPO);
    }

    @Override
    public List<Url> findAllUrls() {
        return permissionDao.findAllUrls();
    }

    @Override
    public List<PermissionUrl> findAllPU(Integer permissionId) {
        return permissionDao.findAllPU(permissionId);
    }
}
