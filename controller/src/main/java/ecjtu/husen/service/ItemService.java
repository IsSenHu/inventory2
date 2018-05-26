package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.*;
import ecjtu.husen.util.Page;

import java.util.List;

/**
 * @author 11785
 */
public interface ItemService {
    List<Applyer> findAllAppler();

    List<Brand> findAllBrand();

    List<Material> findAllMaterial();

    List<Specification> findAllSpecification();

    List<SportItem> findAllSportItem();

    boolean addItem(Item item);

    Item findItemById(Integer itemId);

    void updateItem(Item item);

    Item findByName(String itemName);

    Page<Item> pageItem(Integer currentPage);

    Page<Item> findItem(Item item, Integer currentPage);

    void delete(Integer itemId);

    void uploadPic(String picPath, Item item);
}
