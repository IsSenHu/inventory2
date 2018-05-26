package ecjtu.husen.dao;

import ecjtu.husen.model.OperationNote;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 11785
 */
@Repository
public class InOrderOperationNoteDaoImpl extends HibernateDaoSupport implements InOrderOperationNoteDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void addNote(OperationNote note) {
        getHibernateTemplate().save(note);
    }
}
