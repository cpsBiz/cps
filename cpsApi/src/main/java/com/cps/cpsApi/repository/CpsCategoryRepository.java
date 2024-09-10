package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsCategoryEntity;
import com.cps.cpsApi.entity.CpsMemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsCategoryRepository extends JpaRepository<CpsCategoryEntity, String> {
    CpsCategoryEntity save(CpsMemberEntity entity);

    @Transactional
    void deleteByCategory(String category);
}
