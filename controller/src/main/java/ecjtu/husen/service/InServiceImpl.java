package ecjtu.husen.service;

import ecjtu.husen.dao.InDao;
import ecjtu.husen.dao.InOrderOperationNoteDao;
import ecjtu.husen.dao.ItemDao;
import ecjtu.husen.dao.SportDao;
import ecjtu.husen.model.OperationNote;
import ecjtu.husen.pojo.DAO.*;
import ecjtu.husen.pojo.DTO.InStatu;
import ecjtu.husen.rabbitmq.service.Producer;
import ecjtu.husen.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 11785
 */
@Service
@Transactional
public class InServiceImpl implements InService {
    @Autowired
    private InDao inDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private SportDao sportDao;
    @Autowired
    private InOrderOperationNoteDao inOrderOperationNoteDao;
    @Autowired
    private Producer producer;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Value("${rabbitmq.calItemStock.queue}")
    private String claItemStockQueueName;
    @Value("${rabbitmq.claStock.key}")
    private String claItemStockKeyName;
    @Value("${rabbitmq.exchange}")
    private String rabbitmqExchange;
    @Override
    public List<Item> findAllItemBySportId(Integer sportId) {
        if(sportId == null){
            return itemDao.findIdAndName();
        }
        return itemDao.findIdAndNameBySportId(sportId);
    }

    @Override
    public List<Sport> findAllSport() {
        return sportDao.findAllSport();
    }

    @Override
    public void addInOrder(InOrder inOrder) {
        /*
        * 添加入库单
        *   状态未审核
        *   入库单生成时间
        *   直接添加进数据库，先不计算相关数据，等到审核通过再用rabbitmq来计算
        *   记录入库单操作日志
        * */
        inOrder.setInStatu(InStatu.notReviewde);
        inOrder.setInTime(new Date(System.currentTimeMillis()));
        try{
            inDao.addInOrder(inOrder);
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addInOperationNote(inOrder.getUserIn().getUserId(), "添加入库单", "添加成功");
                }
            });
        }catch (Exception e){
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addInOperationNote(inOrder.getUserIn().getUserId(), "添加入库单", "添加失败");
                }
            });
        }
    }

    @Override
    public Page<InOrder> pageIn(Integer currentPage) {
        Page<InOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = inDao.findTotal();
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
        List<InOrder> inOrders = inDao.page(currentPage, page.getPageSize());
        page.setContent(inOrders);
        return page;
    }

    @Override
    public void deleteIn(Integer inOrderId) {
        InOrder inOrder = inDao.findById(inOrderId);
        if(inOrder != null){
            if(inOrder.getInStatu().getValue() != 0){
                throw new RuntimeException("该入库单不能删除");
            }
            try{
                inDao.deleteIn(inOrderId);
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        addInOperationNote(inOrder.getUserIn().getUserId(), "删除入库单", "删除成功");
                    }
                });
            }catch (Exception e){
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        addInOperationNote(inOrder.getUserIn().getUserId(), "删除入库单", "删除失败");
                    }
                });
            }
        }
    }

    @Override
    public Page<InOrder> findIn(InOrder inOrderCondition, Integer currentPage) {
        Page<InOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = inDao.findTotalWhen(inOrderCondition);
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
        List<InOrder> inOrders = inDao.pageFind(currentPage, page.getPageSize(), inOrderCondition);
        page.setContent(inOrders);
        return page;
    }

    @Override
    public Page<InOrder> pageNoVerfy(Integer currentPage) {
        Page<InOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = inDao.findVerfyTotal();
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
        List<InOrder> inOrders = inDao.pageVerfy(currentPage, page.getPageSize());
        page.setContent(inOrders);
        return page;
    }

    @Override
    public Page<InOrder> findVerfyIn(InOrder inOrderCondition, Integer currentPage) {
        Page<InOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = inDao.findTotalVerfyWhen(inOrderCondition);
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
        List<InOrder> inOrders = inDao.pageFindVerfy(currentPage, page.getPageSize(), inOrderCondition);
        page.setContent(inOrders);
        return page;
    }

    /**
     * 审核通过要计算该商品的库存
     * 适用rabbitmq发送消息进行计算
     * @param userId
     * @param inOrderId
     */
    @Override
    public void access(Integer userId, Integer inOrderId) {
        try{
            //修改数据库状态为通过
            inDao.access(inOrderId);
            //记录审核的人员
            inDao.noteVerfy(inOrderId, userId);
            //计算该商品的库存
            producer.sendQueue(rabbitmqExchange, claItemStockKeyName, inOrderId.toString());
            //添加操作日志
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addInOperationNote(userId, "审核入库单", "审核通过");
                }
            });
        }catch (Exception e){
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addInOperationNote(userId, "审核入库单", "审核失败");
                }
            });
        }
    }

    /**
     * 不通过的不用计算
     * @param userId
     * @param inOrderId
     */
    @Override
    public void faile(Integer userId, Integer inOrderId) {
        try {
            //记录是谁审核
            inDao.faile(inOrderId);
            //记录是谁进行审核
            inDao.noteVerfyFaile(userId, inOrderId);
            //记录操作记录
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addInOperationNote(userId, "审核入库单", "审核不通过");
                }
            });
        }catch (Exception e){
            //记录操作记录
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addInOperationNote(userId, "审核入库单", "审核失败");
                }
            });
        }

    }

    /**
     * 添加入库单操作记录
     * @param userId
     * @param operationContent
     * @param operationResult
     */
    public void addInOperationNote(Integer userId, String operationContent, String operationResult){
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
