package ecjtu.husen.service;

import ecjtu.husen.dao.MaterialDao;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Material;
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
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialDao materialDao;

    @Override
    public boolean addMaterial(Material material) {
        Material material1 = materialDao.findByName(material.getMaterialName());
        if(material1 != null){
            return false;
        }else {
            materialDao.addMaterial(material);
            return true;
        }
    }

    @Override
    public Page<Material> page(Integer currentPage) {
        Page<Material> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = materialDao.findTotal();
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
        List<Material> materials = materialDao.page(currentPage, page.getPageSize());
        page.setContent(materials);
        return page;
    }

    @Override
    public Material findMaterialById(Integer materialId) {
        return materialDao.findById(materialId);
    }

    @Override
    public boolean updateMaterial(Material material) {
        Material material1 = materialDao.findByName(material.getMaterialName());
        if(material1 != null){
            return false;
        }else {
            materialDao.updateMaterial(material);
         return true;
        }
    }

    @Override
    public void deleteMaterial(Integer materialId) {
        materialDao.deleteMaterial(materialId);
    }

    @Override
    public Page<Material> findMaterial(Material material, Integer currentPage) {
        Page<Material> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = materialDao.findTotalWhen(material);
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
        List<Material> materials = materialDao.pageFind(currentPage, page.getPageSize(), material);
        page.setContent(materials);
        return page;
    }
}
