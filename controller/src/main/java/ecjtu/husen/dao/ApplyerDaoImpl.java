package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Applyer;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ApplyerDaoImpl extends HibernateDaoSupport implements ApplyerDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void addApplyer(Applyer applyer) {
        getHibernateTemplate().save(applyer);
    }

    @Override
    public List<Applyer> findAll() {
        return (List<Applyer>) getHibernateTemplate().find("select o from t_applyer o");
    }

    @Override
    public int findTotal() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(applyerId) from t_applyer ");
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }

    @Override
    public List<Applyer> page(Integer currentPage, int pageSize) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from t_applyer");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Applyer findApplyerById(Integer applyerId) {
        return getHibernateTemplate().get(Applyer.class, applyerId);
    }

    @Override
    public Applyer findApplyerByName(String applyerName) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("from t_applyer where applyerName = ?");
        query.setParameter(0, applyerName);
        List<Applyer> applyers = query.list();
        if(applyers.size() >= 1){
            return applyers.get(0);
        }
        return null;
    }

    @Override
    public void saveApplyer(Applyer applyer) {
        Applyer applyer1 = getHibernateTemplate().get(Applyer.class, applyer.getApplyerId());
        applyer1.setApplyerName(applyer.getApplyerName());
        getHibernateTemplate().save(applyer1);
    }

    @Override
    public void deleteApplyerById(Integer applyerId) {
        Applyer applyer = getHibernateTemplate().get(Applyer.class, applyerId);
        getHibernateTemplate().delete(applyer);
    }

    @Override
    public List<Applyer> pageFind(Integer currentPage, int pageSize, Applyer applyer) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder("from t_applyer where 1 = 1");
        if(applyer.getApplyerId() != null){
            hql.append("and applyerId = ?");
        }
        if(StringUtils.isNotBlank(applyer.getApplyerName())){
            hql.append("and applyerName like ?");
        }
        Query query = session.createQuery(hql.toString());
        if(applyer.getApplyerId() != null && StringUtils.isNotBlank(applyer.getApplyerName())){
            query.setParameter(0, applyer.getApplyerId());
            query.setParameter(1, "%" + applyer.getApplyerName() + "%");
        }else if(applyer.getApplyerId() != null && StringUtils.isBlank(applyer.getApplyerName())){
            query.setParameter(0, applyer.getApplyerId());
        }else if (applyer.getApplyerId() == null && StringUtils.isNotBlank(applyer.getApplyerName())){
            query.setParameter(0, "%" + applyer.getApplyerName() + "%");
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public int findTotalWhen(Applyer applyer) {
        Session session = getSessionFactory().getCurrentSession();
        StringBuilder hql = new StringBuilder("select count(applyerId) from t_applyer where 1 = 1");
        if(applyer.getApplyerId() != null){
            hql.append("and applyerId = ?");
        }
        if(StringUtils.isNotBlank(applyer.getApplyerName())){
            hql.append("and applyerName like ?");
        }

        Query query = session.createQuery(hql.toString());
        if(applyer.getApplyerId() != null && StringUtils.isNotBlank(applyer.getApplyerName())){
            query.setParameter(0, applyer.getApplyerId());
            query.setParameter(1, "%" + applyer.getApplyerName() + "%");
        }else if(applyer.getApplyerId() != null && StringUtils.isBlank(applyer.getApplyerName())){
            query.setParameter(0, applyer.getApplyerId());
        }else if (applyer.getApplyerId() == null && StringUtils.isNotBlank(applyer.getApplyerName())){
            query.setParameter(0, "%" + applyer.getApplyerName() + "%");
        }
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }
}
