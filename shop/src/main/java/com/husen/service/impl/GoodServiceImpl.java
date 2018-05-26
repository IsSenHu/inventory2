package com.husen.service.impl;

import com.alibaba.fastjson.JSON;
import com.husen.dao.GoodDao;
import com.husen.dao.ShopPromotionsDao;
import com.husen.dao.StoresDao;
import com.husen.model.*;
import com.husen.service.GoodService;
import com.husen.dao.CouponDao;
import com.husen.util.GlobalVar;
import com.husen.util.HttpService;
import com.husen.util.JsonResult;
import com.husen.util.Page;
import com.husen.vo.*;
import ecjtu.husen.pojo.DAO.*;
import ecjtu.husen.pojo.DAO.Item;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author 11785
 */
@Service
@Transactional
public class GoodServiceImpl implements GoodService {
    private static Logger logger = LogManager.getLogger(GoodServiceImpl.class);
    @Autowired
    private StoresDao storesDao;
    @Autowired
    private GoodDao goodDao;
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private ShopPromotionsDao shopPromotionsDao;

    @Override
    public Page<Item> findItems(Integer currentPage, Integer pageSize, FindItem findItem) {
        Page<Item> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //设置每页数量
        page.setPageSize(pageSize);
        /*
        * 查询出总记录数，并设置
        * */

        try {
            String count = HttpService.getData(getAddress(findItem, "http://localhost:8080/ws/item/countItems"));
            page.setRowsTotal(Integer.valueOf(count));
            /*
            * 开始计算总共有多少页并进行设置
            * */
            int rowsTotal = Integer.valueOf(count);
            int totalPage = (rowsTotal % pageSize) == 0 ? (rowsTotal / pageSize) : ((rowsTotal / pageSize) + 1);
            page.setTotalPage(totalPage);
            /*
            * 判断传入的当前页是否合法
            * */
            if(currentPage <= 0){
                currentPage = 1;
                page.setCurrentPage(currentPage);
            }else if (currentPage > page.getTotalPage()){
                currentPage = page.getTotalPage();
                page.setCurrentPage(currentPage);
            }else if(currentPage == null){
                currentPage = 1;
                page.setCurrentPage(currentPage);
            }
            /*
            * 判断传入的每页大小时候合法
            * */
            if(pageSize > 50){
                pageSize = 50;
                page.setPageSize(pageSize);
            }else if(pageSize < 10){
                pageSize = 10;
                page.setPageSize(pageSize);
            }else if(pageSize == null){
                pageSize = 20;
                page.setPageSize(pageSize);
            }

            String itemsAddress =  getAddress(findItem, "http://localhost:8080/ws/item/items/" + currentPage + "/" + pageSize);
            logger.info("查询物品的接口地址为:{}" + itemsAddress);
            String findItems = HttpService.getData(itemsAddress);
            List<Item> items = JSON.parseArray(findItems, Item.class);
            page.setContent(items);
            return page;
        } catch (IOException e) {
            logger.error("调用接口失败:{}" + e.getMessage());
            return page;
        }
    }

    @Override
    public List<Brand> findAllBrand() {
        try {
            logger.info("调用接口查询所有品牌成功");
            return JSON.parseArray(HttpService.getData("http://localhost:8080/ws/item/brands"), Brand.class);
        } catch (IOException e) {
            logger.error("调用接口查询所有品牌失败" + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<SportItem> findAllSportItem() {
        try {
            logger.info("调用接口查询所有运动物品成功");
            return JSON.parseArray(HttpService.getData("http://localhost:8080/ws/item/sportItems"), SportItem.class);
        } catch (IOException e) {
            logger.error("调用接口查询所有运动物品失败" + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Applyer> findAllApplyer() {
        try {
            logger.info("调用接口查询所有适用者成功");
            return JSON.parseArray(HttpService.getData("http://localhost:8080/ws/item/applyers"), Applyer.class);
        } catch (IOException e) {
            logger.error("调用接口查询所有适用者失败" + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Material> findAllMaterial() {
        try {
            logger.info("调用接口查询所有材质成功");
            return JSON.parseArray(HttpService.getData("http://localhost:8080/ws/item/materials"), Material.class);
        } catch (IOException e) {
            logger.error("调用接口查询所有材质失败" + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Specification> findAllSpecification() {
        try {
            logger.info("调用接口查询所有规格成功");
            return JSON.parseArray(HttpService.getData("http://localhost:8080/ws/item/specifications"), Specification.class);
        } catch (IOException e) {
            logger.error("调用接口查询所有规格失败" + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Item getItemById(Integer itemId) {
        try {
            String item = HttpService.getData("http://localhost:8080/ws/item/itemById/" + itemId);
            logger.info("调用接口查询Item成功:{}" + item);
            return JSON.parseObject(item, Item.class);
        } catch (IOException e) {
            logger.info("调用接口查询Item失败:{}" + e.getMessage());
            return null;
        }
    }

    @Override
    public Good save(Good good, Boss boss) {
        if(!boss.getIsAuthenticate().equals(GlobalVar.HAVE_AUTHENTICATE)){
            logger.info("商家还未被认证");
            return null;
        }
        try {
            //设置商品状态为下架
            good.setIsShelves(GlobalVar.DOWN);
            //设置商品所属的店铺
            Stores stores = storesDao.findByBossBossId(boss.getBossId());
            if(stores == null){
                return null;
            }
            logger.info("该店铺的Id为:{}" + stores.getStoresId());
            good.setStores(stores);
            //设置累计销量为0
            good.setAccumulatedSales(0);
            //设置月销量为0
            good.setMonthlySales(0);
            //邮费默认包邮
            good.setIsPostage(GlobalVar.NO_POSTAGE);
            //设置商品价格
            good.setLastPrice(good.getSalePrice() == null ? good.getOriginalPrice() : good.getSalePrice());
            //保存商品
            logger.info("保存成功:{}" + good);
            return goodDao.save(good);
        }catch (Exception e){
            logger.info("保存失败");
            return null;
        }
    }

    @Override
    public org.springframework.data.domain.Page<Good> findGood(Integer currentPage, String goodName, Integer bossId) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "goodId");
        Sort sort = new Sort(order);
        //page:index是从0开始的，不是从1开始的
        Pageable pageable = new PageRequest(currentPage - 1, 12, sort);
        org.springframework.data.domain.Page<Good> page = null;
        if(StringUtils.isBlank(goodName)){
            page = goodDao.findAll(pageable);
        }else {
            Stores stores = storesDao.findByBossBossId(bossId);
            page = goodDao.findAllByGoodNameAndStoresStoresId(goodName, stores.getStoresId(), pageable);
        }
        return page;
    }

    @Override
    public Good findById(Integer goodId) {
        return goodDao.findById(goodId).get();
    }

    @Override
    public Good update(GoodSecondVO goodVO) {
        Good good = goodDao.findById(goodVO.getGoodId()).get();
        good.setGoodName(goodVO.getGoodName());
        good.setOriginalPrice(goodVO.getOriginalPrice());
        good.setSalePrice(goodVO.getSalePrice());
        good.setLastPrice(goodVO.getSalePrice() == null ? goodVO.getOriginalPrice() : goodVO.getSalePrice());
        return goodDao.save(good);
    }

    @Override
    public void saveDesPic(Integer goodId, String desPic) {
        Good good = goodDao.findById(goodId).get();
        good.setDetailpic(desPic);
        goodDao.save(good);
    }

    @Override
    public List<String> otherPics(Integer goodId) {
        Good good = goodDao.findById(goodId).get();
        if(good == null){
            return new ArrayList<>();
        }else {
            String pisc = good.getPisc();
            if(StringUtils.isBlank(pisc)){
                return new ArrayList<>();
            }
            List<String> pics = Arrays.asList(pisc.split("connectionRegex"));
            return pics;
        }
    }

    @Override
    public void saveOtherPic(Integer goodId, String picPath) {
        Good good = goodDao.findById(goodId).get();
        if(good == null){
            throw new RuntimeException("没有该数据！");
        }
        String pisc = good.getPisc();
        if(StringUtils.isBlank(pisc)){
            pisc = picPath;
        }else {
            pisc = pisc + "connectionRegex" + picPath;
        }
        logger.info("新的图片字符串为：{}", pisc);
        good.setPisc(pisc);
        goodDao.save(good);
    }

    @Override
    public String deletepic(Integer goodId, Integer index) {
        Good good = goodDao.findById(goodId).get();
        List<String> pics = Arrays.asList(good.getPisc().split("connectionRegex"));
        List<String> newPics = new ArrayList<>();
        String oldPic = null;
        for (int i = 0; i < pics.size(); i++){
            if(!index.equals(i)){
                newPics.add(pics.get(i));
            }else {
                oldPic = pics.get(i);
            }
        }
        StringBuilder newPisc = new StringBuilder("");
        for (int i = 0; i < newPics.size(); i++){
            if(i == 0){
                newPisc.append(newPics.get(i));
            }else {
                newPisc.append("connectionRegex").append(newPics.get(i));
            }
        }
        good.setPisc(newPisc.toString());
        goodDao.save(good);
        return oldPic;
    }

    @Override
    public List<Coupon> coupons(Integer goodId) {
        return couponDao.findAllByGood_GoodId(goodId);
    }

    @Override
    public void addCoupon(Integer goodId, CouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setGood(goodDao.findById(goodId).get());
        coupon.setNeed(couponVO.getNeed());
        coupon.setFree(couponVO.getFree());
        coupon.setExpiryTime(new Date(System.currentTimeMillis() + couponVO.getExpiryTimeLong() * 24 * 60 * 60 * 1000));
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH点mm时ss秒");
        coupon.setExpiryTimeStr(format.format(coupon.getExpiryTime()));
        couponDao.save(coupon);
    }

    @Override
    public void deleteCoupon(Integer id) {
        couponDao.deleteById(id);
    }

    @Override
    public List<ShopPromotions> promotions(Integer goodId) {
        return shopPromotionsDao.findAllByGood_GoodId(goodId);
    }

    @Override
    public void savePromotion(Integer goodId, PromotionVO promotionVO) {
        ShopPromotions shopPromotions = new ShopPromotions();
        shopPromotions.setGood(goodDao.findById(goodId).get());
        shopPromotions.setNumber(promotionVO.getNumber());
        shopPromotions.setDiscount(promotionVO.getDiscount());
        shopPromotionsDao.save(shopPromotions);
    }

    @Override
    public void deletePromotion(Integer spId) {
        shopPromotionsDao.deleteById(spId);
    }

    @Override
    public PostageVO showPostage(Integer goodId) {
        Good good = goodDao.findById(goodId).get();
        PostageVO postageVO = new PostageVO(good.getIsPostage(), good.getPostageMoney());
        return postageVO;
    }

    @Override
    public void updatePostage(Integer goodId, PostageVO postageVO) {
        Good good = goodDao.findById(goodId).get();
        good.setIsPostage(postageVO.getIsPostage());
        good.setPostageMoney(postageVO.getPostageMoney());
        goodDao.save(good);
    }

    @Override
    public JsonResult upGoodById(Integer goodId) {
        Good good = goodDao.findById(goodId).get();
        good.setIsShelves(GlobalVar.UP);
        goodDao.save(good);
        return new JsonResult("200", "上架成功", null);
    }

    @Override
    public JsonResult downGoodById(Integer goodId) {
        Good good = goodDao.findById(goodId).get();
        good.setIsShelves(GlobalVar.DOWN);
        goodDao.save(good);
        return new JsonResult("200", "下架成功", null);
    }

    private String getAddress(FindItem findItem, String address){
        String applyerId = findItem.getApplyerId();
        applyerId = (applyerId == null || applyerId.length() == 0) ? "0" : applyerId.trim();
        String brandId = findItem.getBrandId();
        brandId = (brandId == null || brandId.length() == 0) ? "0" : brandId.trim();
        String materialId = findItem.getMaterialId();
        materialId = (materialId == null || materialId.length() == 0) ? "0" : materialId.trim();
        String specificationId = findItem.getSpecificationId();
        specificationId = (specificationId == null || specificationId.length() == 0) ? "0" : specificationId.trim();
        String sportItemId = findItem.getSportItemId();
        sportItemId = (sportItemId == null || sportItemId.length() == 0) ? "0" : sportItemId.trim();
        address = address + "/" + applyerId + "/" + brandId + "/" + materialId + "/" + specificationId + "/" + sportItemId;
        logger.info("接口地址为:{}" + address);
        return address;
    }
}
