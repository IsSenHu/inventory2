package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Item;

import java.util.List;

/**
 * @author 11785
 */
public interface ItemDao {
    Item findByName(String itemName);

    void addItem(Item item);

    Item findById(Integer itemId);

    void updateItem(Item item);

    void deleteItem(Integer itemId);

    int findTotal();

    List<Item> page(Integer currentPage, int pageSize);

    int findTotalWhen(Item item);

    List<Item> pageFind(Integer currentPage, int pageSize, Item item);

    void uploadPic(String picPath, Item item);

    List<Item> findIdAndName();

    List<Item> findIdAndNameBySportId(Integer sportId);

    void claItemStock(Integer itemId, Integer newItem);
}
