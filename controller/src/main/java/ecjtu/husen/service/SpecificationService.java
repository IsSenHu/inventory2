package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.Specification;
import ecjtu.husen.util.Page;

/**
 * @author 11785
 */
public interface SpecificationService {
    boolean addSpecification(Specification specification);

    Page<Specification> pageSpecification(Integer currentPage);

    Specification findById(Integer specificationId);

    boolean updateSpecification(Specification specification);

    void deleteById(Integer specificationId);
}
