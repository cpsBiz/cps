package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CategoryEntity;
import com.cps.cpsApi.entity.CpsMemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    CategoryEntity save(CpsMemberEntity entity);

    @Transactional
    void deleteByCategory(String category);
}
