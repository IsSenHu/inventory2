package com.husen.dao;

import com.husen.model.Stores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoresDao extends JpaRepository<Stores, Integer> {

    Stores findByBossBossId(Integer bossId);
}
