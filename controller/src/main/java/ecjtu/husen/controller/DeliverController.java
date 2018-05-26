package ecjtu.husen.controller;

import com.alibaba.fastjson.JSONObject;
import ecjtu.husen.pojo.DAO.DeliverOrder;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.shop.Address;
import ecjtu.husen.service.DeliverService;
import ecjtu.husen.util.HttpService;
import ecjtu.husen.util.Page;
import ecjtu.husen.vo.DeliverVO;
import ecjtu.husen.vo.FindDeliver;
import ecjtu.husen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author husen
 */
@Controller
public class DeliverController {

    @Autowired
    private DeliverService deliverService;

    /**
     * 根据条件查询发货单
     * @param currentPage
     * @param findDeliver
     * @return
     */
    @RequestMapping("/findDeliverOrder.action")
    private ModelAndView findDeliverOrder(@RequestParam(defaultValue = "1") Integer currentPage, FindDeliver findDeliver){
        Page<DeliverOrder> page = deliverService.find(currentPage, findDeliver);
        List<DeliverVO> deliverVOS = new ArrayList<>();
        page.getContent().stream().forEach(x -> {
            Integer addressId = x.getAddressId();
            try {
                String data = HttpService.getData("http://localhost:8081/addressById.do?addressId=" + addressId);
                Address address = JSONObject.parseObject(data, Address.class);
                DeliverVO deliverVO = new DeliverVO(x, address);
                deliverVOS.add(deliverVO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Page<DeliverVO> voPage = new Page<>();
        voPage.setCurrentPage(page.getCurrentPage());
        voPage.setContent(deliverVOS);
        voPage.setTotalPage(page.getTotalPage());
        voPage.setRowsTotal(page.getRowsTotal());
        voPage.setPageSize(page.getPageSize());
        return new ModelAndView("views/jsp/listDeliver")
                .addObject("page", voPage)
                .addObject("fd", findDeliver);
    }

    /**
     * 发货
     * @param deliverOrderId
     * @return
     */
    @RequestMapping("/deliverGoods.action")
    private @ResponseBody Result<Integer> deliverGoods(Integer deliverOrderId, HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("user");
        if(user == null){
            return new Result<>(400, "没有登录", null);
        }
        Result<Integer> result = deliverService.deliverGoods(deliverOrderId, user);
        return result;
    }

    /**
     * 取消发货
     * @param deliverOrderId
     * @param request
     * @return
     */
    @RequestMapping("/cancelDeliver.action")
    private @ResponseBody Result<Integer> cancelDeliver(Integer deliverOrderId, HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("user");
        if(user == null){
            return new Result<>(400, "没有登录", null);
        }
        Result<Integer> result = deliverService.cancelDeliver(deliverOrderId, user);
        return result;
    }
}
