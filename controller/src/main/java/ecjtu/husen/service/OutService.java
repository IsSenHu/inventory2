package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.OutOrder;
import ecjtu.husen.util.Page;

import java.text.ParseException;

/**
 * @author 11785
 */
public interface OutService {
    Page<OutOrder> page(Integer currentPage);

    Page<OutOrder> findOut(OutOrder outOrder, String start, String end, Integer currentPage) throws ParseException;

    void access(Integer userId, Integer outOrderId);

    void faile(Integer userId, Integer outOrderId);

    Page<OutOrder> pageNoverfy(Integer currentPage);

    Page<OutOrder> findNoVerfy(OutOrder outOrder, String start, String end, Integer currentPage) throws ParseException;

    boolean checkStock(Integer outOrderId);
}
