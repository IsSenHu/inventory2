package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.Brand;
import ecjtu.husen.service.BrandService;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author husen
 */
@Controller
@RequiresPermissions(value = "BasicInfoManage")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 转发到添加品牌页面
     * @return
     */
    @RequestMapping("/toAddBrand.action")
    private ModelAndView toAddBrand(@RequestParam(defaultValue = "1") Integer currentPage){
        return new ModelAndView("views/jsp/addBrand").addObject("currentPage", currentPage);
    }

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @RequestMapping("/addBrand.action")
    private ModelAndView addBrand(Brand brand, @RequestParam(defaultValue = "1") Integer currentPage){
        Map<String, String> errors = new HashMap<>();
        if(StringUtils.isBlank(brand.getBrandName())){
            errors.put("brandNameError", "品牌名字不能为空！");
        }
        if(StringUtils.isBlank(brand.getBrandCompanyName())){
            errors.put("brandCompanyNameError", "公司名字不能为空！");
        }
        if(errors.size() > 0){
            return new ModelAndView("views/jsp/addBrand").addObject("errors", errors).addObject("currentPage",currentPage);
        }
        boolean result = brandService.addBrand(brand);
        if(!result){
            return new ModelAndView("views/jsp/addBrand").addObject("msg", "该品牌已存在！").addObject("currentPage",currentPage);
        }else {
            return new ModelAndView("views/jsp/brandMsg").addObject("msg", "添加成功！").addObject("currentPage",currentPage);
        }
    }

    /**
     * 分页展示品牌
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageBrand.action")
    private ModelAndView pageBrand(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<Brand> page = brandService.pageBrand(currentPage);
        return new ModelAndView("views/jsp/listBrand").addObject("page", page);
    }

    /**
     * 根据id查询出品牌
     * @param brandId
     * @return
     */
    @RequestMapping("/showBrand.action")
    private ModelAndView showBrandById(Integer brandId, @RequestParam(defaultValue = "1") Integer currentPage){
        Brand brand = brandService.findBrandById(brandId);
        return new ModelAndView("views/jsp/updateBrand").addObject("brand", brand).addObject("currentPage",currentPage);
    }

    /**
     * 修改品牌
     * @param brand
     * @return
     */
    @RequestMapping("/updateBrand.action")
    private ModelAndView updateBrand(Brand brand, @RequestParam(defaultValue = "1") Integer currentPage){
        Map<String, String> errors = new HashMap<>();
        if(StringUtils.isBlank(brand.getBrandName())){
            errors.put("brandNameError", "品牌名字不能为空！");
        }
        if(StringUtils.isBlank(brand.getBrandCompanyName())){
            errors.put("brandCompanyNameError", "公司名字不能为空！");
        }
        if(errors.size() > 0){
            return new ModelAndView("views/jsp/updateBrand").addObject("errors", errors).addObject("currentPage",currentPage);
        }
        boolean result = brandService.updateBrand(brand);
        if(!result){
            return new ModelAndView("views/jsp/updateBrand").addObject("msg", "已存在或没有修改信息！").addObject("currentPage",currentPage);
        }else {
            return new ModelAndView("views/jsp/brandMsg").addObject("msg", "修改成功！")
                                                                    .addObject("brandId", brand.getBrandId()).addObject("currentPage",currentPage);
        }
    }

    /**
     * 删除
     * @param brandId
     * @param currentPage
     * @return
     */
    @RequestMapping("/deleteBrand.action")
    private ModelAndView deleteBrandById(Integer brandId, @RequestParam(defaultValue = "1") Integer currentPage){
        brandService.deleteBrandById(brandId);
        return new ModelAndView("redirect:/pageBrand.action?currentPage=" + currentPage);
    }

    /**
     * 根据公司名称和id进行查找
     * @param brand
     * @return
     */
    @RequestMapping("/findBrand.action")
    private ModelAndView findBrand(Brand brand, @RequestParam(defaultValue = "1") Integer currentPage){
        Page<Brand> page = brandService.findBrand(brand, currentPage);
        return new ModelAndView("views/jsp/listBrand").addObject("page", page)
                                                                .addObject("find", "yes")
                                                                .addObject("brandId", brand.getBrandId())
                                                                .addObject("brandCompanyName", brand.getBrandCompanyName());
    }
}
