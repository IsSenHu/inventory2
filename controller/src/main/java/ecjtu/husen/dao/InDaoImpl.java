package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.InOrder;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DTO.InStatu;
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
public class InDaoImpl extends HibernateDaoSupport implements InDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void addInOrder(InOrder inOrder) {
        System.out.println("inOrder:" + inOrder);
        getHibernateTemplate().save(inOrder);
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(inOrderId) from t_inorder");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<InOrder> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_inorder order by inTime desc");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public InOrder findById(Integer inOrderId) {
        return getHibernateTemplate().get(InOrder.class, inOrderId);
    }

    @Override
    public void deleteIn(Integer inOrderId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(InOrder.class, inOrderId));
    }

    @Override
    public int findTotalWhen(InOrder inOrderCondition) {
        StringBuilder hql = new StringBuilder("select count(inOrderId) from t_inorder where 1 = 1");
        List<Object> list = new ArrayList<Object>();
        if(inOrderCondition.getInOrderId() != null){
            hql.append(" and inOrderId = ?");
            list.add(inOrderCondition.getInOrderId());
        }
        if(inOrderCondition.getItem() != null && StringUtils.isNotBlank(inOrderCondition.getItem().getItemName())){
            hql.append(" and item.itemName = ?");
            list.add(inOrderCondition.getItem().getItemName());
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for(int i = 0; i < list.size(); i++){
            query.setParameter(i, list.get(i));
        }
        Object object = query.uniqueResult();
        return ((Long)object).intValue();
    }

    @Override
    public List<InOrder> pageFind(Integer currentPage, int pageSize, InOrder inOrderCondition) {
        StringBuilder hql = new StringBuilder("from t_inorder where 1 = 1");
        List<Object> list = new ArrayList<Object>();
        if(inOrderCondition.getInOrderId() != null){
            hql.append(" and inOrderId = ?");
            list.add(inOrderCondition.getInOrderId());
        }
        if(inOrderCondition.getItem() != null && StringUtils.isNotBlank(inOrderCondition.getItem().getItemName())){
            hql.append(" and item.itemName = ?");
            list.add(inOrderCondition.getItem().getItemName());
        }
        hql.append(" order by inTime desc");
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for(int i = 0; i < list.size(); i++){
            query.setParameter(i, list.get(i));
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public int findVerfyTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(inOrderId) from t_inorder where inStatu = 0");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<InOrder> pageVerfy(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_inorder where inStatu = 0 order by inTime desc");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public int findTotalVerfyWhen(InOrder inOrderCondition) {
        StringBuilder hql = new StringBuilder("select count(inOrderId) from t_inorder where inStatu = 0");
        List<Object> list = new ArrayList<Object>();
        if(inOrderCondition.getInOrderId() != null){
            hql.append(" and inOrderId = ?");
            list.add(inOrderCondition.getInOrderId());
        }
        if(inOrderCondition.getItem() != null && StringUtils.isNotBlank(inOrderCondition.getItem().getItemName())){
            hql.append(" and item.itemName = ?");
            list.add(inOrderCondition.getItem().getItemName());
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for(int i = 0; i < list.size(); i++){
            query.setParameter(i, list.get(i));
        }
        Object object = query.uniqueResult();
        return ((Long)object).intValue();
    }

    @Override
    public List<InOrder> pageFindVerfy(Integer currentPage, int pageSize, InOrder inOrderCondition) {
        StringBuilder hql = new StringBuilder("from t_inorder where inStatu = 0");
        List<Object> list = new ArrayList<Object>();
        if(inOrderCondition.getInOrderId() != null){
            hql.append(" and inOrderId = ?");
            list.add(inOrderCondition.getInOrderId());
        }
        if(inOrderCondition.getItem() != null && StringUtils.isNotBlank(inOrderCondition.getItem().getItemName())){
            hql.append(" and item.itemName = ?");
            list.add(inOrderCondition.getItem().getItemName());
        }
        hql.append(" order by inTime desc");
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for(int i = 0; i < list.size(); i++){
            query.setParameter(i, list.get(i));
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public void access(Integer inOrderId) {
        InOrder inOrder = findById(inOrderId);
        inOrder.setInStatu(InStatu.auditedSuccessed);
        getHibernateTemplate().save(inOrder);
    }

    @Override
    public void faile(Integer inOrderId) {
        InOrder inOrder = findById(inOrderId);
        inOrder.setInStatu(InStatu.auditedFailed);
        getHibernateTemplate().save(inOrder);
    }

    @Override
    public void noteVerfy(Integer inOrderId, Integer userId) {
        InOrder inOrder = findById(inOrderId);
        inOrder.setUserVerfy(new UserPO());
        inOrder.getUserVerfy().setUserId(userId);
        getHibernateTemplate().save(inOrder);
    }

    @Override
    public void noteVerfyFaile(Integer userId, Integer inOrderId) {
        InOrder inOrder = findById(inOrderId);
        inOrder.setUserVerfy(new UserPO());
        inOrder.getUserVerfy().setUserId(userId);
        getHibernateTemplate().save(inOrder);
    }
}
