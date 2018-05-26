package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Material;

import java.util.List;

public interface MaterialDao {
    Material findByName(String materialName);

    void addMaterial(Material material);

    int findTotal();

    List<Material> page(Integer currentPage, int pageSize);

    Material findById(Integer materialId);

    void updateMaterial(Material material);

    void deleteMaterial(Integer materialId);

    int findTotalWhen(Material material);

    List<Material> pageFind(Integer currentPage, int pageSize, Material material);

    List<Material> findAll();
}
