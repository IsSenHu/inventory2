package com.husen.dao;

import com.husen.model.Boss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BossDao extends JpaRepository<Boss, Integer> {
    Boss findByUsername(String username);
    Boss findByPhone(String phone);

}
