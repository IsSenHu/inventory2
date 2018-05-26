package ecjtu.husen.controller;

import ecjtu.husen.model.OperationNote;
import ecjtu.husen.service.VerfyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author husen
 * @date 2018/03/01
 * 显示页面
 */
@Controller
public class ViewsController {
    @Autowired
    private VerfyNoteService verfyNoteService;
    /*
    * 显示用户登陆的页面
    * */
    @RequestMapping("/toLogin.action")
    public ModelAndView tologin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/login");
        return modelAndView;
    }
    /*
    * 显示用户注册的页面
    * */
    @RequestMapping("/toRegist.action")
    public ModelAndView toRegist(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/regist");
        return modelAndView;
    }
    /*
    * RequiresAuthentication注解表示必须进行身份认证才能访问
    * 显示主页
    * */
    @RequestMapping("/toIndex.action")
    public ModelAndView toIndex(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/jsp/index");
        List<OperationNote> notes = verfyNoteService.afterTenNote();
        return modelAndView.addObject("notes", notes);
    }
}
