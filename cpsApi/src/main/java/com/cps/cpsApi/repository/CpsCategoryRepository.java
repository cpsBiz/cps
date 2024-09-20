package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsCategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsCategoryRepository extends JpaRepository<CpsCategoryEntity, String> {
    @Transactional
    void deleteByCategory(String category);
}
