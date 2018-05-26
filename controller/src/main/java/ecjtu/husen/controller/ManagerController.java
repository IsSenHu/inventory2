package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DAO.UserRolePO;
import ecjtu.husen.pojo.DTO.RoleVO;
import ecjtu.husen.service.ManagerService;
import ecjtu.husen.util.JsonResult;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author husen
 */
@Controller
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    /**
     * 分页显示所有管理员
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageManager.action")
    private ModelAndView pageManager(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<UserPO> page = managerService.page(currentPage);
        return new ModelAndView("views/jsp/manager").addObject("page", page);
    }

    /**
     * @param userId
     * @param currentPage
     * @return
     */
    @RequestMapping("/deleteManager.action")
    private ModelAndView delete(Integer userId, @RequestParam(defaultValue = "1") Integer currentPage){
        managerService.deleteById(userId);
        return new ModelAndView("redirect:/pageManager.action?currentPage=" + currentPage);
    }

    /**
     * 修改管理员的状态
     * @param userId
     * @return
     */
    @RequestMapping("/updateManagerStatu.action")
    @ResponseBody
    private JsonResult<String> updateStatu(Integer userId){
        if(userId == null){
            return new JsonResult<>("error", "error");
        }
        managerService.updateStatu(userId);
        return new JsonResult<>("success", "success");
    }

    /**
     * 查找该用户的角色和所有的角色
     * @param userId
     * @return
     */
    @RequestMapping("/showRoles.action")
    @ResponseBody
    private Map<String, Object> showRoles(Integer userId){
        List<RolePO> allRole = managerService.findAllRole();
        List<UserRolePO> userRolePOS = managerService.findMyRP(userId);
        List<RoleVO> myRole = new ArrayList<>();
        for(UserRolePO userRolePO : userRolePOS){
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(userRolePO.getRolePO(), roleVO);
            myRole.add(roleVO);
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("u", allRole);
        result.put("p", myRole);
        return result;
    }

    /**
     * 更新用户的角色
     * @param userId
     * @param roleStr
     * @return
     */
    @RequestMapping("/updateUserRole.action")
    @ResponseBody
    private JsonResult<String> updateUserRole(Integer userId, String roleStr){
        if(userId == null){
            return new JsonResult<>("error", "error");
        }
        String[] rs = null;
        List<RolePO> roles = new ArrayList<>();
        if(StringUtils.isNotBlank(roleStr)){
            rs = roleStr.split(",");
            for (String str : rs){
                RolePO rolePO = new RolePO();
                rolePO.setRoleId(Integer.valueOf(str));
                roles.add(rolePO);
            }
        }
        managerService.updateUserRole(userId, roles);
        return new JsonResult<>("success", "success");
    }
}
