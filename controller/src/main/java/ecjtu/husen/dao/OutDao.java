package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.DeliverOrder;
import ecjtu.husen.pojo.DAO.OutOrder;
import ecjtu.husen.pojo.DAO.Row;

import java.text.ParseException;
import java.util.List;

/**
 * @author 11785
 */
public interface OutDao {
    int findTotal();

    List<OutOrder> page(Integer currentPage, int pageSize);

    int findTotalWhen(OutOrder outOrder, String start, String end) throws ParseException;

    List<OutOrder> pageFind(Integer currentPage, int pageSize, OutOrder outOrder, String start, String end) throws ParseException;

    void access(Integer userId, Integer outOrderId);

    OutOrder findById(Integer outOrderId);

    void faile(Integer userId, Integer outOrderId);

    int findNoVerfyTotal();

    List<OutOrder> pageNoVerfy(Integer currentPage, int pageSize);

    int findNoVerfyTotal(OutOrder outOrder, String start, String end) throws ParseException;

    List<OutOrder> pageNoVerfy(OutOrder outOrder, String start, String end, Integer currentPage, int pageSize) throws ParseException;

    void save(List<Row> rows, OutOrder outOrder);

    void createDeliver(Integer outOrderId, DeliverOrder deliverOrder);

    void calItemStockOut(Integer outOrderId);

    List<Row> rows(Integer outOrderId);

    List<Row> rows2(Integer deliverOrderId);
}
