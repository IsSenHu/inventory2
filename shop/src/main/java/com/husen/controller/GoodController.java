package com.husen.controller;

import com.alibaba.fastjson.JSON;
import com.husen.model.Boss;
import com.husen.model.Coupon;
import com.husen.model.Good;
import com.husen.model.ShopPromotions;
import com.husen.service.GoodService;
import com.husen.util.HttpService;
import com.husen.util.JsonResult;
import com.husen.util.Page;
import com.husen.vo.*;
import ecjtu.husen.pojo.DAO.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11785
 */
@Controller
public class GoodController {
    private static Logger logger = LogManager.getLogger(GoodController.class);
    @Autowired
    private GoodService goodService;

    /**
     * 调用WebService分页显示库存系统的物品
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageItem.do")
    private ModelAndView pageItem(@RequestParam(defaultValue = "1") Integer currentPage,
                        @RequestParam(defaultValue = "20") Integer pageSize,
                        FindItem findItem){
        logger.info("请求的页:{}" + currentPage);
        logger.info("请求的数量:{}" + pageSize);
        logger.info("请求的条件:{}" + findItem);
        Page<Item> page = goodService.findItems(currentPage, pageSize, findItem);
        List<Brand> brands = goodService.findAllBrand();
        List<SportItem> sportItems = goodService.findAllSportItem();
        List<Applyer> applyers = goodService.findAllApplyer();
        List<Material> materials = goodService.findAllMaterial();
        List<Specification> specifications = goodService.findAllSpecification();
        return new ModelAndView("jsp/views/listItem")
                .addObject("page", page)
                .addObject("brands", brands)
                .addObject("sportItems", sportItems)
                .addObject("applyers", applyers)
                .addObject("materials", materials)
                .addObject("specifications", specifications)
                .addObject("findItem", findItem);
    }

    /**
     * 跳转到新增商品页面
     * @param request
     * @param itemId
     * @return
     */
    @RequestMapping("/toAddGood.do")
    private ModelAndView toAddGood(HttpServletRequest request, Integer itemId, @RequestParam(defaultValue = "1") Integer currentPage){
        HttpSession session = request.getSession();
        Boss boss = (Boss) session.getAttribute("boss");
        if(boss == null){
            logger.info("用户没有登陆");
            return new ModelAndView("redirect:/toLogin.do");
        }
        logger.info("得到的itemId:{}" + itemId);
        itemId = itemId == null ? 1 : itemId;

        Item item = goodService.getItemById(itemId);
        logger.info("调用接口查询到的Item:{}" + item);
        if(item == null){
            logger.info("调用接口查询Item失败:{}" + item);
            return new ModelAndView("redirect:/pageItem.do?currentPage=" + currentPage);
        }
        return new ModelAndView("jsp/views/addGood")
                .addObject("item", item)
                .addObject("currentPage", currentPage);
    }

    @RequestMapping("/addGood.do")
    private ModelAndView addGood(HttpServletRequest request, Good good, @RequestParam(defaultValue = "1") Integer currentPage){
        HttpSession session = request.getSession();
        Boss boss = (Boss) session.getAttribute("boss");
        if(boss == null){
            logger.info("用户没有登陆");
            return new ModelAndView("redirect:/toLogin.do");
        }
        logger.info("接收到的商品信息为:{}" + good);

        /*
        * 校验输入信息
        * */
        Map<String, String> errors = new HashMap<>(3);
        if(StringUtils.isBlank(good.getGoodName())){
            errors.put("goodNameError", "商品名称为空");
        }
        if(good.getOriginalPrice() == null){
            errors.put("opError", "原价不能为空");
        }
        if(errors.size() > 0){
            return new ModelAndView("forward:/toAddGood.do?itemId=" + good.getItemId() + "&currentPage=" + currentPage)
                    .addObject("errors", errors)
                    .addObject("currentPage", currentPage);
        }
        /*
        * 开始保存商品信息
        * 保存成功则跳转到商品详情页面
        * */
        if(good.getSalePrice() == null){
            good.setSalePrice(good.getOriginalPrice());
        }
        Good result = goodService.save(good, boss);
        if(result != null){
            logger.info("保存成功:{}" + good);
            Item item = goodService.getItemById(good.getItemId());
            return new ModelAndView("jsp/views/detail")
                    .addObject("item", item)
                    .addObject("good", result)
                    .addObject("msg", "保存成功");
        }else {
            logger.info("保存失败:{}" + good);
            return new ModelAndView("forward:/toAddGood.do?itemId=" + good.getItemId() + "&currentPage=" + currentPage)
                    .addObject("msg", "保存失败")
                    .addObject("currentPage", currentPage);
        }
    }

    /**
     * 根据名称去查找商品列表
     * @param currentPage
     * @param goodName
     * @param request
     * @return
     */
    @RequestMapping("/findGood.do")
    private ModelAndView findGood(@RequestParam(defaultValue = "1") Integer currentPage, String goodName, HttpServletRequest request){
        HttpSession session = request.getSession();
        Boss boss = (Boss) session.getAttribute("boss");
        Integer bossId = null;
        if(boss != null && boss.getBossId() != null){
            bossId  = boss.getBossId();
        }else {
            return new ModelAndView("redirect:/toLogin.do");
        }
        org.springframework.data.domain.Page<Good> page = goodService.findGood(currentPage, goodName, bossId);
        List<GoodVO> goodVOS = new ArrayList<>();
        /**
         * 流式API过滤掉不是该boss的商品再进行遍历
         * Lambda表达式
         */
        page.getContent().stream()
                .forEach(good -> {
                    Integer itemId = good.getItemId();
                    logger.info("根据ItemId调用接口去查询：{}", itemId);
                    try {
                        String item = HttpService.getData("http://localhost:8080/ws/item/itemById/" + itemId);
                        logger.info("调用接口查询Item成功:{}" + item);
                        Item itemFinded =  JSON.parseObject(item, Item.class);
                        GoodVO goodVO = new GoodVO(good, itemFinded);
                        goodVOS.add(goodVO);
                    } catch (IOException e) {
                        logger.error("调用接口查询Item错误：{}", e.getMessage());
                    }
                });
        Page<GoodVO> voPage = new Page<>();
        voPage.setContent(goodVOS);
        voPage.setCurrentPage(page.getNumber() + 1);
        voPage.setPageSize(page.getSize());
        voPage.setTotalPage(page.getTotalPages());
        voPage.setRowsTotal(((Long)page.getTotalElements()).intValue());
        return new ModelAndView("jsp/views/listGood")
                .addObject("page", voPage)
                .addObject("goodName", goodName);
    }

    /**
     * 根据id查询某个商品的详情
     * @param goodId
     * @param currentPage
     * @return
     */
    @RequestMapping("/desGood.do")
    private ModelAndView desGood(@RequestParam(defaultValue = "1") Integer goodId, @RequestParam(defaultValue = "1") Integer currentPage){
        Good good = goodService.findById(goodId);
        String item = null;
        Item itemFinded = null;
        try {
            item = HttpService.getData("http://localhost:8080/ws/item/itemById/" + good.getItemId());
            logger.info("调用接口查询Item成功:{}" + item);
            itemFinded =  JSON.parseObject(item, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoodVO goodVO = new GoodVO(good, itemFinded);
        return new ModelAndView("jsp/views/detail")
                .addObject("good", goodVO)
                .addObject("currentPage", currentPage);
    }

    /**
     * 根据id查找商品
     * @param goodId
     * @return
     */
    @RequestMapping("/findGoodById.do")
    private @ResponseBody Good findGoodById(Integer goodId){
        if(goodId != null){
            Good good = goodService.findById(goodId);
            return good;
        }else {
            throw new RuntimeException("没有传goodId");
        }
    }

    /**
     * 修改商品的信息
     * @param goodVO
     * @return
     */
    @RequestMapping("/saveGoodInfo.do")
    private @ResponseBody Result<Good> saveGoodInfo(GoodSecondVO goodVO){
        logger.info("接收到的商品信息为：{}", goodVO.toString());
        try {
            if(!checkGood(goodVO)){
                return new Result<>(400, "输入错误！", null);
            }else {
                if(goodVO.getSalePrice() == null){
                    goodVO.setSalePrice(goodVO.getOriginalPrice());
                }
                Good good = goodService.update(goodVO);
                logger.info("修改成功：{}", good.toString());
                return new Result<>(200, "修改成功！", good);
            }
        }catch (Exception e){
            logger.error("未知错误：{}", e.getMessage());
            return new Result<>(500, "未知错误！", null);
        }
    }

    /**
     * 修改商品详情图片
     * @param goodId
     * @param pic
     * @return
     */
    @RequestMapping("/saveDesPic.do")
    private @ResponseBody Result<String> saveDesPic(Integer goodId, MultipartFile pic){
        if(pic != null){
            try {
                String picPath = StoresController.uploadFile(pic);
                logger.info("上传成功：{}", picPath);
                goodService.saveDesPic(goodId, picPath);
                return new Result<>(200, "修改成功！", picPath);
            } catch (IOException e) {
                logger.error("上传失败：{}", e.getMessage());
                return new Result<>(500, "位置错误！", null);
            }
        }else {
            return new Result<>(400, "没有选择文件！", null);
        }
    }

    /**
     * 查询出该商品的所有其他图片
     * @param goodId
     * @return
     */
    @RequestMapping("/otherPics.do")
    private @ResponseBody Result<List<String>> otherPics(Integer goodId){
        if(goodId == null){
            logger.info("没有传入Id：{}", goodId);
            return new Result<>(400, "没有传入ID！", null);
        }else {
            try {
                List<String> pics = goodService.otherPics(goodId);
                logger.info("查询到其他图片数量为：{}", pics.size());
                return new Result<>(200, "查询成功！", pics);
            }catch (Exception e){
                logger.error("未知错误：{}", e.getMessage());
                return new Result<>(500, "未知错误！", null);
            }
        }
    }

    /**
     * 新增商品其他图片
     * @param goodId
     * @param pic
     * @return
     */
    @RequestMapping("/saveOtherPic.do")
    private @ResponseBody Result<String> saveOtherPic(Integer goodId, MultipartFile pic){
        if(goodId == null){
            logger.info("没有传入Id：{}", goodId);
            return new Result<>(400, "没有传入ID！", null);
        }
        if(pic == null){
            logger.info("没有选择图片：{}", goodId);
            return new Result<>(401, "没有选择图片！", null);
        }else {
            try {
                String picPath = StoresController.uploadFile(pic);
                logger.info("上传成功：{}", picPath);
                try {
                    logger.info("开始保存到数据库：{}", picPath);
                    goodService.saveOtherPic(goodId, picPath);
                    logger.info("保存成功：{}", picPath);
                    return new Result<>(200, "保存成功！", picPath);
                }catch (Exception e){
                    logger.error("未知错误！{}", e.getMessage());
                    return new Result<>(500, "未知错误！", null);
                }
            } catch (IOException e) {
                logger.error("上传失败：{}", e.getMessage());
                return new Result<>(501, "上传失败！", null);
            }
        }
    }

    /**
     * 根据id和index删除其他图片
     * @param goodId
     * @param index
     * @return
     */
    @RequestMapping("/deletepic.do")
    private @ResponseBody Result<String> deletepic(Integer goodId, Integer index){
        if(goodId == null || index == null){
            logger.info("没有Id或index{}" ,goodId + index);
            return new Result<>(400, "没有Id或index！", null);
        }else {
            try {
                String oldPic = goodService.deletepic(goodId, index);
                logger.info("删除成功：{}", oldPic);
                return new Result<>(200, "删除成功！", oldPic);
            }catch (Exception e){
                logger.error("未知错误：{}", e.getMessage());
                return new Result<>(500, "未知错误！", null);
            }
        }
    }

    /**
     * 根据商品ID查询优惠券
     * @param goodId
     * @return
     */
    @RequestMapping("/coupons.do")
    private @ResponseBody Result<List<CouponVO>> coupons(Integer goodId){
        if(goodId == null){
            logger.info("缺少必要的参数商品ID：{}", goodId);
            return new Result<>(400, "没有传goodId", null);
        }else {
            try {
                List<Coupon> coupons = goodService.coupons(goodId);
                logger.info("根据商品ID查询优惠券成功！:{}", coupons);
                List<CouponVO> couponVOS = new ArrayList<>();
                coupons.stream().forEach(x -> {
                    CouponVO couponVO = new CouponVO();
                    couponVO.setId(x.getId());
                    couponVO.setNeed(x.getNeed());
                    couponVO.setFree(x.getFree());
                    couponVO.setExpiryTimeStr(x.getExpiryTimeStr());
                    couponVO.setGoodName(x.getGood().getGoodName());
                    couponVOS.add(couponVO);
                });
                return new Result<>(200, "查询成功！", couponVOS);
            }catch (Exception e){
                logger.error("未知错误！：{}", e.getMessage());
                return new Result<>(500, "未知错误！", null);
            }
        }
    }

    /**
     * 添加优惠券
     * @param goodId
     * @param couponVO
     * @return
     */
    @RequestMapping("/addCoupon.do")
    private @ResponseBody Result<Integer> addCoupon(Integer goodId, CouponVO couponVO){
        if(goodId == null || couponVO == null){
            logger.info("缺少必要的参数！:{}", goodId);
            logger.info("缺少必要的参数！:{}", couponVO);
            return new Result<>(400, "缺少必要的参数！", goodId);
        }
        try {
            goodService.addCoupon(goodId, couponVO);
            logger.info("添加成功！:{}", goodId);
            return new Result<>(200, "添加优惠券成功！", goodId);
        }catch (Exception e){
            logger.error("未知错误！：{}", e.getMessage());
            return new Result<>(500, "未知错误！", goodId);
        }
    }

    /**
     * 根据Id删除优惠券
     * @param id
     * @return
     */
    @RequestMapping("/deleteCoupon.do")
    private @ResponseBody Result<Integer> deleteCoupon(Integer id){
        if(id == null){
            logger.info("缺少必要的参数：{}", id);
            return new Result<>(400, "缺少必要的参数", id);
        }
        try {
            goodService.deleteCoupon(id);
            logger.info("删除成功！：{}", id);
            return new Result<>(200, "删除成功！", id);
        }catch (Exception e){
            logger.error("未知错误！：{}", e.getMessage());
            return new Result<>(500, "未知错误！", id);
        }
    }

    /**
     * 根据商品ID查询折扣
     * @param goodId
     * @return
     */
    @RequestMapping("/promotions.do")
    private @ResponseBody Result<List<PromotionVO>> promotions(Integer goodId){
        if(goodId == null){
            logger.info("缺少必要的参数：{}", goodId);
            return new Result<>(400, "缺少必要的参数", null);
        }
        try {
            List<ShopPromotions> shopPromotions = goodService.promotions(goodId);
            logger.info("根据商品id查询折扣成功！:{}", shopPromotions);
            List<PromotionVO> promotionVOS = new ArrayList<>();
            shopPromotions.stream().forEach(x -> {
                PromotionVO promotionVO = new PromotionVO();
                promotionVO.setSpId(x.getSpId());
                promotionVO.setDiscount(x.getDiscount());
                promotionVO.setNumber(x.getNumber());
                promotionVO.setGoodName(x.getGood().getGoodName());
                promotionVOS.add(promotionVO);
            });
            return new Result<>(200, "查询成功！", promotionVOS);
        }catch (Exception e){
            logger.error("未知错误！：{}", e.getMessage());
            return new Result<>(500, "未知错误！", null);
        }
    }

    /**
     * 保存新的折扣
     * @param goodId
     * @param promotionVO
     * @return
     */
    @RequestMapping("/savePromotion.do")
    private @ResponseBody Result<Integer> savePromotion(Integer goodId, PromotionVO promotionVO){
        if(goodId == null || promotionVO == null){
            logger.info("缺少必要的参数：{}", goodId);
            return new Result<>(400, "缺少必要的参数", goodId);
        }
        try {
            goodService.savePromotion(goodId, promotionVO);
            logger.info("保存新的折扣成功：", goodId);
            return new Result<>(200, "添加成功！", goodId);
        }catch (Exception e){
            logger.error("未知错误：{}", e.getMessage());
            return new Result<>(500, "未知错误！", goodId);
        }
    }

    /**
     * 删除已有的折扣
     * @param spId
     * @return
     */
    @RequestMapping("/deletePromotion.do")
    private @ResponseBody Result<Integer> deletePromotion(Integer spId){
        if(spId == null){
            logger.info("缺少必要的参数：{}", spId);
            return new Result<>(400, "缺少必要的参数", spId);
        }
        try {
            goodService.deletePromotion(spId);
            logger.info("删除成功：{}", spId);
            return new Result<>(200, "删除成功！", spId);
        }catch (Exception e){
            logger.error("未知错误：{}", e.getMessage());
            return new Result<>(500, "未知错误", spId);
        }
    }

    /**
     * 根据商品id查询是否包邮
     * @param goodId
     * @return
     */
    @RequestMapping("/showPostage.do")
    private @ResponseBody Result<PostageVO> showPostage(Integer goodId){
        if(goodId == null){
            logger.info("缺少必要的参数：{}", goodId);
            return new Result<>(400, "缺少必要的参数", null);
        }
        try {
            PostageVO postageVO = goodService.showPostage(goodId);
            logger.info("查询成功：{}", postageVO);
            return new Result<>(200, "查询成功！", postageVO);
        }catch (Exception e){
            logger.error("未知错误：{}", e.getMessage());
            return new Result<>(500, "未知错误", null);
        }
    }

    /**
     * 修改是否需要邮费以及邮费是多少
     * @param goodId
     * @param postageVO
     * @return
     */
    @RequestMapping("/updatePostage.do")
    private @ResponseBody Result<String> updatePostage(Integer goodId, PostageVO postageVO){
        if(goodId == null || postageVO == null){
            logger.info("缺少必要的参数：{}", goodId);
            return new Result<>(400, "缺少必要的参数", null);
        }
        if(!checkPostage(postageVO)){
            logger.info("参数不合法：{}", postageVO);
            return new Result<>(401, "参数不合法", null);
        }
        try {
            goodService.updatePostage(goodId, postageVO);
            logger.info("修改成功：[]", goodId);
            return new Result<>(200, "修改成功！", postageVO.getIsPostage());
        }catch (Exception e){
            logger.error("未知错误：{}", e.getMessage());
            return new Result<>(500, "未知错误！", null);
        }
    }

    /**
     * 上架商品
     * @param goodId
     * @return
     */
    @PostMapping("/upGoodById.do")
    private @ResponseBody JsonResult upGoodById(Integer goodId){
        return goodService.upGoodById(goodId);
    }

    /**
     * 下架商品
     * @param goodId
     * @return
     */
    @PostMapping("/downGoodById.do")
    private @ResponseBody JsonResult downGoodById(Integer goodId){
        return goodService.downGoodById(goodId);
    }

    /**
     * 检查商品输入信息
     * @param goodSecondVO
     * @return
     */
    private static boolean checkGood(GoodSecondVO goodSecondVO){
        if(goodSecondVO == null){
            return false;
        }
        if(goodSecondVO.getGoodId() == null){
            return false;
        }
        if(StringUtils.isBlank(goodSecondVO.getGoodName())){
            return false;
        }
        if(goodSecondVO.getOriginalPrice() == null){
            return false;
        }
        return true;
    }

    private static boolean checkPostage(PostageVO postageVO){
        if(postageVO == null){
            return false;
        }
        if(StringUtils.isBlank(postageVO.getIsPostage())){
            return false;
        }
        if(postageVO.getPostageMoney() == null){
            return false;
        }
        return true;
    }
}
