package com.husen.controller;

import com.husen.model.Boss;
import com.husen.model.Order;
import com.husen.service.OrderService;
import com.husen.util.Page;
import com.husen.vo.FindOrder;
import com.husen.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class OrderController {

    private final static Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 根据查询条件查询订单
     * @param currentPage
     * @param findOrder
     * @param request
     * @return
     */
    @RequestMapping("/orders.do")
    private ModelAndView findOrder(@RequestParam(defaultValue = "1") Integer currentPage, FindOrder findOrder, HttpServletRequest request){
        logger.info("查询参数为:{}", findOrder);
        Page<Order> page = new Page<>();
        if(findOrder == null){
            page.setContent(new ArrayList<>());
            page.setRowsTotal(0);
            page.setTotalPage(0);
            page.setPageSize(10);
            page.setCurrentPage(1);
            return new ModelAndView("jsp/views/listOrder")
                    .addObject("page", page)
                    .addObject("findOrder", findOrder);
        }else {
            HttpSession session = request.getSession();
            Boss boss = (Boss) session.getAttribute("boss");
            if(boss == null){
                return new ModelAndView("redirect:/toLogin.do");
            }else {
                org.springframework.data.domain.Page<Order> orders = orderService.findOrder1(currentPage, boss, findOrder);
                page.setContent(orders.getContent());
                page.setCurrentPage(orders.getNumber() + 1);
                page.setPageSize(orders.getSize());
                page.setTotalPage(orders.getTotalPages());
                page.setRowsTotal(((Long)orders.getTotalElements()).intValue());
                return new ModelAndView("jsp/views/listOrder")
                        .addObject("page", page)
                        .addObject("findOrder", findOrder);
            }
        }
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @RequestMapping("/deleteOrder.do")
    private @ResponseBody Result<String> deleteOrder(Integer orderId){
        logger.info("删除订单ID：{}", orderId);
        try {
            String oldOrderId = orderService.deleteOrder(orderId);
            logger.info("删除成功ID：{}", oldOrderId);
            return new Result<>(200, "删除成功！", oldOrderId);
        }catch (Exception e){
            logger.info("未知错误！:{}", e.getMessage());
            return new Result<>(500, "未知错误！", null);
        }
    }

    /**
     * 推送发货消息到库存管理系统
     * @param orderId
     * @return
     */
    @RequestMapping("/deliver.do")
    private @ResponseBody Result<String> deliver(Integer orderId){
        logger.info("根据订单发货ID：{}", orderId);
        try {
            logger.info("推送发货消息到库存管理系统：{}", orderId);
            String id = orderService.deliver(orderId);
            return new Result<>(200, "已推送发货消息！", id);
        }catch (Exception e){
            logger.error("未知错误：{}", e.getMessage());
            return new Result<>(500, "未知错误！", null);
        }
    }

    /**
     * 写一个接口，根据id查询订单
     * @param orderId
     * @return
     */
    @PostMapping("/orderById.do")
    private @ResponseBody Order findById(String orderId){
        return orderService.findById(orderId);
    }
}
