package ecjtu.husen.dao;

import ecjtu.husen.pojo.DTO.InStatu;
import ecjtu.husen.vo.GlobVar;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author 11785
 */
@Repository
public class MessageDaoImpl extends HibernateDaoSupport implements MessageDao {

    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Integer waitVerfyInOrderNumber() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select count(inOrderId) from t_inorder where inStatu = ?");
        query.setParameter(0, InStatu.notReviewde);
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public Integer waitVerfyOutOrderNumber() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select count(outOrderId) from t_outorder where inStatu = ?");
        query.setParameter(0, InStatu.notReviewde);
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public Integer waitDoDeliverOrderNumber() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select count(deliverOrderId) from t_deliverorder where statu = ?");
        query.setParameter(0, GlobVar.DELIVER_NO_DONE);
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public Integer warnStockNumber() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select count(itemId) from t_item where currentInventory < ?");
        query.setParameter(0, GlobVar.INVENTORY_WARN_LINE);
        return ((Long)query.uniqueResult()).intValue();
    }
}
