package ecjtu.husen.dao;

import ecjtu.husen.model.OperationNote;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 11785
 */
@Repository
public class VerfyNoteDaoImpl extends HibernateDaoSupport implements VerfyNoteDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public int findTotal() {
        return ((Long)getSessionFactory().getCurrentSession().createQuery("select count(inNoteId) from t_inNote where operationContent like ?").setParameter(0, "%入库%").uniqueResult()).intValue();
    }

    @Override
    public List<OperationNote> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("from t_inNote where operationContent like ?");
        query.setParameter(0, "%入库%");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<OperationNote> afterTenNote() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("from t_inNote order by operationTime desc");
        query.setFirstResult(0);
        query.setMaxResults(10);
        return query.list();
    }

    @Override
    public int findTotal2() {
        return ((Long)getSessionFactory().getCurrentSession().createQuery("select count(inNoteId) from t_inNote where operationContent like ?").setParameter(0, "%出库%").uniqueResult()).intValue();
    }

    @Override
    public List<OperationNote> page2(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("from t_inNote where operationContent like ?");
        query.setParameter(0, "%出库%");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public int findTotal3() {
        return ((Long)getSessionFactory().getCurrentSession().createQuery("select count(inNoteId) from t_inNote where operationContent like ?").setParameter(0, "%发货%").uniqueResult()).intValue();
    }

    @Override
    public List<OperationNote> page3(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("from t_inNote where operationContent like ?");
        query.setParameter(0, "%发货%");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    @Transactional
    public void save(OperationNote operationNote) {
        getHibernateTemplate().save(operationNote);
    }
}
