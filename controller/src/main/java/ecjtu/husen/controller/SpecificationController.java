package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.Specification;
import ecjtu.husen.service.SpecificationService;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author 11785
 */
@Controller
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * 到添加规格页面
     * @param currentPage
     * @return
     */
    @RequestMapping("/toAddSpecification.action")
    private ModelAndView toAddSpecification(@RequestParam(defaultValue = "1") Integer currentPage){
        return new ModelAndView("views/jsp/addSpecification")
                .addObject("currentPage", currentPage);
    }

    /**
     * 添加规格
     * @param specification
     * @param currentPage
     * @return
     */
    @RequestMapping("/addSpecification.action")
    private ModelAndView addSpecification(Specification specification, @RequestParam(defaultValue = "1") Integer currentPage){
        if (StringUtils.isBlank(specification.getSpecificationName())){
            return new ModelAndView("views/jsp/addSpecification")
                    .addObject("msg", "规格名不能为空！")
                    .addObject("currentPage", currentPage);
        }
        boolean result = specificationService.addSpecification(specification);
        if(!result){
            return new ModelAndView("views/jsp/addSpecification")
                    .addObject("currentPage", currentPage)
                    .addObject("msg", "该规格名已存在！");
        }else {
            return new ModelAndView("views/jsp/specificationMsg")
                    .addObject("msg", "添加成功！")
                    .addObject("currentPage", currentPage);
        }
    }

    /**
     * 分页显示规格
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageSpecification.action")
    private ModelAndView pageSpecification(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<Specification> page = specificationService.pageSpecification(currentPage);
        return new ModelAndView("views/jsp/listSpecification")
                .addObject("page", page);
    }

    /**
     * 转发到规格修改页面
     * @param specificationId
     * @param currentPage
     * @return
     */
    @RequestMapping("/showSpecification.action")
    private ModelAndView toUpdateSpecification(Integer specificationId, @RequestParam(defaultValue = "1") Integer currentPage){
        Specification specification = specificationService.findById(specificationId);
        return new ModelAndView("views/jsp/updateSpecification")
                .addObject("specification", specification)
                .addObject("currentPage", currentPage);
    }

    /**
     * 修改规格
     * @param specification
     * @param currentPage
     * @return
     */
    @RequestMapping("/updateSpecification.action")
    private ModelAndView updateSpecification(Specification specification, @RequestParam(defaultValue = "1") Integer currentPage){
        if(StringUtils.isBlank(specification.getSpecificationName())){
            return new ModelAndView("views/jsp/updateSpecification")
                    .addObject("msg", "规格名称不能为空！")
                    .addObject("currentPage", currentPage);
        }
        boolean result = specificationService.updateSpecification(specification);
        if(!result){
            return new ModelAndView("views/jsp/updateSpecification")
                    .addObject("msg", "已存在或没有修改！")
                    .addObject("currentPage", currentPage);
        }else {
            return new ModelAndView("views/jsp/specificationMsg")
                    .addObject("msg", "修改成功！")
                    .addObject("currentPage", currentPage)
                    .addObject("specificationId", specification.getSpecificationId())
                    .addObject("specificationName", specification.getSpecificationName());
        }
    }
    @RequestMapping("/deleteSpecification.action")
    private ModelAndView deleteSpecificationById(Integer specificationId, @RequestParam(defaultValue = "1") Integer currentPage){
        specificationService.deleteById(specificationId);
        return new ModelAndView("redirect:/pageSpecification.action?currentPage=" + currentPage);
    }
}
