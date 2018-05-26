package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.SportItem;

import java.util.List;

/**
 * @author 11785
 */
public interface SportItemDao {
    SportItem findByName(String sportItemName);

    void addSportItem(SportItem sportItem);

    int findTotal();

    List<SportItem> page(Integer currentPage, int pageSize);

    void updateSportItem(SportItem sportItem);

    void deleteSportItem(Integer sportItemId);

    int findTotalWhen(SportItem sportItem);

    List<SportItem> pageFind(Integer currentPage, int pageSize, SportItem sportItem);

    SportItem findById(Integer sportItemId);

    List<SportItem> findAll();
}
