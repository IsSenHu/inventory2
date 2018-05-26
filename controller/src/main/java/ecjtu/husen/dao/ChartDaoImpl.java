package ecjtu.husen.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 11785
 */
@Repository
public class ChartDaoImpl extends HibernateDaoSupport implements ChartDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<Integer> findTodayAllItemId() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select item.itemId from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date()) order by item.itemId");
        return query.list();
    }

    @Override
    public String findTodayItemName(Integer id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select itemName from t_item where itemId = ?");
        query.setParameter(0, id);
        return (String) query.uniqueResult();
    }

    @Override
    public Integer findTodatData(Integer id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date()) and item.itemId = ?");
        query.setParameter(0, id);
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public Double findTodayMoney(Integer id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date()) and item.itemId = ?");
        query.setParameter(0, id);
        return (Double) query.uniqueResult();
    }

    @Override
    public List<Integer> findWeekItemId() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select distinct(item.itemId) from t_inorder where inStatu = 2 and WEEK(inTime) = WEEK(current_date()) order by item.itemId");
        return query.list();
    }

    @Override
    public String findItemName(Integer id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select itemName from t_item where itemId = ?");
        query.setParameter(0, id);
        return (String) query.uniqueResult();
    }

    @Override
    public Integer monNumber(Integer id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + calToMondayLen(LocalDate.now().getDayOfWeek().getValue()) + ") and item.itemId = ?");
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return ((Long)object).intValue();
        }else {
            return 0;
        }
    }

    @Override
    public Integer tuesNumber(Integer id) {
        int value = calThusLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return ((Long)object).intValue();
        }else {
            return 0;
        }
    }

    @Override
    public Integer wednesNumber(Integer id) {
        int value = calWednesLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return ((Long)object).intValue();
        }else {
            return 0;
        }
    }

    @Override
    public Integer thursNumber(Integer id) {
        int value = calThurLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return ((Long)object).intValue();
        }else {
            return 0;
        }
    }

    @Override
    public Integer friNumber(Integer id) {
        int value = calFriLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return ((Long)object).intValue();
        }else {
            return 0;
        }
    }

    @Override
    public Integer saturNumber(Integer id) {
        int value = calSatLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return ((Long)object).intValue();
        }else {
            return 0;
        }
    }

    @Override
    public Integer sunNumber(Integer id) {
        int value = calSunLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return ((Long)object).intValue();
        }else {
            return 0;
        }
    }

    @Override
    public Double monMoney(Integer id) {
        int value = calToMondayLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return (Double) object;
        }else {
            return Double.valueOf(0);
        }
    }

    @Override
    public Double tuesMoney(Integer id) {
        int value = calThusLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return (Double) object;
        }else {
            return Double.valueOf(0);
        }
    }

    @Override
    public Double wednesMoney(Integer id) {
        int value = calWednesLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return (Double) object;
        }else {
            return Double.valueOf(0);
        }
    }

    @Override
    public Double thurMoney(Integer id) {
        int value = calThurLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return (Double) object;
        }else {
            return Double.valueOf(0);
        }
    }

    @Override
    public Double friMoney(Integer id) {
        int value = calFriLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return (Double) object;
        }else {
            return Double.valueOf(0);
        }
    }

    @Override
    public Double satMoney(Integer id) {
        int value = calSatLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return (Double) object;
        }else {
            return Double.valueOf(0);
        }
    }

    @Override
    public Double sunMoney(Integer id) {
        int value = calSunLen(LocalDate.now().getDayOfWeek().getValue());
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() - " + value + ") and item.itemId = ?");
        }else {
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and DATE(inTime) = DATE(current_date() + " + value + ") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return (Double) object;
        }else {
            return Double.valueOf(0);
        }
    }

    @Override
    public List<Integer> findYearId() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select distinct(item.itemId) from t_inorder where inStatu = 2 and YEAR(inTime) = YEAR(current_date()) order by item.itemId");
        return query.list();
    }

    @Override
    public Double oneMoney(Integer id) {
        return calYearMoney(calMonthlen(1), id);
    }

    @Override
    public Double twoMoney(Integer id) {
        return calYearMoney(calMonthlen(2), id);
    }

    @Override
    public Double threeMoney(Integer id) {
        return calYearMoney(calMonthlen(3), id);
    }

    @Override
    public Double fouthMoney(Integer id) {
        return calYearMoney(calMonthlen(4), id);
    }

    @Override
    public Double fiveMoney(Integer id) {
        return calYearMoney(calMonthlen(5), id);
    }

    @Override
    public Double sixMoney(Integer id) {
        return calYearMoney(calMonthlen(6), id);
    }

    @Override
    public Double sevenMoney(Integer id) {
        return calYearMoney(calMonthlen(7), id);
    }

    @Override
    public Double eightMoney(Integer id) {
        return calYearMoney(calMonthlen(8), id);
    }

    @Override
    public Double nineMoney(Integer id) {
        return calYearMoney(calMonthlen(9), id);
    }

    @Override
    public Double tenMoney(Integer id) {
        return calYearMoney(calMonthlen(10), id);
    }

    @Override
    public Double elevenMoney(Integer id) {
        return calYearMoney(calMonthlen(11), id);
    }

    @Override
    public Double towlveMoney(Integer id) {
        return calYearMoney(calMonthlen(12), id);
    }

    @Override
    public Integer oneNumber(Integer id) {
        return calYearNumber(calMonthlen(1), id);
    }

    @Override
    public Integer twoNumber(Integer id) {
        return calYearNumber(calMonthlen(2), id);
    }

    @Override
    public Integer threeNumber(Integer id) {
        return calYearNumber(calMonthlen(3), id);
    }

    @Override
    public Integer fouthNumber(Integer id) {
        return calYearNumber(calMonthlen(4), id);
    }

    @Override
    public Integer fiveNumber(Integer id) {
        return calYearNumber(calMonthlen(5), id);
    }

    @Override
    public Integer sixNumber(Integer id) {
        return calYearNumber(calMonthlen(6), id);
    }

    @Override
    public Integer sevenNumber(Integer id) {
        return calYearNumber(calMonthlen(7), id);
    }

    @Override
    public Integer eightNumber(Integer id) {
        return calYearNumber(calMonthlen(8), id);
    }

    @Override
    public Integer nineNumber(Integer id) {
        return calYearNumber(calMonthlen(9), id);
    }

    @Override
    public Integer tenNumber(Integer id) {
        return calYearNumber(calMonthlen(10), id);
    }

    @Override
    public Integer elevenNumber(Integer id) {
        return calYearNumber(calMonthlen(11), id);
    }

    @Override
    public Integer towlveNumber(Integer id) {
        return calYearNumber(calMonthlen(12), id);
    }

    @Override
    public List<Integer> findAllItemId() {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select distinct(itemId) from t_item");
        return query.list();
    }

    @Override
    public Integer findCurrentInventory(Integer id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select currentInventory from t_item where itemId = ?");
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        return (Integer) object;
    }

    @Test
    public void test(){
        System.out.println(calMonthlen(11));
    }
    private int calToMondayLen(int value){
        if(value == 1){
            return 0;
        }else if(value == 2){
            return 1;
        }else if(value == 3){
            return 2;
        }else if(value == 4){
            return 3;
        }else if(value == 5){
            return 4;
        }else if(value == 6){
            return 5;
        }else {
            return 6;
        }
    }
    private int calThusLen(int value){
        if(value == 1){
            return -1;
        }else if(value == 2){
            return 0;
        }else if(value == 3){
            return 1;
        }else if(value == 4){
            return 2;
        }else if(value == 5){
            return 3;
        }else if(value == 6){
            return 4;
        }else{
            return 5;
        }
    }
    private int calWednesLen(int value){
        if(value == 1){
            return -2;
        }else if(value == 2){
            return -1;
        }else if(value == 3){
            return 0;
        }else if(value == 4){
            return 1;
        }else if(value == 5){
            return 2;
        }else if(value == 6){
            return 3;
        }else{
            return 4;
        }
    }
    private int calThurLen(int value){
        if(value == 1){
            return -3;
        }else if(value == 2){
            return -2;
        }else if(value == 3){
            return -1;
        }else if(value == 4){
            return 0;
        }else if(value == 5){
            return 1;
        }else if(value == 6){
            return 2;
        }else{
            return 3;
        }
    }
    private int calFriLen(int value){
        if(value == 1){
            return -4;
        }else if(value == 2){
            return -3;
        }else if(value == 3){
            return -2;
        }else if(value == 4){
            return -1;
        }else if(value == 5){
            return 0;
        }else if(value == 6){
            return 1;
        }else{
            return 2;
        }
    }
    private int calSatLen(int value){
        if(value == 1){
            return -5;
        }else if(value == 2){
            return -4;
        }else if(value == 3){
            return -3;
        }else if(value == 4){
            return -2;
        }else if(value == 5){
            return -1;
        }else if(value == 6){
            return 0;
        }else{
            return 1;
        }
    }
    private int calSunLen(int value){
        if(value == 1){
            return -6;
        }else if(value == 2){
            return -5;
        }else if(value == 3){
            return -4;
        }else if(value == 4){
            return -3;
        }else if(value == 5){
            return -2;
        }else if(value == 6){
            return -1;
        }else{
            return 0;
        }
    }

    /**
     * 计算给定月份与现在月份之间的距离
     * @param value
     * @return
     */
    private int calMonthlen(int value){
        int offset = LocalDate.now().getMonthValue();
        return offset - value;
    }

    private Double calYearMoney(int value, Integer id){
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and MONTH(inTime) = (MONTH(current_date) - " + value +") and item.itemId = ?");
        }else if(value < 0){
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(totalMoney) from t_inorder where inStatu = 2 and MONTH(inTime) = (MONTH(current_date) + " + value +") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object o = query.uniqueResult();
        if(o != null){
            return (Double) o;
        }else {
            return Double.valueOf(0);
        }
    }

    private int calYearNumber(int value, Integer id){
        Query query = null;
        if(value >= 0){
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and MONTH(inTime) = (MONTH(current_date) - " + value +") and item.itemId = ?");
        }else if(value < 0){
            value = new BigDecimal(value).abs().intValue();
            query = getSessionFactory().getCurrentSession()
                    .createQuery("select sum(itmeNumber) from t_inorder where inStatu = 2 and MONTH(inTime) = (MONTH(current_date) + " + value +") and item.itemId = ?");
        }
        query.setParameter(0, id);
        Object object = query.uniqueResult();
        if(object != null){
            return ((Long)object).intValue();
        }else {
            return Integer.valueOf(0);
        }
    }

    @Override
    public Double findCurrentPrice(Integer id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("select (currentInventory * suggestRetailPrice) from t_item where itemId = ?");
        query.setParameter(0, id);
        return (Double) query.uniqueResult();
    }
}
