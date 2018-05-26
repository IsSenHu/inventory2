package ecjtu.husen.controller;

import ecjtu.husen.pojo.DAO.OutOrder;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DTO.InStatu;
import ecjtu.husen.service.OutService;
import ecjtu.husen.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author husen
 */
@Controller
public class OutController {
    @Autowired
    private OutService outService;


    /**
     * 分页显示出库单列表
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageOut.action")
    private ModelAndView pageOut(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<OutOrder> page = outService.page(currentPage);
        return new ModelAndView("views/jsp/outlist")
                .addObject("page", page);
    }

    /**
     * 根据查询条件进行查询并分页
     * @param outOrderId
     * @param start
     * @param end
     * @param outStatuValue
     * @param currentPage
     * @return
     * @throws ParseException
     */
    @RequestMapping("/findOut.action")
    public ModelAndView findOut(Integer outOrderId, String start, String end,
                                Integer outStatuValue, @RequestParam(defaultValue = "1") Integer currentPage) throws ParseException {
        OutOrder outOrder = new OutOrder();
        if(outOrderId != null){
            outOrder.setOutOrderId(outOrderId);
        }
        if(outStatuValue != null){
            for(InStatu inStatu : InStatu.values()){
                if(outStatuValue.equals(inStatu.getValue())){
                    outOrder.setInStatu(inStatu);
                }
            }
        }
        Page<OutOrder> page = outService.findOut(outOrder, start, end, currentPage);
        return new ModelAndView("views/jsp/outlist")
                .addObject("page", page)
                .addObject("outOrderId", outOrderId)
                .addObject("start", start)
                .addObject("end", end)
                .addObject("outStatuValue", outStatuValue)
                .addObject("find", "yes");
    }

    /**
     * 分页显示未审核的出库单
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageNoVerfyOut.action")
    private ModelAndView pageNoVerfy(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<OutOrder> page = outService.pageNoverfy(currentPage);
        return new ModelAndView("views/jsp/verfyout")
                .addObject("page", page);
    }

    /**
     * 根据条件查询未审核的出库单
     * @return
     * @param outOrderId
     * @param start
     * @param end
     * @param currentPage
     */
    @RequestMapping("/findNoVerfyOut.action")
    private ModelAndView findNoverfy(Integer outOrderId, String start, String end,
                                     @RequestParam(defaultValue = "1") Integer currentPage) throws ParseException {
        OutOrder outOrder = new OutOrder();
        if(outOrderId != null){
            outOrder.setOutOrderId(outOrderId);
        }
        Page<OutOrder> page = outService.findNoVerfy(outOrder, start, end, currentPage);
        return new ModelAndView("views/jsp/verfyout")
                .addObject("outOrderId", outOrderId)
                .addObject("start", start)
                .addObject("end", end)
                .addObject("page", page)
                .addObject("find", "yes");
    }
    /**
     * 出库单审核通过
     * @param outOrderId
     * @return
     */
    @RequestMapping("/outAccess.action")
    @ResponseBody
    private Map<String, String> access(HttpServletRequest request, Integer outOrderId){
        if(outOrderId == null){
            Map<String, String> error = new HashMap<>(1);
            error.put("error", "error");
            return error;
        }else {
            UserPO userPO = (UserPO) request.getSession().getAttribute("user");
            if(userPO == null || userPO.getUserId() == null){
                Map<String, String> noLogin = new HashMap<>();
                noLogin.put("noLogin", "noLogin");
                return noLogin;
            }
            //判断库存是否足够
            boolean result = outService.checkStock(outOrderId);
            if(result == false){
                //库存不足
                Map<String, String> faile = new HashMap<>(1);
                faile.put("faile", "faile");
                return faile;
            }
            outService.access(userPO.getUserId(), outOrderId);
            Map<String, String> success = new HashMap<>(1);
            success.put("success", "success");
            return success;
        }
    }

    /**
     * 出库单审核失败
     * @param request
     * @param outOrderId
     * @return
     */
    @RequestMapping("/outFaile.action")
    @ResponseBody
    private Map<String, String> faile(HttpServletRequest request, Integer outOrderId){
        if(outOrderId == null){
            Map<String, String> error = new HashMap<>();
            error.put("error", "error");
            return error;
        }else {
            UserPO userPO = (UserPO) request.getSession().getAttribute("user");
            if(userPO == null || userPO.getUserId() == null){
                Map<String, String> noLogin = new HashMap<>();
                noLogin.put("noLogin", "noLogin");
                return noLogin;
            }
            outService.faile(userPO.getUserId(), outOrderId);
            Map<String, String> success = new HashMap<>();
            success.put("success", "success");
            return success;
        }
    }
}
