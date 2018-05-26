package com.husen.dao;

import com.husen.model.ShopPromotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopPromotionsDao extends JpaRepository<ShopPromotions, Integer> {
    List<ShopPromotions> findAllByGood_GoodId(Integer goodId);
}
