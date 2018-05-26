package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Specification;
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
public class SpecificationDaoImpl extends HibernateDaoSupport implements SpecificationDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory0){
        super.setSessionFactory(sessionFactory0);
    }

    @Override
    public Specification findByName(String specificationName) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_specification where specificationName = ?");
        query.setParameter(0, specificationName);
        List<Specification> specifications = query.list();
        if(specifications.size() > 0){
            return specifications.get(0);
        }
        return null;
    }

    @Override
    public void addSpecification(Specification specification) {
        getHibernateTemplate().save(specification);
    }

    @Override
    public Specification findById(Integer specificationId) {
        return getHibernateTemplate().get(Specification.class, specificationId);
    }

    @Override
    public void updateSpecification(Specification specification) {
        Specification specification1 = getHibernateTemplate().get(Specification.class, specification.getSpecificationId());
        specification1.setSpecificationName(specification.getSpecificationName());
        getHibernateTemplate().save(specification1);
    }

    @Override
    public void deleteById(Integer specificationId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(Specification.class, specificationId));
    }

    @Override
    public List<Specification> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_specification");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(specificationId) from t_specification");
        Object object = query.uniqueResult();
        return ((Long)object).intValue();
    }

    @Override
    public List<Specification> findAll() {
        return (List<Specification>) getHibernateTemplate().find("from t_specification");
    }
}
