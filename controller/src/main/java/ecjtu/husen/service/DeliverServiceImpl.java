package ecjtu.husen.service;

import ecjtu.husen.dao.DeliverDao;
import ecjtu.husen.dao.OutDao;
import ecjtu.husen.dao.VerfyNoteDao;
import ecjtu.husen.model.OperationNote;
import ecjtu.husen.pojo.DAO.DeliverOrder;
import ecjtu.husen.pojo.DAO.Row;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.rabbitmq.service.Producer;
import ecjtu.husen.util.Page;
import ecjtu.husen.vo.FindDeliver;
import ecjtu.husen.vo.GlobVar;
import ecjtu.husen.vo.Result;
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
public class DeliverServiceImpl implements DeliverService {

    @Autowired
    private VerfyNoteDao verfyNoteDao;
    @Autowired
    private DeliverDao deliverDao;
    @Autowired
    private OutDao outDao;
    @Autowired
    private Producer producer;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.push.delived.key}")
    private String pushDelivedKey;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public Page<DeliverOrder> find(Integer currentPage, FindDeliver findDeliver) {
        Page<DeliverOrder> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = deliverDao.findTotal(findDeliver);
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
        List<DeliverOrder> deliverOrders = deliverDao.page(currentPage, page.getPageSize(), findDeliver);
        for (DeliverOrder x : deliverOrders){
            List<Row> rows = outDao.rows2(x.getDeliverOrderId());
            x.setRows(rows);
        }
        page.setContent(deliverOrders);
        return page;
    }

    @Override
    public Result<Integer> deliverGoods(Integer deliverOrderId, UserPO user) {
        DeliverOrder deliverOrder = deliverDao.findById(deliverOrderId);
        if(GlobVar.DELIVER_NO_DONE.equals(deliverOrder.getStatu())){
            /**
             * 1，修改发货单状态为已发货
             * 2，推送发货消息生成起始物流信息，（存到商家系统的数据库里）
             * 3，保存操作记录
             * */
            try {
                deliverDao.deliverGoods(deliverOrderId);
                producer.sendQueue(exchange, pushDelivedKey, deliverOrder.getOrderId());
                taskExecutor.execute(() -> {
                    OperationNote operationNote = new OperationNote();
                    operationNote.setOperationPerson(user);
                    operationNote.setOperationContent("处理发货单");
                    operationNote.setOperationResult("处理成功{发货}");
                    operationNote.setOperationTime(new Date());
                    verfyNoteDao.save(operationNote);
                });
            }catch (Exception e){
                taskExecutor.execute(() -> {
                    OperationNote operationNote = new OperationNote();
                    operationNote.setOperationPerson(user);
                    operationNote.setOperationContent("处理发货单");
                    operationNote.setOperationResult("处理失败{发货}");
                    operationNote.setOperationTime(new Date());
                    verfyNoteDao.save(operationNote);
                });
            }
            return new Result<>(200, "推送发货消息成功！", deliverOrderId);
        }else {
            return new Result<>(500, "你已经操作过了！", null);
        }
    }

    @Override
    public Result<Integer> cancelDeliver(Integer deliverOrderId, UserPO user) {
        DeliverOrder deliverOrder = deliverDao.findById(deliverOrderId);
        if(GlobVar.DELIVER_NO_DONE.equals(deliverOrder.getStatu())){
            /**
             * 1，修改发货单状态为取消发货
             * 2，保存操作记录
             * */
            try {
                deliverOrder.setStatu(GlobVar.DELIVER_NO_DELIVED);
                deliverDao.save(deliverOrder);
                taskExecutor.execute(() -> {
                    OperationNote operationNote = new OperationNote();
                    operationNote.setOperationPerson(user);
                    operationNote.setOperationContent("处理发货单");
                    operationNote.setOperationResult("处理成功{取消发货}");
                    operationNote.setOperationTime(new Date());
                    verfyNoteDao.save(operationNote);
                });
            }catch (Exception e){
                taskExecutor.execute(() -> {
                    OperationNote operationNote = new OperationNote();
                    operationNote.setOperationPerson(user);
                    operationNote.setOperationContent("处理发货单");
                    operationNote.setOperationResult("处理失败{取消发货}");
                    operationNote.setOperationTime(new Date());
                    verfyNoteDao.save(operationNote);
                });
            }
            return new Result<>(200, "取消成功！", deliverOrderId);
        }else {
            return new Result<>(500, "你已经操作过了！", null);
        }
    }
}
