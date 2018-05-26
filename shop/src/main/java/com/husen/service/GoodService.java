package com.husen.service;

import com.husen.model.Boss;
import com.husen.model.Coupon;
import com.husen.model.Good;
import com.husen.model.ShopPromotions;
import com.husen.util.JsonResult;
import com.husen.util.Page;
import com.husen.vo.*;
import ecjtu.husen.pojo.DAO.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 11785
 */
public interface GoodService {
    Page<Item> findItems(Integer currentPage, Integer pageSize, FindItem findItem);

    List<Brand> findAllBrand();

    List<SportItem> findAllSportItem();

    List<Applyer> findAllApplyer();

    List<Material> findAllMaterial();

    List<Specification> findAllSpecification();

    Item getItemById(Integer itemId);

    Good save(Good good, Boss boss);

    org.springframework.data.domain.Page<Good> findGood(Integer currentPage, String goodName, Integer bossId);

    Good findById(Integer goodId);

    Good update(GoodSecondVO goodVO);

    void saveDesPic(Integer goodId, String desPic);

    List<String> otherPics(Integer goodId);

    void saveOtherPic(Integer goodId, String picPath);

    String deletepic(Integer goodId, Integer index);

    List<Coupon> coupons(Integer goodId);

    void addCoupon(Integer goodId, CouponVO couponVO);

    void deleteCoupon(Integer id);

    List<ShopPromotions> promotions(Integer goodId);

    void savePromotion(Integer goodId, PromotionVO promotionVO);

    void deletePromotion(Integer spId);

    PostageVO showPostage(Integer goodId);

    void updatePostage(Integer goodId, PostageVO postageVO);

    JsonResult upGoodById(Integer goodId);

    JsonResult downGoodById(Integer goodId);
}
