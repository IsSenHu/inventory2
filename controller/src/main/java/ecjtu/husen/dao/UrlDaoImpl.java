package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Url;
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
public class UrlDaoImpl extends HibernateDaoSupport implements UrlDao {
    @Resource
    public void setSessionFactoryo(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public int findTotal() {
        return ((Long)getSessionFactory().getCurrentSession().createQuery("select count(urlId) from Url").uniqueResult()).intValue();
    }

    @Override
    public List<Url> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Url");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Url findById(Integer urlId) {
        return getHibernateTemplate().get(Url.class, urlId);
    }

    @Override
    public void deletePermissionUrl(Integer urlId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("delete from t_permission_url where url.urlId = ?");
        query.setParameter(0, urlId);
        query.executeUpdate();
    }

    @Override
    public void deleteById(Integer urlId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(Url.class, urlId));
    }

    @Override
    public void add(Url url) {
        getHibernateTemplate().save(url);
    }

    @Override
    public void update(Url url) {
        Url oldUrl = findById(url.getUrlId());
        oldUrl.setDescription(url.getDescription());
        oldUrl.setUrl(url.getUrl());
        oldUrl.setUrlId(url.getUrlId());
        getHibernateTemplate().save(oldUrl);
    }
}
