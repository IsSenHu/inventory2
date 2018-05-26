package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.Sport;
import ecjtu.husen.util.Page;

/**
 * @author 11785
 */
public interface SportService {
    boolean addSport(Sport sport);

    Page<Sport> pageStudent(Integer currentPage);

    Sport findSportById(Integer sportId);

    boolean updateSport(Sport sport);

    void deleteSportById(Integer sportId);

    Page<Sport> findSport(Sport sport, Integer currentPage);
}
