package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsAgencyEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsAgencyRepository extends JpaRepository<CpsAgencyEntity, String> {
    CpsAgencyEntity save(CpsAgencyEntity entity);
}
