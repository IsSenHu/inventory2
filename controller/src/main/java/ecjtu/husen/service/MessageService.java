package ecjtu.husen.service;

import ecjtu.husen.vo.Result;

/**
 * @author 11785
 */
public interface MessageService {
    Result<Integer> waitVerfyInOrderNumber();

    Result<Integer> waitVerfyOutOrderNumber();

    Result<Integer> waitDoDeliverOrderNumber();

    Result<Integer> warnStockNumber();
}
