package ecjtu.husen.service;

import ecjtu.husen.dao.SportItemDao;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.SportItem;
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
public class SportItemServiceImpl implements SportItemService {
    @Autowired
    private SportItemDao sportItemDao;
    @Override
    public boolean addSportItem(SportItem sportItem) {
        SportItem sportItem1 = sportItemDao.findByName(sportItem.getSportItemName());
        if(sportItem1 != null){
            return false;
        }else {
            sportItemDao.addSportItem(sportItem);
            return true;
        }
    }

    @Override
    public Page<SportItem> pageSportItem(Integer currentPage) {
        Page<SportItem> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = sportItemDao.findTotal();
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
        List<SportItem> sportItems = sportItemDao.page(currentPage, page.getPageSize());
        page.setContent(sportItems);
        return page;
    }

    @Override
    public SportItem findSportItemById(Integer sportItemId) {
        return sportItemDao.findById(sportItemId);
    }

    @Override
    public boolean updateSportItem(SportItem sportItem) {
        if(sportItemDao.findByName(sportItem.getSportItemName()) != null){
            return false;
        }else {
            sportItemDao.updateSportItem(sportItem);
            return true;
        }
    }

    @Override
    public void deleteSportItemById(Integer sportItemId) {
        sportItemDao.deleteSportItem(sportItemId);
    }

    @Override
    public Page<SportItem> findSportItem(SportItem sportItem, Integer currentPage) {
        Page<SportItem> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = sportItemDao.findTotalWhen(sportItem);
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
        List<SportItem> sportItems = sportItemDao.pageFind(currentPage, page.getPageSize(), sportItem);
        page.setContent(sportItems);
        return page;
    }
}
