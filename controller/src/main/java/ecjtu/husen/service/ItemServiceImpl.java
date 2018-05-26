package ecjtu.husen.service;

import ecjtu.husen.dao.*;
import ecjtu.husen.pojo.DAO.*;
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
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ApplyerDao applyerDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SportItemDao sportItemDao;
    @Override
    public List<Applyer> findAllAppler() {
        return applyerDao.findAll();
    }

    @Override
    public List<Brand> findAllBrand() {
        return brandDao.findAll();
    }

    @Override
    public List<Material> findAllMaterial() {
        return materialDao.findAll();
    }

    @Override
    public List<Specification> findAllSpecification() {
        return specificationDao.findAll();
    }

    @Override
    public List<SportItem> findAllSportItem() {
        return sportItemDao.findAll();
    }

    @Override
    public boolean addItem(Item item) {
        Item item1 = itemDao.findByName(item.getItemName());
        if(item1 != null){
            return false;
        }else {
            itemDao.addItem(item);
            return true;
        }
    }

    @Override
    public Item findItemById(Integer itemId) {
        return itemDao.findById(itemId);
    }

    @Override
    public void updateItem(Item item) {
        itemDao.updateItem(item);
    }

    @Override
    public Item findByName(String itemName) {
        return itemDao.findByName(itemName);
    }

    @Override
    public Page<Item> pageItem(Integer currentPage) {
        Page<Item> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = itemDao.findTotal();
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
        List<Item> items = itemDao.page(currentPage, page.getPageSize());
        page.setContent(items);
        return page;
    }

    @Override
    public Page<Item> findItem(Item item, Integer currentPage) {
        Page<Item> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = itemDao.findTotalWhen(item);
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
        List<Item> items = itemDao.pageFind(currentPage, page.getPageSize(), item);
        page.setContent(items);
        return page;
    }

    @Override
    public void delete(Integer itemId) {
        itemDao.deleteItem(itemId);
    }

    @Override
    public void uploadPic(String picPath, Item item) {
        itemDao.uploadPic(picPath, item);
    }
}
