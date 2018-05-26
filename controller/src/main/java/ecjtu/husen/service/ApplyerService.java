package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Sport;
import ecjtu.husen.util.Page;

import java.util.List;

public interface ApplyerService {
    boolean addApplyer(Applyer applyer);

    Page<Applyer> pageApplyer(Integer currentPage);

    Applyer findApplyerById(Integer applyerId);

    boolean updateApplyer(Applyer applyer);

    void deleteApplyerById(Integer applyerId);

    List<Sport> findAllSport();

    Page<Applyer> findApplyer(Applyer applyer, Integer currentPage);
}
