package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Item;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11785
 */
@Repository
public class ItemDaoImpl extends HibernateDaoSupport implements ItemDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory0){
        super.setSessionFactory(sessionFactory0);
    }

    @Override
    public Item findByName(String itemName) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_item where itemName = ?");
        query.setParameter(0, itemName);
        List<Item> items = query.list();
        if(items.size() > 0){
            return items.get(0);
        }
        return null;
    }

    @Override
    public void addItem(Item item) {
        getHibernateTemplate().save(item);
    }

    @Override
    public Item findById(Integer itemId) {
        return getHibernateTemplate().get(Item.class, itemId);
    }

    @Override
    public void updateItem(Item item) {
        Item item1 = getHibernateTemplate().get(Item.class, item.getItemId());
        Integer itemId = item1.getItemId();
        BeanUtils.copyProperties(item, item1);
        item1.setItemId(itemId);
        getHibernateTemplate().save(item1);
    }

    @Override
    public void deleteItem(Integer itemId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(Item.class, itemId));
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(itemId) from t_item");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<Item> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_item order by currentInventory asc");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public int findTotalWhen(Item item) {
        List<Object> objects = new ArrayList<>();
        StringBuilder hql = new StringBuilder("select count(itemId) from t_item where 1 = 1 ");
        if(item.getItemId() != null){
            objects.add(item.getItemId());
            hql.append("and itemId = ? ");
        }
        if(item.getItemName() != null){
            objects.add("%" + item.getItemName() + "%");
            hql.append("and itemName like ? ");
        }
        if(item.getBrand() != null && item.getBrand().getBrandId() != null){
            objects.add(item.getBrand().getBrandId());
            hql.append("and brandId = ? ");
        }
        if(item.getSportItem() != null && item.getSportItem().getSportItemId() != null){
            objects.add(item.getSportItem().getSportItemId());
            hql.append("and sportItemId = ?");
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < objects.size(); i++){
            query.setParameter(i, objects.get(i));
        }
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<Item> pageFind(Integer currentPage, int pageSize, Item item) {
        List<Object> objects = new ArrayList<>();
        StringBuilder hql = new StringBuilder("from t_item where 1 = 1 ");
        if(item.getItemId() != null){
            objects.add(item.getItemId());
            hql.append("and itemId = ? ");
        }
        if(item.getItemName() != null){
            objects.add("%" + item.getItemName() + "%");
            hql.append("and itemName like ? ");
        }
        if(item.getBrand() != null && item.getBrand().getBrandId() != null){
            objects.add(item.getBrand().getBrandId());
            hql.append("and brandId = ? ");
        }
        if(item.getSportItem() != null && item.getSportItem().getSportItemId() != null){
            objects.add(item.getSportItem().getSportItemId());
            hql.append("and sportItemId = ?");
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < objects.size(); i++){
            query.setParameter(i, objects.get(i));
        }
        return query.list();
    }

    @Override
    public void uploadPic(String picPath, Item item) {
        Item item1 = getHibernateTemplate().get(Item.class, item.getItemId());
        item1.setPicPath(picPath);
        getHibernateTemplate().save(item1);
    }

    @Override
    public List<Item> findIdAndName() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select itemId, itemName from t_item");
        return query.list();
    }

    @Override
    public List<Item> findIdAndNameBySportId(Integer sportId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("select itemId, itemName from t_item where applyer.sport.sportId = ?");
        query.setParameter(0, sportId);
        return query.list();
    }

    @Override
    @Transactional
    public void claItemStock(Integer itemId, Integer newInventory) {
        Item item = findById(itemId);
        item.setCurrentInventory(newInventory);
        getHibernateTemplate().save(item);
    }
}
