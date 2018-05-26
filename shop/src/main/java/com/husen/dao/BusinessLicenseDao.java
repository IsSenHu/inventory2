package com.husen.dao;

import com.husen.model.BusinessLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessLicenseDao extends JpaRepository<BusinessLicense, Integer> {
}
