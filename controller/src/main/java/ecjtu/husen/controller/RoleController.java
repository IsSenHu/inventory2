package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.RolePermission;
import ecjtu.husen.pojo.DTO.PermissionVO;
import ecjtu.husen.service.RoleService;
import ecjtu.husen.util.JsonResult;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
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
 * @author husen
 * @date 2018/03/12
 */
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 增加角色
     * @return
     */
    @RequestMapping("/addRole.action")
    @ResponseBody
    private JsonResult<String> addRole(RolePO role){
        if(role == null){
            return new JsonResult<>("noInput", "noInput");
        }
        List<String> error = new ArrayList<>();
        if(StringUtils.isBlank(role.getRoleName())){
            error.add("noName");
        }
        if(error.size() > 0){
            return new JsonResult<>("inputError", "inputError", error);
        }
        roleService.addRole(role);
        return new JsonResult<>("success", "success");
    }

    /**
     * 分页显示所有的角色
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageRole.action")
    private ModelAndView pageRole(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<RolePO> page = roleService.page(currentPage);
        return new ModelAndView("views/jsp/listRole")
                .addObject("page", page);
    }

    /**
     * 根据角色ID删除角色
     * @param currentPage
     * @param roleId
     * @return
     */
    @RequestMapping("/deleteRole.action")
    private ModelAndView delete(@RequestParam(defaultValue = "1") Integer currentPage, Integer roleId){
        if(roleId != null){
            roleService.deleteById(roleId);
            return new ModelAndView("redirect:/pageRole.action?currentPage=" + currentPage);
        }else {
            return new ModelAndView("redirect:/pageRole.action?currentPage=" + currentPage);
        }
    }

    /**
     * 显示要修改的角色
     * @param roleId
     * @return
     */
    @RequestMapping("/showRole.action")
    @ResponseBody
    private RolePO showRole(Integer roleId){
        RolePO role = roleService.findById(roleId);
        return role;
    }

    /**
     * 修改角色的信息
     * @return
     */
    @RequestMapping("/updateRole.action")
    @ResponseBody
    private JsonResult<String> updateRole(RolePO rolePO){
        if(rolePO == null){
            return new JsonResult<>("noInput", "noInput");
        }
        List<String> error = new ArrayList<>();
        if(rolePO.getRoleId() == null){
            error.add("noId");
        }
        if(StringUtils.isBlank(rolePO.getRoleName())){
            error.add("noName");
        }
        if(error.size() > 0){
            return new JsonResult<>("inputError", "inputError", error);
        }
        roleService.updateRole(rolePO);
        return new JsonResult<>("success", "success");
    }

    /**
     * 查找角色所拥有的权限和所有的权限
     * @param roleId
     * @return
     */
    @RequestMapping("/showPermissions.action")
    @ResponseBody
    private Map<String, Object> showPermissions(Integer roleId){
        List<PermissionPO> allPermissions= roleService.findAllPermission();
        List<RolePermission> myRolePermissions = roleService.findMyPermission(roleId);
        List<PermissionVO> myPermissions = new ArrayList<>();
        for(RolePermission rolePermission : myRolePermissions){
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(rolePermission.getPermission(), permissionVO);
            myPermissions.add(permissionVO);
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("p", myPermissions);
        result.put("u", allPermissions);
        return result;
    }
    /**
     *  修改角色所拥有的权限
     * @return
     */
    @RequestMapping("/updateRolePermission.action")
    @ResponseBody
    private JsonResult<String> updateRolePermission(Integer roleId, String permissionStr){
        if(permissionStr == null){
            return new JsonResult<>("noInput", "noInput");
        }else {
            List<PermissionPO> permissions = new ArrayList<>();
            if(StringUtils.isNotBlank(permissionStr)){
                String[] ps = permissionStr.split(",");
                for(String permissionId : ps){
                    PermissionPO permission = new PermissionPO();
                    permission.setPermissionId(Integer.valueOf(permissionId));
                    permissions.add(permission);
                }
            }
            roleService.updateRolePermission(roleId, permissions);
            return new JsonResult<>("success", "success");
        }
    }
}
