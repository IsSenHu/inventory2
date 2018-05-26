package com.husen.controller;

import com.husen.service.SportService;
import ecjtu.husen.pojo.DAO.Sport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * @author 11785
 * 显示页面
 */
@Controller
public class ViewsController {
    private static Logger logger = LogManager.getLogger(ViewsController.class);
    @Autowired
    private SportService sportService;
    /*
    * 显示用户登陆的页面
    * */
    @RequestMapping("/toLogin.do")
    public ModelAndView tologin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsp/login");
        return modelAndView;
    }
    /*
    * 显示用户注册的页面
    * */
    @RequestMapping("/toRegist.do")
    public ModelAndView toRegist(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsp/regist");
        return modelAndView;
    }
    /*
    * 显示主页
    * */
    @RequestMapping("/toIndex.do")
    public ModelAndView toIndex(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsp/views/index");
        return modelAndView;
    }

    @RequestMapping("/toOne.do")
    public ModelAndView toOne(){
        List<Sport> sports = null;
        try {
            sports = sportService.getAllSports();
            logger.info("调用接口查找所有运动成功:{}" + sports.size() + "个");
            for (Sport sport : sports){
                logger.info("{}" + sport);
            }
        } catch (IOException e) {
            logger.error("发生错误，调用接口失败:{}");
            e.printStackTrace();
        }
        return new ModelAndView("jsp/views/one")
                .addObject("sports", sports);
    }

    @RequestMapping("/toTwo.do")
    public ModelAndView toTwo(){
        return new ModelAndView("jsp/views/two");
    }

    @RequestMapping("/toThree.do")
    public ModelAndView toThree(){
        return new ModelAndView("jsp/views/three");
    }

    @RequestMapping("/toFouth.do")
    public ModelAndView toFouth(){
        return new ModelAndView("jsp/views/fouth");
    }

    @RequestMapping("/toDetail.do")
    public ModelAndView detail(){
        return new ModelAndView("jsp/views/detail");
    }

    @RequestMapping("pageGood.do")
    public ModelAndView pageGood(){
        return new ModelAndView("jsp/views/listGood");
    }

    @RequestMapping("/lookhow.do")
    public ModelAndView orders(){
        return new ModelAndView("jsp/views/listOrder");
    }
}
