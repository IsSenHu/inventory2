package ecjtu.husen.service;

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
public class SportServiceImpl implements SportService {
    @Autowired
    private SportDao sportDao;
    @Override
    public boolean addSport(Sport sport) {
        Sport sport1 = sportDao.findSportbyName(sport.getSportName());
        if(sport1 != null){
            return false;
        }else {
            sportDao.addSport(sport);
            return true;
        }
    }

    @Override
    public Page<Sport> pageStudent(Integer currentPage) {
        Page<Sport> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = sportDao.findTotal();
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
        List<Sport> sports = sportDao.page(currentPage, page.getPageSize());
        page.setContent(sports);
        return page;
    }

    @Override
    public Sport findSportById(Integer sportId) {
        return sportDao.findSportById(sportId);
    }

    @Override
    public boolean updateSport(Sport sport) {
        /*
        * 判断输入的名称是否已经存在
        * */
        Sport sport1 = sportDao.findSportbyName(sport.getSportName());
        if(sport1 != null){
            return false;
        }else {
            sportDao.updateSport(sport);
            return true;
        }
    }

    @Override
    public void deleteSportById(Integer sportId) {
        sportDao.deleteSportById(sportId);
    }

    @Override
    public Page<Sport> findSport(Sport sport, Integer currentPage) {
        Page<Sport> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = sportDao.findTotalWhen(sport);
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
        List<Sport> sports = sportDao.pageFind(currentPage, page.getPageSize(), sport);
        page.setContent(sports);
        return page;
    }
}
