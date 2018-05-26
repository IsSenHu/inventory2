package ecjtu.husen.dao;

import java.util.List;

/**
 * @author 11785
 */
public interface ChartDao {

    List<Integer> findTodayAllItemId();

    String findTodayItemName(Integer id);

    Integer findTodatData(Integer id);

    Double findTodayMoney(Integer id);

    List<Integer> findWeekItemId();

    String findItemName(Integer id);

    Integer monNumber(Integer id);

    Integer tuesNumber(Integer id);

    Integer wednesNumber(Integer id);

    Integer thursNumber(Integer id);

    Integer friNumber(Integer id);

    Integer saturNumber(Integer id);

    Integer sunNumber(Integer id);

    Double monMoney(Integer id);

    Double tuesMoney(Integer id);

    Double wednesMoney(Integer id);

    Double thurMoney(Integer id);

    Double friMoney(Integer id);

    Double satMoney(Integer id);

    Double sunMoney(Integer id);

    List<Integer> findYearId();

    Double oneMoney(Integer id);

    Double twoMoney(Integer id);

    Double threeMoney(Integer id);

    Double fouthMoney(Integer id);

    Double fiveMoney(Integer id);

    Double sixMoney(Integer id);

    Double sevenMoney(Integer id);

    Double eightMoney(Integer id);

    Double nineMoney(Integer id);

    Double tenMoney(Integer id);

    Double elevenMoney(Integer id);

    Double towlveMoney(Integer id);

    Integer oneNumber(Integer id);
    Integer twoNumber(Integer id);
    Integer threeNumber(Integer id);
    Integer fouthNumber(Integer id);
    Integer fiveNumber(Integer id);
    Integer sixNumber(Integer id);
    Integer sevenNumber(Integer id);
    Integer eightNumber(Integer id);
    Integer nineNumber(Integer id);
    Integer tenNumber(Integer id);
    Integer elevenNumber(Integer id);
    Integer towlveNumber(Integer id);

    List<Integer> findAllItemId();

    Integer findCurrentInventory(Integer id);

    Double findCurrentPrice(Integer id);
}
