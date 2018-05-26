package ecjtu.husen.service;

import ecjtu.husen.dao.ApplyerDao;
import ecjtu.husen.dao.SportDao;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Sport;
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
public class ApplyerServiceImpl implements ApplyerService {
    @Autowired
    private ApplyerDao applyerDao;
    @Autowired
    private SportDao sportDao;
    @Override
    public boolean addApplyer(Applyer applyer) {
        /*
        * 在添加之前首先要判断该名称是否已经存在了
        * */
        Applyer applyer1 = applyerDao.findApplyerByName(applyer.getApplyerName());
        if(applyer1 != null && applyer.getSport().getSportId().equals(applyer1.getSport().getSportId())){
            return false;
        }else {
            applyerDao.addApplyer(applyer);
            return true;
        }
    }

    @Override
    public Page<Applyer> pageApplyer(Integer currentPage) {
        Page<Applyer> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = applyerDao.findTotal();
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
        List<Applyer> applyers = applyerDao.page(currentPage, page.getPageSize());
        page.setContent(applyers);
        return page;
    }

    @Override
    public Applyer findApplyerById(Integer applyerId) {
        return applyerDao.findApplyerById(applyerId);
    }

    @Override
    public boolean updateApplyer(Applyer applyer) {
        /*
        * 判断这次输入的名称是否已经存在
        * select * from t_applyer where applyerName = ?
        * 如果没有查出，说明名称不存在，可以添加
        * */
        Applyer applyer1 = applyerDao.findApplyerByName(applyer.getApplyerName());
        if(applyer1 != null && applyer.getSport().getSportId().equals(applyer1.getSport().getSportId())){
            return false;
        }else {
            applyerDao.saveApplyer(applyer);
            return true;
        }
    }

    @Override
    public void deleteApplyerById(Integer applyerId) {
        applyerDao.deleteApplyerById(applyerId);
    }

    @Override
    public List<Sport> findAllSport() {
        return sportDao.findAllSport();
    }

    @Override
    public Page<Applyer> findApplyer(Applyer applyer, Integer currentPage) {
        Page<Applyer> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = applyerDao.findTotalWhen(applyer);
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
        List<Applyer> applyers = applyerDao.pageFind(currentPage, page.getPageSize(), applyer);
        page.setContent(applyers);
        return page;
    }
}
