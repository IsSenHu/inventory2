package com.husen.dao;

import com.husen.model.LogisticsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticsRecordDao extends JpaRepository<LogisticsRecord, Integer> {
}
