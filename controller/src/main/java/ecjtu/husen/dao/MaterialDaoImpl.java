package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Material;
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
public class MaterialDaoImpl extends HibernateDaoSupport implements MaterialDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Material findByName(String materialName) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_material where materialName = ?");
        query.setParameter(0, materialName);
        List<Material> materials = query.list();
        if(materials.size() > 0){
            return materials.get(0);
        }
        return null;
    }

    @Override
    public void addMaterial(Material material) {
        getHibernateTemplate().save(material);
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(materialId) from t_material");
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }

    @Override
    public List<Material> page(Integer currentPage, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from t_material ");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Material findById(Integer materialId) {
        return getHibernateTemplate().get(Material.class, materialId);
    }

    @Override
    public void updateMaterial(Material material) {
        Material material1 = getHibernateTemplate().get(Material.class, material.getMaterialId());
        material1.setMaterialName(material.getMaterialName());
        getHibernateTemplate().save(material1);
    }

    @Override
    public void deleteMaterial(Integer materialId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(Material.class, materialId));
    }

    @Override
    public int findTotalWhen(Material material) {
        Session session = getSessionFactory().getCurrentSession();
        StringBuilder hql = new StringBuilder("select count(materialId) from t_material where 1 = 1");
        if(material.getMaterialId() != null){
            hql.append("and materialId = ?");
        }
        if(StringUtils.isNotBlank(material.getMaterialName())){
            hql.append("and materialName like ?");
        }

        Query query = session.createQuery(hql.toString());
        if(material.getMaterialId() != null && StringUtils.isNotBlank(material.getMaterialName())){
            query.setParameter(0, material.getMaterialId());
            query.setParameter(1, "%" + material.getMaterialName() + "%");
        }else if(material.getMaterialId() != null && StringUtils.isBlank(material.getMaterialName())){
            query.setParameter(0, material.getMaterialId());
        }else if (material.getMaterialId() == null && StringUtils.isNotBlank(material.getMaterialName())){
            query.setParameter(0, "%" + material.getMaterialName() + "%");
        }
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }

    @Override
    public List<Material> pageFind(Integer currentPage, int pageSize, Material material) {
        Session session = getSessionFactory().getCurrentSession();
        StringBuilder hql = new StringBuilder("from t_material where 1 = 1");
        if(material.getMaterialId() != null){
            hql.append("and materialId = ?");
        }
        if(StringUtils.isNotBlank(material.getMaterialName())){
            hql.append("and materialName like ?");
        }

        Query query = session.createQuery(hql.toString());
        if(material.getMaterialId() != null && StringUtils.isNotBlank(material.getMaterialName())){
            query.setParameter(0, material.getMaterialId());
            query.setParameter(1, "%" + material.getMaterialName() + "%");
        }else if(material.getMaterialId() != null && StringUtils.isBlank(material.getMaterialName())){
            query.setParameter(0, material.getMaterialId());
        }else if (material.getMaterialId() == null && StringUtils.isNotBlank(material.getMaterialName())){
            query.setParameter(0, "%" + material.getMaterialName() + "%");
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<Material> findAll() {
        return (List<Material>) getHibernateTemplate().find("from t_material");
    }
}
