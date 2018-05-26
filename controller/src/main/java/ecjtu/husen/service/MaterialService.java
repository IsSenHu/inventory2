package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.Material;
import ecjtu.husen.util.Page;

public interface MaterialService {
    boolean addMaterial(Material material);

    Page<Material> page(Integer currentPage);

    Material findMaterialById(Integer materialId);

    boolean updateMaterial(Material material);

    void deleteMaterial(Integer materialId);

    Page<Material> findMaterial(Material material, Integer currentPage);
}
