package ecjtu.husen.service;

import ecjtu.husen.model.InventoryResult;
import ecjtu.husen.model.TodayResult;
import ecjtu.husen.model.WeekResult;
import ecjtu.husen.model.YearResult;

/**
 * @author 11785
 */
public interface ChartService {
    TodayResult todayAllItemResult();
    TodayResult todayAllItemMoney();
    WeekResult weekAllItemResult();

    WeekResult<Double> weekAllItemMoney();

    YearResult<Integer> yearAllItemResult();

    YearResult<Double> yearAllItemMoney();

    InventoryResult<Integer> inventoryResult();

    InventoryResult<Double> doubleInventoryResult();
}
