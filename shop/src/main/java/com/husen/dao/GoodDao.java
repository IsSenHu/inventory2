package com.husen.dao;

import com.husen.model.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GoodDao extends JpaRepository<Good, Integer> {
    Page<Good> findAllByGoodNameAndStoresStoresId(String goodName, Integer storesId, Pageable pageable);
}
