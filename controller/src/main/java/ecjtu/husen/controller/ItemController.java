package ecjtu.husen.controller;

import com.aliyun.oss.OSSClient;
import ecjtu.husen.pojo.DAO.*;
import ecjtu.husen.service.ItemService;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author husen
 */
@Controller
public class ItemController {
    private static final String BUCKET_NAMW = "oss-husen-test";
    private static final String END_POINT = "oss-cn-shenzhen.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIwfCaedv20UgG";
    private static final String SECRET_ACCESS_KEY = "viZURAskaBPvxeN8BC1jrrohxP8znf";
    @Autowired
    private ItemService itemService;

    /**
     * 转发到添加商品页面
     * @param currentPage
     * @return
     */
    @RequestMapping("/toAddItem.action")
    private ModelAndView toAddItem(@RequestParam(defaultValue = "1") Integer currentPage){
        /*
        * 要查出商品适用者，品牌，材质，规格，运动物品来。
        * */
        List<Applyer> applyers = itemService.findAllAppler();
        List<Brand> brands = itemService.findAllBrand();
        List<Material> materials = itemService.findAllMaterial();
        List<Specification> specifications = itemService.findAllSpecification();
        List<SportItem> sportItems = itemService.findAllSportItem();
        return new ModelAndView("views/jsp/addItem")
                .addObject("currentPage", currentPage)
                .addObject("applyers", applyers)
                .addObject("brands", brands)
                .addObject("materials", materials)
                .addObject("specifications", specifications)
                .addObject("sportItems", sportItems);
    }

    /**
     * 添加商品并进行验证
     * @param item
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/addItem.action", method = RequestMethod.POST)
    private ModelAndView addItem(MultipartFile pic, Item item, @RequestParam(defaultValue = "1") Integer currentPage) throws IOException {
        /*
        * 检验数据
        * 1，校验商品名称是否为空
        * 2，校验商品品牌是否选择
        * 3，当前库存不能被修改
        * 4，商品所属规格必须选择
        * 5，建议零售价不能为空，只能是数字
        * 6，实际单价不能为空，只能是数字
        * 7，适用对象必须选择
        * 8，上市时间不能为空
        * 9，材质必须选择
        * 10，图片地址有则保存，无则不保存
        * 11，属于哪个运动物品必须选择
        * A，用一个集合来存放所有的错误信息
        * */

        /*
        * 判断errors内如果有对象，则有错误，转发回输入页面
        * 要再把相关信息返回到输入页面
        * */
        Map<String, String> errors = validate(item);
        if(errors.size() > 0){
            List<Applyer> applyers = itemService.findAllAppler();
            List<Brand> brands = itemService.findAllBrand();
            List<Material> materials = itemService.findAllMaterial();
            List<Specification> specifications = itemService.findAllSpecification();
            List<SportItem> sportItems = itemService.findAllSportItem();
            return new ModelAndView("views/jsp/addItem")
                    .addObject("currentPage", currentPage)
                    .addObject("applyers", applyers)
                    .addObject("brands", brands)
                    .addObject("materials", materials)
                    .addObject("specifications", specifications)
                    .addObject("sportItems", sportItems)
                    .addObject("errors", errors);
        }
        /*
        * 数据输入正确，则进行添加
        * 添加之前要看看是否已有该商品
        * */
        Item item1 = itemService.findByName(item.getItemName());
        if(item1 != null){
            List<Applyer> applyers = itemService.findAllAppler();
            List<Brand> brands = itemService.findAllBrand();
            List<Material> materials = itemService.findAllMaterial();
            List<Specification> specifications = itemService.findAllSpecification();
            List<SportItem> sportItems = itemService.findAllSportItem();
            return new ModelAndView("views/jsp/addItem")
                    .addObject("currentPage", currentPage)
                    .addObject("applyers", applyers)
                    .addObject("brands", brands)
                    .addObject("materials", materials)
                    .addObject("specifications", specifications)
                    .addObject("sportItems", sportItems)
                    .addObject("msg", "该商品信息已存在！");
        }else {
            //判断是否有选择上传文件，如果有，则上传
            if(pic != null){
                String picPath = uploadFile(pic);
                if(StringUtils.isNotBlank(picPath)){
                    item.setPicPath(picPath);
                }
            }
            item.setCurrentInventory(0);
            itemService.addItem(item);
            return new ModelAndView("views/jsp/itemMsg")
                    .addObject("msg", "添加成功！")
                    .addObject("currentPage", currentPage);
        }
    }

    /**
     * 转发到修改商品信息页面，并不是所有的信息都可以修改
     * @param itemId
     * @param currentPage
     * @return
     */
    @RequestMapping("/showItem.action")
    private ModelAndView showItem(Integer itemId, @RequestParam(defaultValue = "1") Integer currentPage){
        Item item = itemService.findItemById(itemId);
        List<Applyer> applyers = itemService.findAllAppler();
        List<Brand> brands = itemService.findAllBrand();
        List<Material> materials = itemService.findAllMaterial();
        List<Specification> specifications = itemService.findAllSpecification();
        List<SportItem> sportItems = itemService.findAllSportItem();
        return new ModelAndView("views/jsp/updateItem")
                .addObject("item", item)
                .addObject("currentPage", currentPage)
                .addObject("applyers", applyers)
                .addObject("brands", brands)
                .addObject("materials", materials)
                .addObject("specifications", specifications)
                .addObject("sportItems", sportItems);
    }

    /**
     * 修改商品信息
     * @param item
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/updateItem.action", method = RequestMethod.POST)
    private ModelAndView updateItem(Item item, @RequestParam(defaultValue = "1") Integer currentPage){
        /*
        * 检验数据
        * */
        Map<String, String> errors = validate(item);
        Item item1 = itemService.findItemById(item.getItemId());
        if(errors.size() > 0){
            Item item2 = itemService.findItemById(item.getItemId());
            List<Applyer> applyers = itemService.findAllAppler();
            List<Brand> brands = itemService.findAllBrand();
            List<Material> materials = itemService.findAllMaterial();
            List<Specification> specifications = itemService.findAllSpecification();
            List<SportItem> sportItems = itemService.findAllSportItem();
            return new ModelAndView("views/jsp/updateItem")
                    .addObject("item", item1)
                    .addObject("currentPage", currentPage)
                    .addObject("errors", errors)
                    .addObject("item", item2)
                    .addObject("applyers", applyers)
                    .addObject("brands", brands)
                    .addObject("materials", materials)
                    .addObject("specifications", specifications)
                    .addObject("sportItems", sportItems);
        }
        //如果不等于之前的名字，则去查询是否已存在输入的商品名称
        if(!item.getItemName().equals(item1.getItemName())){
            Item item2 = itemService.findByName(item.getItemName());
            //如果返回结果不为空，则说明已有该商品名称存在
            if(item2 != null){
                Item item3 = itemService.findItemById(item.getItemId());
                List<Applyer> applyers = itemService.findAllAppler();
                List<Brand> brands = itemService.findAllBrand();
                List<Material> materials = itemService.findAllMaterial();
                List<Specification> specifications = itemService.findAllSpecification();
                List<SportItem> sportItems = itemService.findAllSportItem();
                return new ModelAndView("views/jsp/updateItem")
                        .addObject("msg", "该商品名已存在！")
                        .addObject("currentPage", currentPage)
                        .addObject("item", item3)
                        .addObject("applyers", applyers)
                        .addObject("brands", brands)
                        .addObject("materials", materials)
                        .addObject("specifications", specifications)
                        .addObject("sportItems", sportItems);
            }else {
                //否则直接保存
                itemService.updateItem(item);
                return new ModelAndView("views/jsp/itemMsg")
                        .addObject("msg", "修改成功！")
                        .addObject("currentPage", currentPage)
                        .addObject("itemId", item.getItemId());
            }
        }else {
            //如果等于之前的名字，直接保存
            itemService.updateItem(item);
            return new ModelAndView("views/jsp/itemMsg")
                    .addObject("msg", "修改成功！")
                    .addObject("currentPage", currentPage)
                    .addObject("itemId", item.getItemId());
        }
    }

    /**
     * 分页显示商品
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageItem.action")
    private ModelAndView pageItem(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<Item> page = itemService.pageItem(currentPage);
        List<Brand> brands = itemService.findAllBrand();
        List<SportItem> sportItems = itemService.findAllSportItem();
        return new ModelAndView("views/jsp/listItem")
                .addObject("page", page)
                .addObject("brands", brands)
                .addObject("sportItems", sportItems);
    }

    /**
     * 根据商品Id，商品名称，商品品牌，运动物品来查询
     * @param item
     * @param currentPage
     * @return
     */
    @RequestMapping("/findItem.action")
    private ModelAndView findItem(Item item, Integer brandId, Integer sportItemId, @RequestParam(defaultValue = "1") Integer currentPage){
        item.setBrand(new Brand());
        item.getBrand().setBrandId(brandId);
        item.setSportItem(new SportItem());
        item.getSportItem().setSportItemId(sportItemId);
        Page<Item> page = itemService.findItem(item, currentPage);
        List<Brand> brands = itemService.findAllBrand();
        List<SportItem> sportItems = itemService.findAllSportItem();
        return new ModelAndView("views/jsp/listItem")
                .addObject("page", page)
                .addObject("itemId", item.getItemId())
                .addObject("itemName", item.getItemName())
                .addObject("brandId", item.getBrand().getBrandId())
                .addObject("sprotItemId", item.getSportItem().getSportItemId())
                .addObject("find", "yes")
                .addObject("brands", brands)
                .addObject("sportItems", sportItems);
    }
    /**
     * 根据Id删除商品
     * @param itemId
     * @param currentPage
     * @return
     */
    @RequestMapping("/deleteItem.action")
    private ModelAndView deleteItem(Integer itemId, @RequestParam(defaultValue = "1") Integer currentPage){
        itemService.delete(itemId);
        return new ModelAndView("redirect:/pageItem.action?currentPage=" + currentPage);
    }

    /**
     * 转发到修改图片的页面
     * @param itemId
     * @param currentPage
     * @return
     */
    @RequestMapping("/showPic.action")
    private ModelAndView showPic(Integer itemId, @RequestParam(defaultValue = "1") Integer currentPage){
        Item item = itemService.findItemById(itemId);
        return new ModelAndView("views/jsp/showPic")
                .addObject("item", item)
                .addObject("currentPage", currentPage);
    }
    /**
     * 修改图片的方法
     * @param pic
     * @param item
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/updatePic.action", method = RequestMethod.POST)
    private ModelAndView updatePic(MultipartFile pic, Item item, @RequestParam(defaultValue = "1") Integer currentPage) throws IOException {
        if(pic != null){
            String picPath = uploadFile(pic);
            if(StringUtils.isNotBlank(picPath)){
                itemService.uploadPic(picPath, item);
                return new ModelAndView("views/jsp/itemMsg")
                        .addObject("msg", "修改图片成功！")
                        .addObject("currentPage", currentPage);
            }
        }
        return new ModelAndView("views/jsp/showPic")
                .addObject("msg", "请选择图片！")
                .addObject("currentPage", currentPage)
                .addObject("item", item);
    }
    /**
     * 封装校验数据的方法
     * @param item
     * @return 错误信息的集合
     */
    private static Map<String, String> validate(Item item){
        Map<String, String> errors = new HashMap<>(20);
        //检验商品名字是否为空
        if(StringUtils.isBlank(item.getItemName())){
            errors.put("itemNameError", "商品名称为空！");
        }
        //校验商品品牌是否选择
        if(item.getBrand() == null || item.getBrand().getBrandId() == null){
            errors.put("brandError", "必须选择商品品牌！");
        }
        //检验商品规格是否选择
        if(item.getSpecification() == null || item.getSpecification().getSpecificationId() == null){
            errors.put("specificationError", "必须选择商品规格！");
        }
        //检验建议零售价
        if(item.getSuggestRetailPrice() == null){
            errors.put("suggestRetailPriceError", "建议零售价不能为空！");
        }
        //检验适用对象是否选择
        if(item.getApplyer() == null || item.getApplyer().getApplyerId() == null){
            errors.put("applyerError", "必须选择适用者！");
        }
        //检验上市时间
        if(item.getTimeToMarket() == null){
            errors.put("timeToMarketError", "上市时间不能为空！");
        }
        //检验材质是否选择
        if(item.getMaterial() == null || item.getMaterial().getMaterialId() == null){
            errors.put("materialError", "必须选择材质！");
        }
        //检验所属运动物品是否选择
        if(item.getSportItem() == null || item.getSportItem().getSportItemId() == null){
            errors.put("sportItemError", "必须选择运动物品！");
        }
        return errors;
    }

    /**
     * 封装一个上传文件的方法
     * @param multipartFile
     */
    private static String uploadFile(MultipartFile multipartFile) throws IOException {
        //上传照片
        if(multipartFile != null){
            //原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            //上传图片
            String newFileName = null;
            if(multipartFile != null && StringUtils.isNotBlank(originalFilename)){
                //新的图片名称
                newFileName = UUID.randomUUID() +
                        originalFilename.substring(originalFilename.lastIndexOf("."));
                String filePath = LocalDateTime.now().getMonth().toString() + "/" + newFileName;
                //在OSS上存储图片的地址
                String picPath = "http://" + BUCKET_NAMW + "." + END_POINT + "/" + filePath;
                OSSClient ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
                ossClient.putObject(BUCKET_NAMW, filePath, multipartFile.getInputStream());
                ossClient.shutdown();
                return picPath;
            }
        }
        return null;
    }
}
