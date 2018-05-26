package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.*;
import ecjtu.husen.pojo.DTO.InStatu;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 11785
 */
@Repository
public class OutDaoImpl extends HibernateDaoSupport implements OutDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(outOrderId) from t_outorder");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<OutOrder> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_outorder order by outTime desc");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public int findTotalWhen(OutOrder outOrder, String start, String end) throws ParseException {
        StringBuilder hql = new StringBuilder("select count(outOrderId) from t_outorder where 1 = 1");
        List<Object> list = new ArrayList<>();
        if(outOrder.getInStatu() != null){
            for(InStatu inStatu : InStatu.values()){
                if(outOrder.getInStatu().getValue().equals(inStatu.getValue())){
                    hql.append(" and inStatu = " + outOrder.getInStatu().getValue());
                }
            }
        }
        if(outOrder.getOutOrderId() != null){
            hql.append(" and outOrderId = ?");
            list.add(outOrder.getOutOrderId());
        }
        if(StringUtils.isNotBlank(start)){
            Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            hql.append(" and outTime > ?");
            list.add(startTime);
        }
        if(StringUtils.isNotBlank(end)){
            Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            hql.append(" and outTime < ?");
            list.add(endTime);
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for(int i = 0; i < list.size(); i++){
            query.setParameter(i, list.get(i));
        }
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<OutOrder> pageFind(Integer currentPage, int pageSize, OutOrder outOrder, String start, String end) throws ParseException {
        StringBuilder hql = new StringBuilder("from t_outorder where 1 = 1");
        List<Object> list = new ArrayList<>();
        if(outOrder.getInStatu() != null){
            for(InStatu inStatu : InStatu.values()){
                if(outOrder.getInStatu().getValue().equals(inStatu.getValue())){
                    hql.append(" and inStatu = " + outOrder.getInStatu().getValue());
                }
            }
        }
        if(outOrder.getOutOrderId() != null){
            hql.append(" and outOrderId = ?");
            list.add(outOrder.getOutOrderId());
        }
        if(StringUtils.isNotBlank(start)){
            Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            hql.append(" and outTime > ?");
            list.add(startTime);
        }
        if(StringUtils.isNotBlank(end)){
            Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            hql.append(" and outTime < ?");
            list.add(endTime);
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for(int i = 0; i < list.size(); i++){
            query.setParameter(i, list.get(i));
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public void access(Integer userId, Integer outOrderId) {
        OutOrder outOrder = getHibernateTemplate().get(OutOrder.class, outOrderId);
        outOrder.setInStatu(InStatu.auditedSuccessed);
        outOrder.setVerfyUser(new UserPO());
        outOrder.getVerfyUser().setUserId(userId);
        getHibernateTemplate().save(outOrder);
    }

    @Override
    public OutOrder findById(Integer outOrderId) {
        return getHibernateTemplate().get(OutOrder.class, outOrderId);
    }

    @Override
    public void faile(Integer userId, Integer outOrderId) {

    }

    @Override
    public int findNoVerfyTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(outOrderId) from t_outorder where inStatu = 0");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<OutOrder> pageNoVerfy(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_outorder where inStatu = 0 order by outTime desc");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<Row> rows(Integer outOrderId) {
        List<Row> rows = (List<Row>) getHibernateTemplate().find("from t_row where outOrder.outOrderId = ?", outOrderId);
        return rows;
    }

    @Override
    public int findNoVerfyTotal(OutOrder outOrder, String start, String end) throws ParseException {
        StringBuilder hql = new StringBuilder("select count(outOrderId) from t_outorder where inStatu = 0");
        List<Object> list = new ArrayList<>();
        if(outOrder != null && outOrder.getOutOrderId() != null){
            hql.append(" and outOrderId = ?");
            list.add(outOrder.getOutOrderId());
        }
        if(StringUtils.isNotBlank(start)){
            Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            hql.append(" and outTime > ?");
            list.add(startTime);
        }
        if(StringUtils.isNotBlank(end)){
            Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            hql.append(" and outTime < ?");
            list.add(endTime);
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < list.size(); i++){
            query.setParameter(i, list.get(i));
        }
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<OutOrder> pageNoVerfy(OutOrder outOrder, String start, String end, Integer currentPage, int pageSize) throws ParseException {
        StringBuilder hql = new StringBuilder("from t_outorder where inStatu = 0");
        List<Object> list = new ArrayList<>();
        if(outOrder != null && outOrder.getOutOrderId() != null){
            hql.append(" and outOrderId = ?");
            list.add(outOrder.getOutOrderId());
        }
        if(StringUtils.isNotBlank(start)){
            Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            hql.append(" and outTime > ?");
            list.add(startTime);
        }
        if(StringUtils.isNotBlank(end)){
            Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            hql.append(" and outTime < ?");
            list.add(endTime);
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < list.size(); i++){
            query.setParameter(i, list.get(i));
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    @Transactional
    public void save(List<Row> rows, OutOrder outOrder) {
        Session session = getSessionFactory().getCurrentSession();
        session.save(outOrder);
        session.flush();
        for(Row row : rows){
            row.setOutOrder(outOrder);
            session.save(row);
        }
        session.flush();
    }

    @Override
    @Transactional
    public void createDeliver(Integer outOrderId, DeliverOrder deliverOrder) {
        Session session = getSessionFactory().getCurrentSession();
        session.save(deliverOrder);
        List<Row> rows = rows(outOrderId);
        for(Row row : rows){
            row.setDeliverOrder(deliverOrder);
            getHibernateTemplate().save(row);
        }
    }

    @Override
    public List<Row> rows2(Integer deliverOrderId) {
        return (List<Row>) getHibernateTemplate().find("from t_row where deliverOrder.deliverOrderId = ?", deliverOrderId);
    }

    @Override
    @Transactional
    public void calItemStockOut(Integer outOrderId) {
        List<Row> rows = rows(outOrderId);
        for(Row row : rows){
            Integer itemId = row.getItem().getItemId();
            Item item = getHibernateTemplate().get(Item.class, itemId);
            Integer currentInventory = item.getCurrentInventory();
            item.setCurrentInventory(currentInventory - row.getNumber());
            getHibernateTemplate().save(item);
        }
    }
}
