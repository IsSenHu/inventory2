package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.Url;
import ecjtu.husen.service.UrlService;
import ecjtu.husen.util.JsonResult;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author 11785
 */
@Controller
@RequiresPermissions(value = "SystemManage")
public class UrlController {
    @Autowired
    private UrlService urlService;

    /**
     * 添加url地址
     * @param url
     * @return
     */
    @RequestMapping("/addUrl.action")
    @ResponseBody
    private JsonResult<String> addUrl(Url url){
        if(url == null){
            return new JsonResult<>("noInput", "noInput");
        }
        List<String> error = new ArrayList<>();
        if(StringUtils.isBlank(url.getUrl())){
            error.add("noUrl");
        }
        if(StringUtils.isBlank(url.getDescription())){
            error.add("noDescription");
        }
        if(error.size() > 0){
            return new JsonResult<>("haveError", "haveError", error);
        }
        urlService.addUrl(url);
        return new JsonResult<>("success", "success");
    }

    /**
     * 分页显示所有的地址
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageUrl.action")
    private ModelAndView pageUrl(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<Url> page = urlService.page(currentPage);
        return new ModelAndView("views/jsp/listUrl")
                .addObject("page", page);
    }

    /**
     * 根据id显示url
     * @param urlId
     * @return
     */
    @RequestMapping("/showUrl.action")
    @ResponseBody
    private Url showUrl(Integer urlId){
        return urlService.findById(urlId);
    }

    /**
     * 更新地址
     * @param url
     * @return
     */
    @RequestMapping("/updateUrl.action")
    @ResponseBody
    private JsonResult<String> updateUrl(Url url){
        if(url == null){
            return new JsonResult<>("noInput", "noInput");
        }
        List<String> error = new ArrayList<>();
        if(StringUtils.isBlank(url.getUrl())){
            error.add("noUrl");
        }
        if(StringUtils.isBlank(url.getDescription())){
            error.add("noDescription");
        }
        if(error.size() > 0){
            return new JsonResult<>("haveError", "haveError", error);
        }
        urlService.update(url);
        return new JsonResult<>("success", "success");
    }

    /**
     * 删除url
     * @param urlId
     * @param currentPage
     * @return
     */
    @RequestMapping("/deleteUrl.action")
    private ModelAndView delete(Integer urlId, @RequestParam(defaultValue = "1") Integer currentPage){
        urlService.delete(urlId);
        return new ModelAndView("redirect:/pageUrl.action?currentPage=" + currentPage);
    }
}
