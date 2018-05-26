package ecjtu.husen.service;

import ecjtu.husen.dao.SpecificationDao;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Specification;
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
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationDao specificationDao;
    @Override
    public boolean addSpecification(Specification specification) {
        Specification specification1 = specificationDao.findByName(specification.getSpecificationName());
        if(specification1 != null){
            return false;
        }else {
            specificationDao.addSpecification(specification);
            return true;
        }
    }

    @Override
    public Page<Specification> pageSpecification(Integer currentPage) {
        Page<Specification> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = specificationDao.findTotal();
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
        List<Specification> specifications = specificationDao.page(currentPage, page.getPageSize());
        page.setContent(specifications);
        return page;
    }

    @Override
    public Specification findById(Integer specificationId) {
        Specification specification = specificationDao.findById(specificationId);
        return specification;
    }

    @Override
    public boolean updateSpecification(Specification specification) {
        Specification specification1 = specificationDao.findByName(specification.getSpecificationName());
        if(specification1 != null){
            return false;
        }else {
            specificationDao.updateSpecification(specification);
            return true;
        }
    }

    @Override
    public void deleteById(Integer specificationId) {
        specificationDao.deleteById(specificationId);
    }
}
