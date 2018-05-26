package ecjtu.husen.service;

import ecjtu.husen.dao.PermissionDao;
import ecjtu.husen.dao.RoleDao;
import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.RolePermission;
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
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public Page<RolePO> page(Integer currentPage) {
        Page<RolePO> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = roleDao.findTotal();
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
        List<RolePO> rolePOS = roleDao.page(currentPage, page.getPageSize());
        page.setContent(rolePOS);
        return page;
    }

    @Override
    public void deleteById(Integer roleId) {
        /*
        * 删除角色要先出角色和管理员关系
        * 删除角色和权限的关系
        * 再删除角色
        * */
        roleDao.deleteUserRole(roleId);
        roleDao.deleteRolePermissionByRoleId(roleId);
        roleDao.deleteById(roleId);
    }

    @Override
    public RolePO findById(Integer roleId) {
        return roleDao.findById(roleId);
    }

    @Override
    public void updateRole(RolePO rolePO) {
        roleDao.updateRole(rolePO);
    }

    @Override
    public void updateRolePermission(Integer roleId, List<PermissionPO> permissions) {
        /*
        * 先把角色以前拥有的权限关系删除
        * 然后再新增
        * */
        roleDao.deleteRolePermissionById(roleId);
        /*
        * 要判断传过来的权限集合是否为空，未空表示该角色的所有权限被取消,直接删除就可以了。
        * */
        if(permissions != null){
            roleDao.addRolePermission(roleId, permissions);
        }
    }

    @Override
    public void addRole(RolePO role) {
        roleDao.addRole(role);
    }

    @Override
    public List<PermissionPO> findAllPermission() {
        return roleDao.findAllPermission();
    }

    @Override
    public List<RolePermission> findMyPermission(Integer roleId) {
        return roleDao.findMyPermission(roleId);
    }
}
