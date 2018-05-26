package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.Sport;
import ecjtu.husen.service.SportService;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author 11785
 */
@Controller
@RequiresPermissions(value = "BasicInfoManage")
public class SportController {
    @Autowired
    private SportService sportService;
    /**
     * 转发到添加运动页面
     * @return 添加运动页面的路径
     */
    @RequestMapping("/toAddSport.action")
    private String toAddSport(@RequestParam(defaultValue = "1") Integer currentPage, HttpServletRequest request){
        request.setAttribute("currentPage", currentPage);
        return "views/jsp/addSport";
    }

    /**
     * 添加运动
     * @param sport
     * @param request
     * @return
     */
    @RequestMapping("/addSport.action")
    private String addSport (Sport sport, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer currentPage){
        if(StringUtils.isBlank(sport.getSportName())){
            request.setAttribute("msg", "运动名不能为空！");
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/addSport";
        }
        boolean result = sportService.addSport(sport);
        if(!result){
            request.setAttribute("msg", "该运动已存在！");
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/addSport";
        }else {
            request.setAttribute("msg", "运动添加成功！");
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/sportMsg";
        }
    }

    /**
     * 分页显示运动
     * @param currentPage
     * @param request
     * @return
     */
    @RequestMapping("/pageSport.action")
    private String pageSport(@RequestParam(required = true, defaultValue = "1") Integer currentPage, HttpServletRequest request){
        Page<Sport> page = sportService.pageStudent(currentPage);
        request.setAttribute("page", page);
        return "views/jsp/listSport";
    }

    /**
     * 根据Id显示相应的运动
     * @param sportId
     * @param request
     * @return
     */
    @RequestMapping("/showSport.action")
    private String showSport(Integer sportId, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer currentPage){
        Sport sport = sportService.findSportById(sportId);
        request.setAttribute("sport", sport);
        request.setAttribute("currentPage", currentPage);
        return "views/jsp/updateSport";
    }

    /**
     * 更新运动信息
     * @param sport
     * @param request
     * @return
     */
    @RequestMapping("/updateSport.action")
    private String updateSport(Sport sport, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer currentPage){
        if(StringUtils.isBlank(sport.getSportName())){
            request.setAttribute("msg", "运动名不能为空！");
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/updateSport";
        }
        boolean result = sportService.updateSport(sport);
        if(!result){
            request.setAttribute("msg", "已存在或没有修改信息！");
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/updateSport";
        }else {
            request.setAttribute("msg", "修改成功！");
            request.setAttribute("sportId", sport.getSportId());
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/sportMsg";
        }
    }

    /**
     * 根据id删除运动
     * @param sportId
     * @param currentPage
     * @param request
     * @return
     */
    @RequestMapping("/deleteSport.action")
    private String deleteSport(Integer sportId, @RequestParam(defaultValue = "1") Integer currentPage, HttpServletRequest request){
        sportService.deleteSportById(sportId);
        return "redirect:/pageSport.action?currentPage=" + currentPage;
    }

    /**
     * 根据id和名称查询运动
     * @param currentPage
     * @param sport
     * @param request
     * @return
     */
    @RequestMapping("/findSport.action")
    private String findSport(@RequestParam(defaultValue = "1") Integer currentPage, Sport sport, HttpServletRequest request){
        Page<Sport> page = sportService.findSport(sport, currentPage);
        request.setAttribute("page", page);
        request.setAttribute("find", "yes");
        request.setAttribute("sportId", sport.getSportId());
        request.setAttribute("sportName", sport.getSportName());
        return "views/jsp/listSport";
    }
}
