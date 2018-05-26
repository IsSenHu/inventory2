package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Brand;

import java.util.List;

public interface BrandDao {
    Brand findByNameAndCompany(Brand brand);

    void saveBrand(Brand brand);

    int findTotal();

    List<Brand> page(Integer currentPage, int pageSize);

    Brand findBrandById(Integer brandId);

    void updateBrand(Brand brand);

    void deleteBrandById(Integer brandId);

    int findTotalWhen(Brand brand);

    List<Brand> pageFind(Integer currentPage, int pageSize, Brand brand);

    List<Brand> findAll();
}
