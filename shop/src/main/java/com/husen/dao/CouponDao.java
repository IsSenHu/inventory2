package com.husen.dao;

import com.husen.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponDao extends JpaRepository<Coupon, Integer> {
    List<Coupon> findAllByGood_GoodId(Integer goodId);
}
