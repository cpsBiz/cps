package com.cps.common.repository;

import com.cps.common.entity.CpsAgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsAgencyRepository extends JpaRepository<CpsAgencyEntity, String> {
    CpsAgencyEntity save(CpsAgencyEntity entity);
}
