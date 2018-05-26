package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Brand;
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
public class BrandDaoImpl extends HibernateDaoSupport implements BrandDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public Brand findByNameAndCompany(Brand brand) {
        Object[] objects = {brand.getBrandName(), brand.getBrandCompanyName()};
        List<Brand> brands = (List<Brand>) getHibernateTemplate().find("from t_brand where brandName = ? and brandCompanyName = ?", objects);
        if(brands.size() > 0){
            return brands.get(0);
        }
        return null;
    }

    @Override
    public void saveBrand(Brand brand) {
        getHibernateTemplate().save(brand);
    }

    @Override
    public int findTotal() {
        Query query = getSessionFactory().getCurrentSession().createQuery("select count(brandId) from t_brand ");
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }

    @Override
    public List<Brand> page(Integer currentPage, int pageSize) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from t_brand ");
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Brand findBrandById(Integer brandId) {
        return getHibernateTemplate().get(Brand.class, brandId);
    }

    @Override
    public void updateBrand(Brand brand) {
        Brand brand1 = getHibernateTemplate().get(Brand.class, brand.getBrandId());
        brand1.setBrandCompanyName(brand.getBrandCompanyName());
        brand1.setBrandName(brand.getBrandName());
        getHibernateTemplate().save(brand1);
    }

    @Override
    public void deleteBrandById(Integer brandId) {
        getHibernateTemplate().delete(getHibernateTemplate().get(Brand.class, brandId));
    }

    @Override
    public int findTotalWhen(Brand brand) {
        Session session = getSessionFactory().getCurrentSession();
        StringBuilder hql = new StringBuilder("select count(brandId) from t_brand where 1 = 1");
        if(brand.getBrandId() != null){
            hql.append("and brandId = ?");
        }
        if(StringUtils.isNotBlank(brand.getBrandCompanyName())){
            hql.append("and brandCompanyName like ?");
        }

        Query query = session.createQuery(hql.toString());
        if(brand.getBrandId() != null && StringUtils.isNotBlank(brand.getBrandCompanyName())){
            query.setParameter(0, brand.getBrandId());
            query.setParameter(1, "%" + brand.getBrandCompanyName() + "%");
        }else if(brand.getBrandId() != null && StringUtils.isBlank(brand.getBrandCompanyName())){
            query.setParameter(0, brand.getBrandId());
        }else if (brand.getBrandId() == null && StringUtils.isNotBlank(brand.getBrandCompanyName())){
            query.setParameter(0, "%" + brand.getBrandCompanyName() + "%");
        }
        Object object = query.uniqueResult();
        //首先把object变成long类型，再变成int类型
        Long lobj = (Long) object;
        return lobj.intValue();
    }

    @Override
    public List<Brand> pageFind(Integer currentPage, int pageSize, Brand brand) {
        Session session = getSessionFactory().getCurrentSession();
        StringBuilder hql = new StringBuilder("from t_brand where 1 = 1");
        if(brand.getBrandId() != null){
            hql.append("and brandId = ?");
        }
        if(StringUtils.isNotBlank(brand.getBrandCompanyName())){
            hql.append("and brandCompanyName like ?");
        }

        Query query = session.createQuery(hql.toString());
        if(brand.getBrandId() != null && StringUtils.isNotBlank(brand.getBrandCompanyName())){
            query.setParameter(0, brand.getBrandId());
            query.setParameter(1, "%" + brand.getBrandCompanyName() + "%");
        }else if(brand.getBrandId() != null && StringUtils.isBlank(brand.getBrandCompanyName())){
            query.setParameter(0, brand.getBrandId());
        }else if (brand.getBrandId() == null && StringUtils.isNotBlank(brand.getBrandCompanyName())){
            query.setParameter(0, "%" + brand.getBrandCompanyName() + "%");
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<Brand> findAll() {
        return (List<Brand>) getHibernateTemplate().find("from t_brand");
    }
}
