package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Specification;

import java.util.List;

/**
 * @author 11785
 */
public interface SpecificationDao {
    Specification findByName(String specificationName);

    void addSpecification(Specification specification);

    Specification findById(Integer specificationId);

    void updateSpecification(Specification specification);

    void deleteById(Integer specificationId);

    List<Specification> page(Integer currentPage, int pageSize);

    int findTotal();

    List<Specification> findAll();
}
