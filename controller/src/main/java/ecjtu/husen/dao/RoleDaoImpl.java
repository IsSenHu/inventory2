package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.RolePermission;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 11785
 */
@Repository
public class RoleDaoImpl extends HibernateDaoSupport implements RoleDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(roleId) from t_role");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<RolePO> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_role");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public void deleteById(Integer roleId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(RolePO.class, roleId));
    }

    @Override
    public RolePO findById(Integer roleId) {
        return getHibernateTemplate().get(RolePO.class, roleId);
    }

    @Override
    public void updateRole(RolePO rolePO) {
        RolePO newRole = findById(rolePO.getRoleId());
        BeanUtils.copyProperties(rolePO, newRole);
        getHibernateTemplate().save(newRole);
    }

    @Override
    public void addRole(RolePO role) {
        getHibernateTemplate().save(role);
    }

    @Override
    public void deleteRolePermissionById(Integer roleId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("delete from t_role_permission where role.roleId = ?");
        query.setParameter(0, roleId);
        query.executeUpdate();
    }

    @Override
    public void addRolePermission(Integer roleId, List<PermissionPO> permissions) {
        for(PermissionPO permissionPO : permissions){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(new PermissionPO());
            rolePermission.setRole(new RolePO());
            rolePermission.getRole().setRoleId(roleId);
            rolePermission.getPermission().setPermissionId(permissionPO.getPermissionId());
            getHibernateTemplate().save(rolePermission);
        }
    }

    @Override
    public void deleteUserRole(Integer roleId) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("delete from t_user_role where rolePO.roleId = ?");
        query.setParameter(0, roleId);
        query.executeUpdate();
    }

    @Override
    public void deleteRolePermissionByRoleId(Integer roleId) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("delete from t_role_permission where role.roleId = ?");
        query.setParameter(0, roleId);
        query.executeUpdate();
    }

    @Override
    public List<RolePermission> findMyPermission(Integer roleId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_role_permission where role.roleId = ?");
        query.setParameter(0, roleId);
        return query.list();
    }

    @Override
    public List<PermissionPO> findAllPermission() {
        return getSessionFactory().getCurrentSession().createQuery("from t_permisson").list();
    }
}
