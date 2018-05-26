package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.SportItem;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
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
public class SportItemDaoImpl extends HibernateDaoSupport implements SportItemDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public SportItem findByName(String sportItemName) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_sport_item where sportItemName = ?");
        query.setParameter(0, sportItemName);
        List<SportItem> sportItems = query.list();
        if(sportItems.size() > 0){
            return sportItems.get(0);
        }
        return null;
    }

    @Override
    public void addSportItem(SportItem sportItem) {
        getHibernateTemplate().save(sportItem);
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(sportItemId) from t_sport_item");
        Object object = query.uniqueResult();
        Long ltotal = (Long) object;
        return ltotal.intValue();
    }

    @Override
    public List<SportItem> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_sport_item");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public void updateSportItem(SportItem sportItem) {
        SportItem sportItem1 = getHibernateTemplate().get(SportItem.class, sportItem.getSportItemId());
        sportItem1.setSportItemName(sportItem.getSportItemName());
        getHibernateTemplate().save(sportItem1);
    }

    @Override
    public void deleteSportItem(Integer sportItemId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(SportItem.class, sportItemId));
    }

    @Override
    public int findTotalWhen(SportItem sportItem) {
        Session session = getSessionFactory().getCurrentSession();
        StringBuilder hql = new StringBuilder("select count(sportItemId) from t_sport_item where 1 = 1");
        if(sportItem.getSportItemId() != null){
            hql.append("and sportItemId = ?");
        }
        if(StringUtils.isNotBlank(sportItem.getSportItemName())){
            hql.append("and sportItemName like ?");
        }

        Query query = session.createQuery(hql.toString());
        if(sportItem.getSportItemId() != null && StringUtils.isNotBlank(sportItem.getSportItemName())){
            query.setParameter(0, sportItem.getSportItemId());
            query.setParameter(1, "%" + sportItem.getSportItemName() + "%");
        }else if(sportItem.getSportItemId() != null && StringUtils.isBlank(sportItem.getSportItemName())){
            query.setParameter(0, sportItem.getSportItemId());
        }else if (sportItem.getSportItemId() == null && StringUtils.isNotBlank(sportItem.getSportItemName())){
            query.setParameter(0, "%" + sportItem.getSportItemName() + "%");
        }
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }

    @Override
    public List<SportItem> pageFind(Integer currentPage, int pageSize, SportItem sportItem) {
        Session session = getSessionFactory().getCurrentSession();
        StringBuilder hql = new StringBuilder("from t_sport_item where 1 = 1");
        if(sportItem.getSportItemId() != null){
            hql.append("and sportItemId = ?");
        }
        if(StringUtils.isNotBlank(sportItem.getSportItemName())){
            hql.append("and sportItemName like ?");
        }

        Query query = session.createQuery(hql.toString());
        if(sportItem.getSportItemId() != null && StringUtils.isNotBlank(sportItem.getSportItemName())){
            query.setParameter(0, sportItem.getSportItemId());
            query.setParameter(1, "%" + sportItem.getSportItemName() + "%");
        }else if(sportItem.getSportItemId() != null && StringUtils.isBlank(sportItem.getSportItemName())){
            query.setParameter(0, sportItem.getSportItemId());
        }else if (sportItem.getSportItemId() == null && StringUtils.isNotBlank(sportItem.getSportItemName())){
            query.setParameter(0, "%" + sportItem.getSportItemName() + "%");
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public SportItem findById(Integer sportItemId) {
        return getHibernateTemplate().get(SportItem.class, sportItemId);
    }

    @Override
    public List<SportItem> findAll() {
        return (List<SportItem>) getHibernateTemplate().find("from t_sport_item");
    }
}
