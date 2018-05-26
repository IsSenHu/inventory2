package ecjtu.husen.service;

import ecjtu.husen.dao.BrandDao;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Brand;
import ecjtu.husen.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 11785
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;
    @Override
    public boolean addBrand(Brand brand) {
        Brand brand1 = brandDao.findByNameAndCompany(brand);
        if(brand1 != null){
            return false;
        }else {
            brandDao.saveBrand(brand);
            return true;
        }
    }

    @Override
    public Page<Brand> pageBrand(Integer currentPage) {
        Page<Brand> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = brandDao.findTotal();
        page.setRowsTotal(rowsTotal);
        /*
        * 开始计算总共有多少页并进行设置
        * */
        if(rowsTotal % page.getPageSize() == 0){
            page.setTotalPage(rowsTotal / page.getPageSize());
        }else {
            page.setTotalPage(rowsTotal / page.getPageSize() + 1);
        }
        /*
        * 判断传入的当前页是否合法
        * */
        if(currentPage <= 0){
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }else if (currentPage > page.getTotalPage()){
            currentPage = page.getTotalPage();
            page.setCurrentPage(currentPage);
        }
        List<Brand> brands = brandDao.page(currentPage, page.getPageSize());
        page.setContent(brands);
        return page;
    }

    @Override
    public Brand findBrandById(Integer brandId) {
        return brandDao.findBrandById(brandId);
    }

    @Override
    public boolean updateBrand(Brand brand) {
        Brand brand1 = brandDao.findByNameAndCompany(brand);
        if(brand1 != null){
            return false;
        }else {
            brandDao.updateBrand(brand);
            return true;
        }
    }

    @Override
    public void deleteBrandById(Integer brandId) {
        brandDao.deleteBrandById(brandId);
    }

    @Override
    public Page<Brand> findBrand(Brand brand, Integer currentPage) {
        Page<Brand> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = brandDao.findTotalWhen(brand);
        page.setRowsTotal(rowsTotal);
        /*
        * 开始计算总共有多少页并进行设置
        * */
        if(rowsTotal % page.getPageSize() == 0){
            page.setTotalPage(rowsTotal / page.getPageSize());
        }else {
            page.setTotalPage(rowsTotal / page.getPageSize() + 1);
        }
        /*
        * 判断传入的当前页是否合法
        * */
        if(currentPage <= 0){
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }else if (currentPage > page.getTotalPage()){
            currentPage = page.getTotalPage();
            page.setCurrentPage(currentPage);
        }
        List<Brand> brands = brandDao.pageFind(currentPage, page.getPageSize(), brand);
        page.setContent(brands);
        return page;
    }
}
