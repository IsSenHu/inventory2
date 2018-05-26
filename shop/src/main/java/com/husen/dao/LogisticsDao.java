package com.husen.dao;

import com.husen.model.Logistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticsDao extends JpaRepository<Logistics, Integer> {
}
