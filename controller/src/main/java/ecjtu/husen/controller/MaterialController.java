package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.Material;
import ecjtu.husen.service.MaterialService;
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
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    /**
     * 转发到添加材质页面
     * @param currentPage
     * @return
     */
    @RequestMapping("/toAddMaterial.action")
    private ModelAndView toAddMaterial(@RequestParam(defaultValue = "1") Integer currentPage){
        return new ModelAndView("views/jsp/addMaterial").addObject("currentPage", currentPage);
    }

    /**
     * 添加材质
     * @param material
     * @param currentPage
     * @return
     */
    @RequestMapping("/addMaterial.action")
    private ModelAndView addMaterial(Material material, @RequestParam(defaultValue = "1") Integer currentPage){
        if(StringUtils.isBlank(material.getMaterialName())){
            return new ModelAndView("views/jsp/addMaterial")
                    .addObject("msg", "材质名称不能为空！")
                    .addObject("currentPage", currentPage);
        }
        boolean result = materialService.addMaterial(material);
        if(!result){
            return new ModelAndView("views/jsp/addMaterial")
                    .addObject("msg", "该材质已存在！");
        }else {
            return new ModelAndView("views/jsp/materialMsg")
                    .addObject("msg", "添加成功！")
                    .addObject("currentPage", currentPage);
        }
    }

    /**
     * 分页显示材质
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageMaterial.action")
    private ModelAndView pageMaterial(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<Material> page = materialService.page(currentPage);
        return new ModelAndView("views/jsp/listMaterial")
                .addObject("page", page);
    }

    /**
     * 转发到修改材质页面
     * @param materialId
     * @param currentPage
     * @return
     */
    @RequestMapping("/showMaterial.action")
    private ModelAndView toUpdateMaterial(Integer materialId, @RequestParam(defaultValue = "1") Integer currentPage){
        Material material = materialService.findMaterialById(materialId);
        return new ModelAndView("views/jsp/updateMaterial")
                .addObject("material", material)
                .addObject("currentPage", currentPage);
    }

    /**
     * 修改材质
     * @param material
     * @param currentPage
     * @return
     */
    @RequestMapping("/updateMaterial.action")
    private ModelAndView updateMaterial(Material material, @RequestParam(defaultValue = "1") Integer currentPage){
        if(StringUtils.isBlank(material.getMaterialName())){
            return new ModelAndView("views/jsp/updateMaterial")
                    .addObject("msg", "材质名不能为空！")
                    .addObject("currentPage", currentPage);
        }
        boolean result = materialService.updateMaterial(material);
        if(!result){
            return new ModelAndView("views/jsp/updateMaterial")
                    .addObject("msg", "该材质已存在或没有修改")
                    .addObject("currentPage", currentPage);
        }else {
            return new ModelAndView("views/jsp/materialMsg")
                    .addObject("msg", "修改成功")
                    .addObject("currentPage", currentPage)
                    .addObject("materialId", material.getMaterialId());
        }
    }

    /**
     * 根据Id删除材质
     * @param materialId
     * @param currentPage
     * @return
     */
    @RequestMapping("/deleteMaterial.action")
    private ModelAndView deleteMaterial(Integer materialId, @RequestParam(defaultValue = "1") Integer currentPage){
        materialService.deleteMaterial(materialId);
        return new ModelAndView("redirect:/pageMaterial.action?currentPage=" + currentPage);
    }

    /**
     * 根据id和名称查询材质并分页
     * @param material
     * @param currentPage
     * @return
     */
    @RequestMapping("/findMaterial.action")
    private ModelAndView findMaterial(Material material, @RequestParam(defaultValue = "1") Integer currentPage){
        Page<Material> page = materialService.findMaterial(material, currentPage);
        return new ModelAndView("/views/jsp/listMaterial")
                .addObject("page", page)
                .addObject("find", "yes")
                .addObject("materialId", material.getMaterialId())
                .addObject("materialName", material.getMaterialName());
    }
}
