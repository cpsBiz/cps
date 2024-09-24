package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsClickEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsClickRepository extends JpaRepository<CpsClickEntity, String> {
    CpsClickEntity save(CpsClickEntity entity);

    CpsClickEntity findByClickNum(int clickNum);
}
