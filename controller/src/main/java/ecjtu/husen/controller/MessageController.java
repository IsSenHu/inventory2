package ecjtu.husen.controller;

import ecjtu.husen.service.MessageService;
import ecjtu.husen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 11785
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;
    /**
     * 查询待审核入库单的数量
     * @return
     */
    @RequestMapping("/waitVerfyInOrderNumber.action")
    private @ResponseBody Result<Integer> waitVerfyInOrderNumber(){
        Result<Integer> result = messageService.waitVerfyInOrderNumber();
        return result;
    }

    /**
     * 查询待审核出库单的数量
     * @return
     */
    @RequestMapping("/waitVerfyOutOrderNumber.action")
    private @ResponseBody Result<Integer> waitVerfyOutOrderNumber(){
        Result<Integer> result = messageService.waitVerfyOutOrderNumber();
        return result;
    }

    /**
     * 查询未处理的发货单数量
     * @return
     */
    @RequestMapping("/waitDoDeliverOrderNumber.action")
    private @ResponseBody Result<Integer> waitDoDeliverOrderNumber(){
        Result<Integer> result = messageService.waitDoDeliverOrderNumber();
        return result;
    }

    /**
     * 查询出库存警告商品的数量
     * @return
     */
    @RequestMapping("/warnStockNumber.action")
    private @ResponseBody Result<Integer> warnStockNumber(){
        Result<Integer> result = messageService.warnStockNumber();
        return result;
    }

    /**
     * 查询提醒总数
     * @return
     */
    @RequestMapping("/count.action")
    private @ResponseBody Result<Integer> count(){
        return new Result<>(200, "ok", waitVerfyInOrderNumber().getData() + waitVerfyOutOrderNumber().getData() + waitDoDeliverOrderNumber().getData());
    }
}
