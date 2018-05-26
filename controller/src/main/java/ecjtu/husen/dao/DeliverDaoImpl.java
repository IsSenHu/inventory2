package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.DeliverOrder;
import ecjtu.husen.vo.FindDeliver;
import ecjtu.husen.vo.GlobVar;
import org.apache.commons.lang3.StringUtils;
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
public class DeliverDaoImpl extends HibernateDaoSupport implements DeliverDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public int findTotal(FindDeliver findDeliver) {
        StringBuilder hql = new StringBuilder("select count(deliverOrderId) from t_deliverorder where 1 = 1");
        List<Object> params = new ArrayList<>();
        if (findDeliver != null) {
            if (findDeliver.getDeliverOrderId() != null) {
                hql.append(" and deliverOrderId = ?");
                params.add(findDeliver.getDeliverOrderId());
            }
            if (StringUtils.isNotBlank(findDeliver.getStatu())) {
                hql.append(" and statu = ?");
                params.add(findDeliver.getStatu());
            }
            if (findDeliver.getUserId() != null) {
                hql.append(" and userId = ?");
                params.add(findDeliver.getUserId());
            }
            if (findDeliver.getStoresId() != null) {
                hql.append(" and storesId = ?");
                params.add(findDeliver.getStoresId());
            }
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
        }
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public List<DeliverOrder> page(Integer currentPage, int pageSize, FindDeliver findDeliver) {
        StringBuilder hql = new StringBuilder("from t_deliverorder where 1 = 1");
        List<Object> params = new ArrayList<>();
        if (findDeliver != null) {
            if (findDeliver.getDeliverOrderId() != null) {
                hql.append(" and deliverOrderId = ?");
                params.add(findDeliver.getDeliverOrderId());
            }
            if (StringUtils.isNotBlank(findDeliver.getStatu())) {
                hql.append(" and statu = ?");
                params.add(findDeliver.getStatu());
            }
            if (findDeliver.getUserId() != null) {
                hql.append(" and userId = ?");
                params.add(findDeliver.getUserId());
            }
            if (findDeliver.getStoresId() != null) {
                hql.append(" and storesId = ?");
                params.add(findDeliver.getStoresId());
            }
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public DeliverOrder findById(Integer deliverOrderId) {
        return getHibernateTemplate().get(DeliverOrder.class, deliverOrderId);
    }

    @Override
    public void deliverGoods(Integer deliverOrderId) {
        DeliverOrder deliverOrder = getHibernateTemplate().get(DeliverOrder.class, deliverOrderId);
        deliverOrder.setStatu(GlobVar.DELIVER_DELIVED);
        getHibernateTemplate().save(deliverOrder);
    }

    @Override
    public void save(DeliverOrder deliverOrder) {
        getHibernateTemplate().save(deliverOrder);
    }
}
