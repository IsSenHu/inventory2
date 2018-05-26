package com.husen.dao;

import com.husen.model.Refund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundDao extends JpaRepository<Refund, Integer> {
    Page<Refund> findAllByStoresIdAndUserIdAndStatu(Integer storesId, Integer userId, Integer statu, Pageable pageable);
    Page<Refund> findAllByStoresIdAndStatu(Integer storesId, Integer statu, Pageable pageable);
    List<Refund> findAllByOrderId(Integer orderId);
}
