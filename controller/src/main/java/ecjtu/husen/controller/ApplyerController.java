package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Sport;
import ecjtu.husen.service.ApplyerService;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author husen
 */
@Controller
public class ApplyerController {
    @Autowired
    private ApplyerService applyerService;

    /**
     * 转发到添加使用者页面，要获取所有的运动信息
     * @return
     */
    @RequestMapping("/toAddApplyer.action")
    private String toAddApplyer(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer currentPage){
        List<Sport> sports = applyerService.findAllSport();
        request.setAttribute("sports", sports);
        request.setAttribute("currentPage",currentPage);
        return "views/jsp/addApplyer";
    }

    /**
     * 添加使用者
     * @param applyer
     * @param request
     * @return
     */
    @RequestMapping("/addApplyer.action")
    private String addApplyer(Applyer applyer, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer currentPage){
        System.out.println(applyer.getSport().getSportId());
        if(StringUtils.isBlank(applyer.getApplyerName())){
            request.setAttribute("msg", "使用者名称不能为空！");
            request.setAttribute("sports", applyerService.findAllSport());
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/addApplyer";
        }
        boolean result = applyerService.addApplyer(applyer);
        if(result){
            request.setAttribute("msg", "添加成功！");
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/applyerMsg";
        }else {
            request.setAttribute("msg", "该使用者已存在！");
            request.setAttribute("sports", applyerService.findAllSport());
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/addApplyer";
        }
    }

    /**
     * 分页显示使用者
     * @param currentPage
     * @param request
     * @return
     */
    @RequestMapping("/pageApplyer.action")
    private String pageApplyer(@RequestParam(required = true, defaultValue = "1") Integer currentPage, HttpServletRequest request){
        Page<Applyer> page = applyerService.pageApplyer(currentPage);
        request.setAttribute("page", page);
        return "views/jsp/listApplyer";
    }

    /**
     * 根据Id展示使用者
     * @param applyerId
     * @param request
     * @return
     */
    @RequestMapping("/showApplyer.action")
    private String showApplyer(Integer applyerId, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer currentPage){
        Applyer applyer = applyerService.findApplyerById(applyerId);
        List<Sport> sports = applyerService.findAllSport();
        request.setAttribute("applyer", applyer);
        request.setAttribute("sports", sports);
        request.setAttribute("currentPage", currentPage);
        return "views/jsp/updateApplyer";
    }

    /**
     * 修改使用者
     * @param applyer
     * @param request
     * @return
     */
    @RequestMapping("/updateApplyer.action")
    private String updateApplyer(Applyer applyer, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer currentPage){
        if(StringUtils.isBlank(applyer.getApplyerName())){
            List<Sport> sports = applyerService.findAllSport();
            request.setAttribute("sports", sports);
            request.setAttribute("msg", "使用者名称不能为空！");
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/updateApplyer";
        }
        boolean result = applyerService.updateApplyer(applyer);
        if(!result){
            List<Sport> sports = applyerService.findAllSport();
            request.setAttribute("sports", sports);
            request.setAttribute("msg", "已存在或没有修改信息！");
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/updateApplyer";
        }else {
            request.setAttribute("msg", "修改成功！");
            request.setAttribute("applyerId", applyer.getApplyerId());
            request.setAttribute("currentPage", currentPage);
            return "views/jsp/applyerMsg";
        }
    }

    /**
     * 删除使用者
     * @param applyerId
     * @param currentPage
     * @param request
     * @return
     */
    @RequestMapping("/deleteApplyer.action")
    private String deleteApplyerById(Integer applyerId, @RequestParam(defaultValue = "1") Integer currentPage, HttpServletRequest request){
        applyerService.deleteApplyerById(applyerId);
        return "redirect:/pageApplyer.action?currentPage=" + currentPage;
    }

    /**
     * 根据id和名称查询使用者
     * @param currentPage
     * @param applyer
     * @param request
     * @return
     */
    @RequestMapping("/findApplyer.action")
    private String findApplyer(@RequestParam(defaultValue = "1") Integer currentPage, Applyer applyer, HttpServletRequest request){
        Page<Applyer> page = applyerService.findApplyer(applyer, currentPage);
        request.setAttribute("page", page);
        request.setAttribute("find", "yes");
        request.setAttribute("applyerId", applyer.getApplyerId());
        request.setAttribute("applyerName", applyer.getApplyerName());
        return "views/jsp/listApplyer";
    }
}
