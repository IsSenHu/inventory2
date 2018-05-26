package com.husen.dao;

import com.husen.model.BalanceDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceDetailDao extends JpaRepository<BalanceDetail, Integer> {
    List<BalanceDetail> findAllByUser_UserId(Integer userId, Sort sort);
}
