package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.PermissionPO;
import ecjtu.husen.pojo.DAO.PermissionUrl;
import ecjtu.husen.pojo.DAO.Url;
import ecjtu.husen.pojo.DTO.UrlVO;
import ecjtu.husen.service.PermissionService;
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
 * @author 11785
 * @date 2018/03/12
 */
@Controller
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 新增一个权限
     * @param permissionPO
     * @return
     */
    @RequestMapping("/addPermission.action")
    @ResponseBody
    private JsonResult<String> addPermission(PermissionPO permissionPO){
        System.out.println(permissionPO);
        if(permissionPO == null){
            return new JsonResult<>("noInput", "noInput");
        }
        List<String> error = new ArrayList<>();
        if(StringUtils.isBlank(permissionPO.getPermissionName())){
            error.add("noName");
        }
        if(StringUtils.isBlank(permissionPO.getDescription())){
            error.add("noDescription");
        }
        if(error.size() > 0){
            return new JsonResult<>("haveError", "haveError", error);
        }
        permissionService.addPermission(permissionPO);
        return new JsonResult<>("success", "success");
    }
    /**
     * 分页显示所有的权限
     * @param currentPage
     * @return
     */
    @RequestMapping("/pagePermission.action")
    private ModelAndView pagePermission(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<PermissionPO> page = permissionService.page(currentPage);
        return new ModelAndView("views/jsp/listPermission")
                .addObject("page", page);

    }

    /**
     * 根据id删除权限
     * @param permissionId
     * @param currentPage
     * @return
     */
    @RequestMapping("/deletePermission.action")
    private ModelAndView deletePermission(Integer permissionId, @RequestParam(defaultValue = "1") Integer currentPage){
        permissionService.deleteById(permissionId);
        return new ModelAndView("redirect:/pagePermission.action?currentPage=" + currentPage);
    }

    /**
     * 根据id查出权限的信息
     * @param permissionId
     * @return
     */
    @RequestMapping("/showPermission.action")
    @ResponseBody
    private PermissionPO showPermission(Integer permissionId){
        PermissionPO permission = permissionService.findById(permissionId);
        return permission;
    }
    /**
     * 修改权限的信息
     * @return
     */
    @RequestMapping("/updatePermission.action")
    @ResponseBody
    private JsonResult<String> updatePermission(PermissionPO permissionPO){
        if(permissionPO == null){
            return new JsonResult<>("noInput", "noInput");
        }
        List<String> error = new ArrayList<>();
        if(permissionPO.getPermissionId() == null){
            error.add("noId");
        }
        if(StringUtils.isBlank(permissionPO.getDescription())){
            error.add("noDescription");
        }
        if(error.size() > 0){
            return new JsonResult<>("haveError", "haveError", error);
        }
        permissionService.updatePermission(permissionPO);
        return new JsonResult<>("success", "success");
    }

    /**
     * 修改权限所能访问的地址
     * @param permissionId
     * @param urlStr
     * @return
     */
    @RequestMapping("/updatePermissionUrl.action")
    @ResponseBody
    private JsonResult<String> updatePermissionUrl(Integer permissionId, @RequestParam("urlStr") String urlStr){
        List<Url> urls = new ArrayList<>();
        if(StringUtils.isNotBlank(urlStr)){
            String[] str = urlStr.split(",");
            for(String urlId : str){
                Url url = new Url();
                url.setUrlId(Integer.valueOf(urlId));
                urls.add(url);
            }
        }
        permissionService.updatePermissionUrl(permissionId, urls);
        return new JsonResult<>("success", "success");
    }

    @RequestMapping("/showUrls.action")
    @ResponseBody
    private Map<String, Object> showUrls(Integer permissionId){
        Map<String, Object> result = new HashMap<>(2);
        List<PermissionUrl> permissionUrls = permissionService.findAllPU(permissionId);
        List<UrlVO> urlVOS = new ArrayList<>();
        for(PermissionUrl permissionUrl : permissionUrls){
            UrlVO urlVO = new UrlVO();
            BeanUtils.copyProperties(permissionUrl.getUrl(), urlVO);
            urlVOS.add(urlVO);
        }
        result.put("p", urlVOS);
        result.put("u", permissionService.findAllUrls());
        return result;
    }
}
