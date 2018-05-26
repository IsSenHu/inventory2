package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.Brand;
import ecjtu.husen.util.Page;

public interface BrandService {
    boolean addBrand(Brand brand);

    Page<Brand> pageBrand(Integer currentPage);

    Brand findBrandById(Integer brandId);

    boolean updateBrand(Brand brand);

    void deleteBrandById(Integer brandId);

    Page<Brand> findBrand(Brand brand, Integer currentPage);
}
