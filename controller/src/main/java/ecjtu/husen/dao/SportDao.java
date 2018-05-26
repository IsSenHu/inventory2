package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Sport;

import java.util.List;

/**
 * @author 11785
 */
public interface SportDao {
    Sport findSportbyName(String sportName);

    void addSport(Sport sport);

    int findTotal();

    List<Sport> page(Integer currentPage, int pageSize);

    Sport findSportById(Integer sportId);

    void updateSport(Sport sport);

    void deleteSportById(Integer sportId);

    List<Sport> pageFind(Integer currentPage, int pageSize, Sport sport);

    List<Sport> findAllSport();

    int findTotalWhen(Sport sport);
}
