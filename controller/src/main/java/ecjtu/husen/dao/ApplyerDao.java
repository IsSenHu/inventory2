package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Applyer;

import java.util.List;

public interface ApplyerDao {
    void addApplyer(Applyer applyer);

    List<Applyer> findAll();

    int findTotal();

    List<Applyer> page(Integer currentPage, int pageSize);

    Applyer findApplyerById(Integer applyerId);

    Applyer findApplyerByName(String applyerName);

    void saveApplyer(Applyer applyer);

    void deleteApplyerById(Integer applyerId);

    List<Applyer> pageFind(Integer currentPage, int pageSize, Applyer applyer);

    int findTotalWhen(Applyer applyer);
}
