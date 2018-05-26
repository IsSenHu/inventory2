package com.husen.dao;

import com.husen.model.LogisticsCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisCompanyDao extends JpaRepository<LogisticsCompany, Integer> {
}
