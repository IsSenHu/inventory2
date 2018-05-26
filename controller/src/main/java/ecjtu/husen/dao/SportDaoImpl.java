package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Sport;
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
public class SportDaoImpl extends HibernateDaoSupport implements  SportDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Sport findSportbyName(String sportName) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_sport where sportName = ?");
        query.setParameter(0, sportName);
        List<Sport> sports = query.list();
        if(sports.size() > 0){
            return sports.get(0);
        }
        return null;
    }

    @Override
    public void addSport(Sport sport) {
        getHibernateTemplate().save(sport);
    }

    @Override
    public int findTotal() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(sportId) from t_sport ");
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }

    @Override
    public List<Sport> page(Integer currentPage, int pageSize) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from t_sport ");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Sport findSportById(Integer sportId) {
        return getHibernateTemplate().get(Sport.class, sportId);
    }

    @Override
    public void updateSport(Sport sport) {
        Sport sport1 = getHibernateTemplate().get(Sport.class, sport.getSportId());
        sport1.setSportName(sport.getSportName());
        getHibernateTemplate().save(sport1);
    }

    @Override
    public void deleteSportById(Integer sportId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(Sport.class, sportId));
    }

    @Override
    public List<Sport> pageFind(Integer currentPage, int pageSize, Sport sport) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder("from t_sport where 1 = 1");
        if(sport.getSportId() != null){
            hql.append("and sportId = ?");
        }
        if(StringUtils.isNotBlank(sport.getSportName())){
            hql.append("and sportName like ?");
        }
        Query query = session.createQuery(hql.toString());
        if(sport.getSportId() != null && StringUtils.isNotBlank(sport.getSportName())){
            query.setParameter(0, sport.getSportId());
            query.setParameter(1, "%" + sport.getSportName() + "%");
        }else if(sport.getSportId() != null && StringUtils.isBlank(sport.getSportName())){
            query.setParameter(0, sport.getSportId());
        }else if (sport.getSportId() == null && StringUtils.isNotBlank(sport.getSportName())){
            query.setParameter(0, "%" + sport.getSportName() + "%");
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<Sport> findAllSport() {
        return (List<Sport>) getHibernateTemplate().find("from t_sport");
    }

    @Override
    public int findTotalWhen(Sport sport) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder("select count(sportId) from t_sport where 1 = 1");
        if(sport.getSportId() != null){
            hql.append("and sportId = ?");
        }
        if(StringUtils.isNotBlank(sport.getSportName())){
            hql.append("and sportName like ?");
        }
        Query query = session.createQuery(hql.toString());
        if(sport.getSportId() != null && StringUtils.isNotBlank(sport.getSportName())){
            query.setParameter(0, sport.getSportId());
            query.setParameter(1, "%" + sport.getSportName() + "%");
        }else if(sport.getSportId() != null && StringUtils.isBlank(sport.getSportName())){
            query.setParameter(0, sport.getSportId());
        }else if (sport.getSportId() == null && StringUtils.isNotBlank(sport.getSportName())){
            query.setParameter(0, "%" + sport.getSportName() + "%");
        }
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }
}
