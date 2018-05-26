package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.SportItem;
import ecjtu.husen.service.SportItemService;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequiresPermissions(value = "BasicInfoManage")
public class SportItemController {
    @Autowired
    private SportItemService sportItemService;

    /**
     * 到添加运动物品页面
     * @param currentPage
     * @return
     */
    @RequestMapping("/toAddSportItem.action")
    private ModelAndView toAddSportItem(@RequestParam(defaultValue = "1") Integer currentPage){
        return new ModelAndView("views/jsp/addSportItem")
                .addObject("currentPage", currentPage);
    }

    /**
     * 添加运动物品
     * @param sportItem
     * @param currentPage
     * @return
     */
    @RequestMapping("/addSportItem.action")
    private ModelAndView addSportItem(SportItem sportItem, @RequestParam(defaultValue = "1") Integer currentPage){
        if(StringUtils.isBlank(sportItem.getSportItemName())){
            return new ModelAndView("views/jsp/addSportItem")
                    .addObject("msg", "运动物品名不能为空！")
                    .addObject("currentPage", currentPage);
        }
        boolean result = sportItemService.addSportItem(sportItem);
        if(!result){
            return new ModelAndView("views/jsp/addSportItem")
                    .addObject("msg", "该运动物品已存在！")
                    .addObject("currentPage", currentPage);
        }else {
            return new ModelAndView("views/jsp/sportItemMsg")
                    .addObject("msg", "添加成功！")
                    .addObject("currentPage", currentPage);
        }
    }

    /**
     * 分页显示运动物品
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageSportItem.action")
    private ModelAndView pageSportItem(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<SportItem> page = sportItemService.pageSportItem(currentPage);
        return new ModelAndView("views/jsp/listSportItem")
                .addObject("page", page);
    }

    /**
     * 到修改运动物品页面
     * @param currentPage
     * @return
     */
    @RequestMapping("/showSportItem.action")
    private ModelAndView toUpdateSportItem(Integer sportItemId, @RequestParam(defaultValue = "1") Integer currentPage){
        SportItem sportItem = sportItemService.findSportItemById(sportItemId);
        return new ModelAndView("views/jsp/updateSportItem")
                .addObject("currentPage", currentPage)
                .addObject("sportItem", sportItem);
    }

    /**
     * 修改运动物品信息
     * @param sportItem
     * @param currentPage
     * @return
     */
    @RequestMapping("/updateSportItem.action")
    private ModelAndView updateSportItem(SportItem sportItem, @RequestParam(defaultValue = "1") Integer currentPage){
        if(StringUtils.isBlank(sportItem.getSportItemName())){
            return new ModelAndView("views/jsp/updateSportItem")
                    .addObject("msg", "运动物品名称不能为空！")
                    .addObject("currentPage", currentPage);
        }
        boolean result = sportItemService.updateSportItem(sportItem);
        if(!result){
            return new ModelAndView("views/jsp/updateSportItem")
                    .addObject("msg", "已存在或没有修改")
                    .addObject("curerntPage", currentPage);
        }else {
            return new ModelAndView("views/jsp/sportItemMsg")
                    .addObject("msg", "修改成功！")
                    .addObject("currentPage", currentPage)
                    .addObject("sportItemId", sportItem.getSportItemId());
        }
    }

    /**
     * 删除运动物品
     * @param sportItemId
     * @param currentPage
     * @return
     */
    @RequestMapping("/deleteSportItem.action")
    private ModelAndView deleteSportItemById(Integer sportItemId, @RequestParam(defaultValue = "1") Integer currentPage){
        sportItemService.deleteSportItemById(sportItemId);
        return new ModelAndView("redirect:/pageSportItem.action?currentPage=" + currentPage);
    }

    /**
     * 根据id和名称查询运动物品
     * @param sportItem
     * @param currentPage
     * @return
     */
    @RequestMapping("/findSportItem.action")
    private ModelAndView findSportItem(SportItem sportItem, @RequestParam(defaultValue = "1") Integer currentPage){
        Page<SportItem> page = sportItemService.findSportItem(sportItem, currentPage);
        return new ModelAndView("views/jsp/listSportItem")
                .addObject("page", page)
                .addObject("find", "yes")
                .addObject("sportItemId", sportItem.getSportItemId())
                .addObject("sportItemName", sportItem.getSportItemId());
    }
}
