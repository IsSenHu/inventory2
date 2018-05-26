package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.InOrder;
import ecjtu.husen.pojo.DAO.Item;
import ecjtu.husen.pojo.DAO.Sport;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DTO.UserVO;
import ecjtu.husen.service.InService;
import ecjtu.husen.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11785
 */
@Controller
public class InController {
    @Autowired
    private InService inService;

    /**
     * 显示所有的运动，让使用者来选择添加哪个运动的商品进行入库
     * @return
     */
    @RequestMapping("/showAllSport.action")
    @ResponseBody
    private List<Sport> findAllSport(){
        List<Sport> sports = inService.findAllSport();
        return sports;
    }
    /**
     *
     * @param sportId
     * @return
     * 首先要选择所属运动，然后在显示所属的添加页面
     */
    @RequestMapping("/toIn.action")
    @ResponseBody
    private List<Item> toIn(@RequestParam(required = false) Integer sportId){
        List<Item> items = inService.findAllItemBySportId(sportId);
        return items;
    }

    /**
     * 添加入库单
     * @param inOrder
     * @param request
     * @return
     * 还要将添加入库单的管理员设置好
     */
    @RequestMapping("/addIn.action")
    @ResponseBody
    private Map<String, String> addIn(HttpServletRequest request, Integer itemId, InOrder inOrder){
        //得到是谁添加的入库单
        UserPO userPO = (UserPO) request.getSession().getAttribute("user");
        Item item = new Item();
        item.setItemId(itemId);
        inOrder.setItem(item);
        if(userPO == null || userPO.getUserId() == null){
            Map<String, String> noLogin = new HashMap<>();
            noLogin.put("noLogin", "noLogin");
            return noLogin;
        }else {
            inOrder.setUserIn(new UserPO());
            inOrder.getUserIn().setUserId(userPO.getUserId());
        }
        Integer itemNumber = inOrder.getItmeNumber();
        Double totalMoney = inOrder.getTotalMoney();
        if(itemNumber == null || totalMoney == null || inOrder.getItem() == null){
            Map<String, String> error = new HashMap<>();
            error.put("error", "error");
            return error;
        }else {
            inService.addInOrder(inOrder);
            Map<String, String> success = new HashMap<>();
            success.put("success", "success");
            return success;
        }
    }

    /**
     * 分页显示入库单
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageIn.action")
    private ModelAndView pageIn(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<InOrder> page = inService.pageIn(currentPage);
        return new ModelAndView("views/jsp/inlist")
                .addObject("page", page);
    }

    /**
     * 根据Id删除未审核的订单
     * @param currentPage
     * @param inOrderId
     * @return
     */
    @RequestMapping("/deleteIn.action")
    private ModelAndView deleteIn(@RequestParam(defaultValue = "1") Integer currentPage, Integer inOrderId){
        inService.deleteIn(inOrderId);
        return new ModelAndView("redirect:/pageIn.action?currentPage=" + currentPage);
    }
    @RequestMapping("/findIn.action")
    private ModelAndView findIn(InOrder inOrder, String itemName, @RequestParam(defaultValue = "1") Integer currentPage){
        InOrder inOrderCondition = new InOrder();
        inOrderCondition.setItem(new Item());
        inOrderCondition.getItem().setItemName(itemName);
        inOrderCondition.setInOrderId(inOrder.getInOrderId());
        Page<InOrder> page = inService.findIn(inOrderCondition, currentPage);
        return new ModelAndView("views/jsp/inlist")
                .addObject("page", page)
                .addObject("find", "yes")
                .addObject("inOrderId", inOrder.getInOrderId())
                .addObject("itemName", itemName);
    }

    /**
     * 查询出所有的未审核的订单并分页显示
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageNoVerfy.action")
    private ModelAndView pageNoVerfy(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<InOrder> page = inService.pageNoVerfy(currentPage);
        return new ModelAndView("views/jsp/verfyin")
                .addObject("page", page);
    }

    /**
     *  按条件查询出未审核的入库单
     * @param currentPage
     * @return
     */
    @RequestMapping("/findVerfyIn.action")
    private ModelAndView findVerfyIn(InOrder inOrder, String itemName, @RequestParam(defaultValue = "1") Integer currentPage){
        InOrder inOrderCondition = new InOrder();
        inOrderCondition.setItem(new Item());
        inOrderCondition.getItem().setItemName(itemName);
        inOrderCondition.setInOrderId(inOrder.getInOrderId());
        Page<InOrder> page = inService.findVerfyIn(inOrderCondition, currentPage);
        return new ModelAndView("views/jsp/verfyin")
                .addObject("page", page)
                .addObject("find", "yes")
                .addObject("inOrderId", inOrder.getInOrderId())
                .addObject("itemName", itemName);
    }

    /**
     * 审核通过
     * @param inOrderId
     * @return
     */
    @RequestMapping("/access.action")
    private @ResponseBody Map<String, String> access(HttpServletRequest request, Integer inOrderId){
        if(inOrderId == null){
            Map<String, String> error = new HashMap<>();
            error.put("error", "error");
            return error;
        }else {
            UserPO userPO = (UserPO) request.getSession().getAttribute("user");
            if (userPO == null || userPO.getUserId() == null) {
                Map<String, String> noLogin = new HashMap<>();
                noLogin.put("noLogin", "noLogin");
                return noLogin;
            }
            inService.access(userPO.getUserId(), inOrderId);
            Map<String, String> success = new HashMap<>();
            success.put("success", "success");
            return success;
        }
    }

    /**
     * 审核不通过
     * @param inOrderId
     * @return
     */
    @RequestMapping("/faile.action")
    private @ResponseBody Map<String, String> faile(HttpServletRequest request, Integer inOrderId){
        if(inOrderId == null){
            Map<String, String> error = new HashMap<>();
            error.put("error", "error");
            return error;
        }else {
            UserPO userPO = (UserPO) request.getSession().getAttribute("user");
            if (userPO == null || userPO.getUserId() == null) {
                Map<String, String> noLogin = new HashMap<>();
                noLogin.put("noLogin", "noLogin");
                return noLogin;
            }
            inService.faile(userPO.getUserId(), inOrderId);
            Map<String, String> success = new HashMap<>();
            success.put("success", "success");
            return success;
        }
    }
 }
