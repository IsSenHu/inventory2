package ecjtu.husen.service;

import ecjtu.husen.dao.InOrderOperationNoteDao;
import ecjtu.husen.dao.OutDao;
import ecjtu.husen.model.OperationNote;
import ecjtu.husen.pojo.DAO.OutOrder;
import ecjtu.husen.pojo.DAO.Row;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.rabbitmq.service.Producer;
import ecjtu.husen.util.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author husen
 */
@Service
@Transactional
public class OutServiceImpl implements OutService {
    @Autowired
    private OutDao outDao;
    @Autowired
    private Producer producer;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private InOrderOperationNoteDao inOrderOperationNoteDao;
    @Value("${rabbitmq.calItemStockOut.queue}")
    private String claItemStockQueueName;
    @Value("${rabbitmq.claStockout.key}")
    private String claItemStockKeyName;
    @Value("${rabbitmq.exchange}")
    private String rabbitmqExchange;

    @Override
    @RequiresPermissions(value = "InManage")
    public Page<OutOrder> page(Integer currentPage) {
        Page<OutOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = outDao.findTotal();
        page.setRowsTotal(rowsTotal);
        /*
        * 开始计算总共有多少页并进行设置
        * */
        if(rowsTotal % page.getPageSize() == 0){
            page.setTotalPage(rowsTotal / page.getPageSize());
        }else {
            page.setTotalPage(rowsTotal / page.getPageSize() + 1);
        }
        /*
        * 判断传入的当前页是否合法
        * */
        if(currentPage <= 0){
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }else if (currentPage > page.getTotalPage()){
            currentPage = page.getTotalPage();
            page.setCurrentPage(currentPage);
        }
        List<OutOrder> outOrders = outDao.page(currentPage, page.getPageSize());
        page.setContent(outOrders);
        return page;
    }

    @Override
    public Page<OutOrder> findOut(OutOrder outOrder, String start, String end, Integer currentPage) throws ParseException {
        Page<OutOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = outDao.findTotalWhen(outOrder, start, end);
        page.setRowsTotal(rowsTotal);
        /*
        * 开始计算总共有多少页并进行设置
        * */
        if(rowsTotal % page.getPageSize() == 0){
            page.setTotalPage(rowsTotal / page.getPageSize());
        }else {
            page.setTotalPage(rowsTotal / page.getPageSize() + 1);
        }
        /*
        * 判断传入的当前页是否合法
        * */
        if(currentPage <= 0){
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }else if (currentPage > page.getTotalPage()){
            currentPage = page.getTotalPage();
            page.setCurrentPage(currentPage);
        }
        List<OutOrder> outOrders = outDao.pageFind(currentPage, page.getPageSize(), outOrder, start, end);
        for (OutOrder x : outOrders){
            List<Row> rows = outDao.rows(x.getOutOrderId());
            x.setRows(rows);
        }
        page.setContent(outOrders);
        return page;
    }

    @Override
    public boolean checkStock(Integer outOrderId) {
        List<Row> rows = outDao.rows(outOrderId);
        for(Row row : rows){
            if(row.getNumber() > row.getItem().getCurrentInventory()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void access(Integer userId, Integer outOrderId) {
        try {
            //修改数据库状态为通过并记录审核的人员
            outDao.access(userId, outOrderId);
            //重新计算该商品的库存并生成发货单
            producer.sendQueue(rabbitmqExchange, claItemStockKeyName, outOrderId.toString());
            //添加操作日志
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addOutOperationNote(userId, "审核出库单", "审核通过");
                }
            });
        }catch (Exception e){
            //添加操作日志
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addOutOperationNote(userId, "审核出库单", "审核失败");
                }
            });
        }
    }

    @Override
    public void faile(Integer userId, Integer outOrderId) {
        try{
            //修改数据库状态为不通过并记录审核的人员
            outDao.faile(userId, outOrderId);
            //不用计算商品的库存，记录操作记录就可以了
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addOutOperationNote(userId, "审核出库单", "审核不通过");
                }
            });
        }catch (Exception e){
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addOutOperationNote(userId, "审核出库单", "审核失败");
                }
            });
        }
    }

    @Override
    public Page<OutOrder> pageNoverfy(Integer currentPage) {
        Page<OutOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = outDao.findNoVerfyTotal();
        page.setRowsTotal(rowsTotal);
        /*
        * 开始计算总共有多少页并进行设置
        * */
        if(rowsTotal % page.getPageSize() == 0){
            page.setTotalPage(rowsTotal / page.getPageSize());
        }else {
            page.setTotalPage(rowsTotal / page.getPageSize() + 1);
        }
        /*
        * 判断传入的当前页是否合法
        * */
        if(currentPage <= 0){
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }else if (currentPage > page.getTotalPage()){
            currentPage = page.getTotalPage();
            page.setCurrentPage(currentPage);
        }
        List<OutOrder> outOrders = outDao.pageNoVerfy(currentPage, page.getPageSize());
        for (OutOrder outOrder : outOrders){
            outOrder.setRows(outDao.rows(outOrder.getOutOrderId()));
        }
        page.setContent(outOrders);
        return page;
    }

    @Override
    public Page<OutOrder> findNoVerfy(OutOrder outOrder, String start, String end, Integer currentPage) throws ParseException {
        Page<OutOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = outDao.findNoVerfyTotal(outOrder, start, end);
        page.setRowsTotal(rowsTotal);
        /*
        * 开始计算总共有多少页并进行设置
        * */
        if(rowsTotal % page.getPageSize() == 0){
            page.setTotalPage(rowsTotal / page.getPageSize());
        }else {
            page.setTotalPage(rowsTotal / page.getPageSize() + 1);
        }
        /*
        * 判断传入的当前页是否合法
        * */
        if(currentPage <= 0){
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }else if (currentPage > page.getTotalPage()){
            currentPage = page.getTotalPage();
            page.setCurrentPage(currentPage);
        }
        List<OutOrder> outOrders = outDao.pageNoVerfy(outOrder, start, end, currentPage, page.getPageSize());
        page.setContent(outOrders);
        return page;
    }

    /**
     * 添加出库单操作记录
     * @param userId
     * @param operationContent
     * @param operationResult
     */
    public void addOutOperationNote(Integer userId, String operationContent, String operationResult){
        OperationNote note = new OperationNote();
        //记录操作的时间
        note.setOperationTime(new Date(System.currentTimeMillis()));
        //记录是谁操作
        note.setOperationPerson(new UserPO());
        note.getOperationPerson().setUserId(userId);
        //记录操作的内容
        note.setOperationContent(operationContent);
        //记录操作的结果
        note.setOperationResult(operationResult);
        //保存到数据库
        inOrderOperationNoteDao.addNote(note);
    }
}
