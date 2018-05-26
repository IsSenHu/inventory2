package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.*;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11785
 */
@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    @Resource
    public void setSessionFactory2(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public void userRegist(UserPO userPO) throws Exception {
        this.getHibernateTemplate().save(userPO);
    }

    @Override
    public UserPO findUserByUsername(String username) throws Exception {
        List<UserPO> list = (List<UserPO>) getHibernateTemplate().find("from t_user where username = ?", username);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(userId) from t_user");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<RolePO> findRoleByUserId(Integer userId) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("from t_user_role where userPO.userId = ?");
        query.setParameter(0, userId);
        List<UserRolePO> userRoles = query.list();
        List<RolePO> roles = new ArrayList<>();
        for(UserRolePO userRole : userRoles){
            roles.add(userRole.getRolePO());
        }
        return roles;
    }

    @Override
    public List<UserPO> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_user order by createDate asc");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public UserPO update(UserPO userPO) {
        UserPO newUser = getHibernateTemplate().get(UserPO.class, userPO.getUserId());
        newUser.setUsername(userPO.getUsername());
        newUser.setEmali(userPO.getEmali());
        newUser.setPhone(userPO.getPhone());
        newUser.setRealName(userPO.getRealName());
        getHibernateTemplate().save(newUser);
        return newUser;
    }

    @Override
    public List<PermissionPO> findPermissionByRoleId(Integer roleId) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("from t_role_permission where role.roleId = ?");
        query.setParameter(0, roleId);
        List<RolePermission> rolePermissions = query.list();
        List<PermissionPO> permissions = new ArrayList<>();
        for(RolePermission rolePermission : rolePermissions){
            permissions.add(rolePermission.getPermission());
        }
        return permissions;
    }
}
