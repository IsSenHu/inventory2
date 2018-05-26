package com.husen.controller;

import com.husen.model.Boss;
import com.husen.service.RefundService;
import com.husen.util.JsonResult;
import com.husen.util.Page;
import com.husen.vo.RefundVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author husen
 */
@Controller
public class RefundController {
    @Autowired
    private RefundService refundService;
    /**
     * 根据用户ID和店铺ID查询退货申请，如果没有用户ID则查询该店铺的所有退货申请
     * @param userId
     * @return
     */
    @RequestMapping("/refunds.do")
    private ModelAndView refunds(@RequestParam(required = false) Integer userId, @RequestParam(defaultValue = "1") Integer currentPage, HttpServletRequest request){
        Boss boss = (Boss) request.getSession(true).getAttribute("boss");
        if(boss == null){
            return new ModelAndView("redirect:/bossLogin.do");
        }
        Page<RefundVO> page = refundService.refunds(boss, userId, currentPage);
        return new ModelAndView("jsp/views/listRefund")
                .addObject("page", page)
                .addObject("userId", userId);
    }

    /**
     * 退款操作
     * @param refundId
     * @return
     */
    @PostMapping("/refund.do")
    private @ResponseBody JsonResult refund(Integer refundId){
        try {
            JsonResult result = refundService.refund(refundId);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("500", "error", null);
        }
    }
}
