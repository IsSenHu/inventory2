package ecjtu.husen.service;

import ecjtu.husen.dao.ChartDao;
import ecjtu.husen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11785
 */
@Service
@Transactional
public class ChartServiceImpl implements ChartService {
    @Autowired
    private ChartDao chartDao;
    @Override
    public TodayResult todayAllItemResult() {
        /*
        * 1.先查出今天入库商品的所有id
        * 2.先查找出今日入库商品的的所有的名字
        * 3.遍历每个名字获得今日入库商品的总数量
        * */
        List<Integer> ids = chartDao.findTodayAllItemId();
        List<String> xAxis = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        if(ids == null){
            ids = new ArrayList<>();
        }
        for(Integer id : ids){
            xAxis.add(chartDao.findTodayItemName(id));
            data.add(chartDao.findTodatData(id));
        }
        TodayResult<Integer> todayResult = new TodayResult();
        todayResult.setTitle("今日商品入库情况");
        todayResult.setLegend("入库数量");
        todayResult.setData(data);
        todayResult.setxAxis(xAxis);
        todayResult.setSeriesName("入库数量");
        todayResult.setSeriesType("bar");
        todayResult.setxAxisSize(xAxis.size());
        todayResult.setDataSize(data.size());
        return todayResult;
    }

    @Override
    public TodayResult todayAllItemMoney() {
        List<Integer> ids = chartDao.findTodayAllItemId();
        List<String> xAxis = new ArrayList<>();
        List<Double> data = new ArrayList<>();
        if(ids == null){
            ids = new ArrayList<>();
        }
        for(Integer id : ids){
            xAxis.add(chartDao.findTodayItemName(id));
            data.add(chartDao.findTodayMoney(id));
        }
        TodayResult<Double> todayResult = new TodayResult();
        todayResult.setTitle("今日商品入库金额");
        todayResult.setLegend("入库金额");
        todayResult.setData(data);
        todayResult.setxAxis(xAxis);
        todayResult.setSeriesName("入库金额");
        todayResult.setSeriesType("bar");
        todayResult.setxAxisSize(xAxis.size());
        todayResult.setDataSize(data.size());
        return todayResult;
    }

    @Override
    public WeekResult weekAllItemResult() {
        /*
        * 1.先查询出本周入库的所有商品的Id
        * 2.再用这个id去查名字和星期一到星期日的每日的入库量
        * */
        List<Integer> ids = chartDao.findWeekItemId();
        List<Series<Integer>> seriesList = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        if(ids == null){
            ids = new ArrayList<>();
        }
        for(Integer id : ids){
            String itemName = chartDao.findItemName(id);
            Integer monNumber = chartDao.monNumber(id);
            Integer tuesNumber = chartDao.tuesNumber(id);
            Integer wednesNumber = chartDao.wednesNumber(id);
            Integer thursNumber = chartDao.thursNumber(id);
            Integer friNumber = chartDao.friNumber(id);
            Integer saturNumber = chartDao.saturNumber(id);
            Integer sunNumber = chartDao.sunNumber(id);
            List<Integer> data = new ArrayList<>();
            data.add(monNumber);
            data.add(tuesNumber);
            data.add(wednesNumber);
            data.add(thursNumber);
            data.add(friNumber);
            data.add(saturNumber);
            data.add(sunNumber);

            legend.add(itemName);

            Series<Integer> series = new Series<>();
            series.setName(itemName);
            series.setType("line");
            series.setStack("入库数量");
            series.setData(data);
            series.setAtaSize(data.size());

            seriesList.add(series);
        }
        WeekResult<Integer> weekResult = new WeekResult<>();
        weekResult.setTitle("本周商品入库数量");
        weekResult.setLegend(legend);
        weekResult.setLegendSize(legend.size());
        weekResult.setSeries(seriesList);
        return weekResult;
    }

    @Override
    public WeekResult<Double> weekAllItemMoney() {
        List<Integer> ids = chartDao.findWeekItemId();
        List<Series<Double>> seriesList = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        if(ids == null){
            ids = new ArrayList<>();
        }
        for(Integer id : ids){
            String itemName = chartDao.findItemName(id);
            Double monMoney = chartDao.monMoney(id);
            Double tuesMoney = chartDao.tuesMoney(id);
            Double wednesMoney = chartDao.wednesMoney(id);
            Double thurMoney = chartDao.thurMoney(id);
            Double friMoney = chartDao.friMoney(id);
            Double satMoney = chartDao.satMoney(id);
            Double sunMoney = chartDao.sunMoney(id);

            legend.add(itemName);

            List<Double> data = new ArrayList<>();
            data.add(monMoney);
            data.add(tuesMoney);
            data.add(wednesMoney);
            data.add(thurMoney);
            data.add(friMoney);
            data.add(satMoney);
            data.add(sunMoney);

            Series<Double> series = new Series<>();
            series.setName(itemName);
            series.setType("line");
            series.setStack("入库金额");
            series.setData(data);
            series.setAtaSize(data.size());

            seriesList.add(series);
        }
        WeekResult<Double> weekResult = new WeekResult<>();
        weekResult.setTitle("本周商品入库金额");
        weekResult.setLegend(legend);
        weekResult.setLegendSize(legend.size());
        weekResult.setSeries(seriesList);
        return weekResult;
    }

    @Override
    public YearResult<Integer> yearAllItemResult() {
        List<Integer> ids = chartDao.findYearId();
        List<Series<Integer>> seriesList = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        if(ids == null){
            ids = new ArrayList<>();
        }
        for(Integer id : ids){
            String itemName = chartDao.findItemName(id);
            Integer oneNumber = chartDao.oneNumber(id);
            Integer twoNumber = chartDao.twoNumber(id);
            Integer threeNumber = chartDao.threeNumber(id);
            Integer fouthNumber = chartDao.fouthNumber(id);
            Integer fiveNumber = chartDao.fiveNumber(id);
            Integer sixNumber = chartDao.sixNumber(id);
            Integer sevenNumber = chartDao.sevenNumber(id);
            Integer eightNumber = chartDao.eightNumber(id);
            Integer nineNumber = chartDao.nineNumber(id);
            Integer tenNumber = chartDao.tenNumber(id);
            Integer elevenNumber = chartDao.elevenNumber(id);
            Integer towlveNumber = chartDao.towlveNumber(id);

            legend.add(itemName);

            List<Integer> data = new ArrayList<>();
            data.add(oneNumber);
            data.add(twoNumber);
            data.add(threeNumber);
            data.add(fouthNumber);
            data.add(fiveNumber);
            data.add(sixNumber);
            data.add(sevenNumber);
            data.add(eightNumber);
            data.add(nineNumber);
            data.add(tenNumber);
            data.add(elevenNumber);
            data.add(towlveNumber);

            Series<Integer> series = new Series<>();
            series.setName(itemName);
            series.setType("line");
            series.setStack("入库数量");
            series.setData(data);
            series.setAtaSize(data.size());

            seriesList.add(series);
        }
        YearResult<Integer> yearResult = new YearResult<>();
        yearResult.setTitle("今年商品入库数量");
        yearResult.setLegend(legend);
        yearResult.setLegendSize(legend.size());
        yearResult.setSeries(seriesList);
        return yearResult;
    }

    @Override
    public YearResult<Double> yearAllItemMoney() {
        List<Integer> ids = chartDao.findYearId();
        List<Series<Double>> seriesList = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        if(ids == null){
            ids = new ArrayList<>();
        }
        for(Integer id : ids){
            String itemName = chartDao.findItemName(id);
            Double oneMoney = chartDao.oneMoney(id);
            Double twoMoney = chartDao.twoMoney(id);
            Double threeMoney = chartDao.threeMoney(id);
            Double fouthMoney = chartDao.fouthMoney(id);
            Double fiveMoney = chartDao.fiveMoney(id);
            Double sixMoney = chartDao.sixMoney(id);
            Double sevenMoney = chartDao.sevenMoney(id);
            Double eightMoney = chartDao.eightMoney(id);
            Double nineMoney = chartDao.nineMoney(id);
            Double tenMoney = chartDao.tenMoney(id);
            Double elevenMoney = chartDao.elevenMoney(id);
            Double towlveMoney = chartDao.towlveMoney(id);

            legend.add(itemName);

            List<Double> data = new ArrayList<>();
            data.add(oneMoney);
            data.add(twoMoney);
            data.add(threeMoney);
            data.add(fouthMoney);
            data.add(fiveMoney);
            data.add(sixMoney);
            data.add(sevenMoney);
            data.add(eightMoney);
            data.add(nineMoney);
            data.add(tenMoney);
            data.add(elevenMoney);
            data.add(towlveMoney);

            Series<Double> series = new Series<>();
            series.setName(itemName);
            series.setType("line");
            series.setStack("入库金额");
            series.setData(data);
            series.setAtaSize(data.size());

            seriesList.add(series);
        }
        YearResult<Double> yearResult = new YearResult<>();
        yearResult.setTitle("今年商品入库金额");
        yearResult.setLegend(legend);
        yearResult.setLegendSize(legend.size());
        yearResult.setSeries(seriesList);
        return yearResult;
    }

    @Override
    public InventoryResult<Integer> inventoryResult() {
        /*
        * 1.先查出所有商品的所有id
        * 2.先查找所有商品的的所有的名字和当前库存
        * */
        List<Integer> ids = chartDao.findAllItemId();
        List<String> xAxis = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        if(ids == null){
            ids = new ArrayList<>();
        }
        for(Integer id : ids){
            xAxis.add(chartDao.findItemName(id));
            data.add(chartDao.findCurrentInventory(id));
        }
        InventoryResult<Integer> inventoryResult = new InventoryResult();
        inventoryResult.setTitle("所有商品的库存");
        inventoryResult.setLegend("库存数量");
        inventoryResult.setData(data);
        inventoryResult.setxAxis(xAxis);
        inventoryResult.setSeriesName("库存数量");
        inventoryResult.setSeriesType("bar");
        inventoryResult.setxAxisSize(xAxis.size());
        inventoryResult.setDataSize(data.size());
        return inventoryResult;
    }

    @Override
    public InventoryResult<Double> doubleInventoryResult() {
        /*
        * 1.先查出所有商品的所有id
        * 2.先查找所有商品的的所有的名字和当前库存x建议单价
        * */
        List<Integer> ids = chartDao.findAllItemId();
        List<String> xAxis = new ArrayList<>();
        List<Double> data = new ArrayList<>();
        if(ids == null){
            ids = new ArrayList<>();
        }
        for(Integer id : ids){
            xAxis.add(chartDao.findItemName(id));
            data.add(chartDao.findCurrentPrice(id));
        }
        InventoryResult<Double> inventoryResult = new InventoryResult();
        inventoryResult.setTitle("所有商品的理论价值");
        inventoryResult.setLegend("库存理论价值");
        inventoryResult.setData(data);
        inventoryResult.setxAxis(xAxis);
        inventoryResult.setSeriesName("库存理论价值");
        inventoryResult.setSeriesType("bar");
        inventoryResult.setxAxisSize(xAxis.size());
        inventoryResult.setDataSize(data.size());
        return inventoryResult;
    }
}
