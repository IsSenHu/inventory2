package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DAO.UserRolePO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 11785
 */
@Repository
public class ManagerDaoImpl extends HibernateDaoSupport implements ManagerDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void deleteById(UserPO user) {
        getHibernateTemplate().save(user);
    }

    @Override
    public int findTotal() {
        return ((Long)getSessionFactory().getCurrentSession().createQuery("select count(userId) from t_user where userStatu <> 3").uniqueResult()).intValue();
    }

    @Override
    public List<UserPO> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_user where userStatu <> 3");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public UserPO findById(Integer userId) {
        return getHibernateTemplate().get(UserPO.class, userId);
    }

    @Override
    public void updateStatu(UserPO user) {
        getHibernateTemplate().save(user);
    }

    @Override
    public List<RolePO> findAllRole() {
        return getSessionFactory().getCurrentSession().createQuery("from t_role").list();
    }

    @Override
    public List<UserRolePO> findMyUR(Integer userId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_user_role where userPO.userId = ?");
        query.setParameter(0, userId);
        return query.list();
    }

    @Override
    public void deleteUR(Integer userId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("delete from t_user_role where userPO.userId = ?");
        query.setParameter(0, userId);
        query.executeUpdate();
    }

    @Override
    public void addUR(Integer userId, RolePO role) {
        UserRolePO userRolePO = new UserRolePO();
        userRolePO.setUserPO(new UserPO());
        userRolePO.getUserPO().setUserId(userId);
        userRolePO.setRolePO(new RolePO());
        userRolePO.getRolePO().setRoleId(role.getRoleId());
        getHibernateTemplate().save(userRolePO);
    }
}
