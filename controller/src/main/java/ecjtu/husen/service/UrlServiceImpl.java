package ecjtu.husen.service;

import ecjtu.husen.dao.UrlDao;
import ecjtu.husen.pojo.DAO.Url;
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
public class UrlServiceImpl implements UrlService {
    @Autowired
    private UrlDao urlDao;

    @Override
    public void addUrl(Url url) {
        urlDao.add(url);
    }

    @Override
    public Page<Url> page(Integer currentPage) {
        Page<Url> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = urlDao.findTotal();
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
        List<Url> urls = urlDao.page(currentPage, page.getPageSize());
        page.setContent(urls);
        return page;
    }

    @Override
    public Url findById(Integer urlId) {
        return urlDao.findById(urlId);
    }

    @Override
    public void update(Url url) {
        urlDao.update(url);
    }

    @Override
    public void delete(Integer urlId) {
        /*
        * 删除一个地址
        * 1，要先删除拥有该地址的权限地址关系
        * 2,在删除该地址
        * */
        urlDao.deletePermissionUrl(urlId);
        urlDao.deleteById(urlId);
    }
}
