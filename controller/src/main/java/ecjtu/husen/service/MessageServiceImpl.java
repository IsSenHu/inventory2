package ecjtu.husen.service;

import ecjtu.husen.dao.MessageDao;
import ecjtu.husen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 11785
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public Result<Integer> waitVerfyInOrderNumber() {
        try {
            Integer number = messageDao.waitVerfyInOrderNumber();
            return new Result<>(200, "查询成功！", number);
        }catch (Exception e){
            return new Result<>(400, "查询失败", null);
        }
    }

    @Override
    public Result<Integer> waitVerfyOutOrderNumber() {
        try {
            Integer number = messageDao.waitVerfyOutOrderNumber();
            return new Result<>(200, "查询成功！", number);
        }catch (Exception e){
            return new Result<>(400, "查询失败", null);
        }
    }

    @Override
    public Result<Integer> waitDoDeliverOrderNumber() {
        try {
            Integer number = messageDao.waitDoDeliverOrderNumber();
            return new Result<>(200, "查询成功！", number);
        }catch (Exception e){
            return new Result<>(400, "查询失败", null);
        }
    }

    @Override
    public Result<Integer> warnStockNumber() {
        try {
            Integer number = messageDao.warnStockNumber();
            return new Result<>(200, "查询成功！", number);
        }catch (Exception e){
            return new Result<>(400, "查询失败", null);
        }
    }
}
