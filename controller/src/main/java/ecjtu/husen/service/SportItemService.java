package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.SportItem;
import ecjtu.husen.util.Page;

/**
 * @author 11785
 */
public interface SportItemService {
    boolean addSportItem(SportItem sportItem);

    Page<SportItem> pageSportItem(Integer currentPage);

    SportItem findSportItemById(Integer sportItemId);

    boolean updateSportItem(SportItem sportItem);

    void deleteSportItemById(Integer sportItemId);

    Page<SportItem> findSportItem(SportItem sportItem, Integer currentPage);
}
