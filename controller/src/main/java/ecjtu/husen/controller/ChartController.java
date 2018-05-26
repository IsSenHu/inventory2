package ecjtu.husen.controller;

import ecjtu.husen.model.*;
import ecjtu.husen.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author husen
 */
@Controller
public class ChartController {
    @Autowired
    private ChartService chartService;

    /**
     * 统计今日的信息
     * @return
     */
    @RequestMapping("/lttoday.action")
    private ModelAndView today(){
        /*
        * 1.统计今日入库的每个商品的入库量
        * 2.统计今日入库的每个商品的总金额
        * */
        TodayResult<Integer> todayAllItemResult = chartService.todayAllItemResult();
        TodayResult<Double> todayAllItemMoney = chartService.todayAllItemMoney();
        return new ModelAndView("views/jsp/today")
                .addObject("todayAllItemResult", todayAllItemResult)
                .addObject("todayAllItemMoney", todayAllItemMoney);
    }
    @RequestMapping("/ltweek.action")
    private ModelAndView week(){
        /*
        * 1.统计本周入库的每个商品的入库量
        * 2.统计本周入库的每个商品的总金额
        * */
        WeekResult<Integer> weekAllItemResult = chartService.weekAllItemResult();
        WeekResult<Double> weekAllItemMoney = chartService.weekAllItemMoney();
        return new ModelAndView("views/jsp/week")
                .addObject("weekAllItemResult", weekAllItemResult)
                .addObject("weekAllItemMoney", weekAllItemMoney);
    }
    @RequestMapping("/ltyear.action")
    private ModelAndView year(){
        /*
        * 1.统计今年入库的每个商品的入库量
        * 2.统计今年入库的每个商品的总金额
        * */
        YearResult<Integer> yearAllItemResult = chartService.yearAllItemResult();
        YearResult<Double> yearAllItemMoney = chartService.yearAllItemMoney();
        return new ModelAndView("views/jsp/year")
                .addObject("yearAllItemResult", yearAllItemResult)
                .addObject("yearAllItemMoney", yearAllItemMoney);

    }

    /**
     * 查找所有商品的库存数量和库存理论价值
     * @return
     */
    @RequestMapping("/ltInventory.action")
    private ModelAndView allInventory(){
        InventoryResult<Integer> inventoryResult = chartService.inventoryResult();
        InventoryResult<Double> doubleInventoryResult = chartService.doubleInventoryResult();
        return new ModelAndView("views/jsp/inventory")
                .addObject("inventoryResult", inventoryResult)
                .addObject("doubleInventoryResult", doubleInventoryResult);
    }
}
