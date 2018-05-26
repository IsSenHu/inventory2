package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.PermissionUrl;
import ecjtu.husen.pojo.DAO.Url;
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
public class PermissionDaoImpl extends HibernateDaoSupport implements PermissionDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(permissionId) from t_permisson");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<PermissionPO> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_permisson");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public void deleteById(Integer permissionId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(PermissionPO.class, permissionId));
    }

    @Override
    public void update(PermissionPO permissionPO) {
        PermissionPO oldPermission = getHibernateTemplate().get(PermissionPO.class, permissionPO.getPermissionId());
        oldPermission.setDescription(permissionPO.getDescription());
        oldPermission.setPermissionName(permissionPO.getPermissionName());
        getHibernateTemplate().save(oldPermission);
    }

    @Override
    public PermissionPO findById(Integer permissionId) {
        return getHibernateTemplate().get(PermissionPO.class, permissionId);
    }

    @Override
    public void add(PermissionPO permissionPO) {
        getHibernateTemplate().save(permissionPO);
    }

    @Override
    public void deletePermissionUrl(Integer permissionId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("delete from t_permission_url where permission.permissionId = ?");
        query.setParameter(0, permissionId);
        query.executeUpdate();
    }

    @Override
    public void addPermissionUrl(Integer permissionId, Url url) {
        PermissionUrl permissionUrl = new PermissionUrl();
        permissionUrl.setPermission(new PermissionPO());
        permissionUrl.getPermission().setPermissionId(permissionId);
        permissionUrl.setUrl(new Url());
        permissionUrl.setUrl(url);
        getHibernateTemplate().save(permissionUrl);
    }

    @Override
    public void deletePermissionUrlByPermissionId(Integer permissionId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("delete from t_permission_url where permission.permissionId = ?");
        query.setParameter(0, permissionId);
        query.executeUpdate();
    }

    @Override
    public void deleteRolePermission(Integer permissionId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("delete from t_role_permission where permission.permissionId = ?");
        query.setParameter(0, permissionId);
        query.executeUpdate();
    }

    @Override
    public List<Url> findAllUrls() {
        return getSessionFactory().getCurrentSession().createQuery("from Url").list();
    }

    @Override
    public List<PermissionUrl> findAllPU(Integer permissionId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_permission_url where permission.permissionId = ?");
        query.setParameter(0, permissionId);
        return query.list();
    }
}
