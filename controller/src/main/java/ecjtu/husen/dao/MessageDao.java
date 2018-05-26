package ecjtu.husen.dao;

/**
 * @author 11785
 */
public interface MessageDao {
    Integer waitVerfyInOrderNumber();

    Integer waitVerfyOutOrderNumber();

    Integer waitDoDeliverOrderNumber();

    Integer warnStockNumber();
}
