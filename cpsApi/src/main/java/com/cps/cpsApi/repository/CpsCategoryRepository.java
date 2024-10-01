package com.cps.cpsApi.repository;

import com.cps.cpsApi.dto.CpsCategoryDto;
import com.cps.cpsApi.entity.CpsCategoryEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsCategoryRepository extends JpaRepository<CpsCategoryEntity, String> {
    @Transactional
    void deleteByCategory(String category);

    CpsCategoryEntity findByCategoryName(String categoryName);

    @Query("SELECT new com.cps.cpsApi.dto.CpsCategoryDto(c.category, c.categoryName, c.categoryRank, "
            + "SUM(CASE WHEN cam.campaignStart <= :currentDate AND cam.campaignEnd >= :currentDate THEN 1 ELSE 0 END)) "
            + "FROM CpsCategoryEntity c LEFT JOIN CpsCampaignEntity cam ON c.category = cam.category "
            + "GROUP BY c.category, c.categoryName, c.categoryRank ")
    List<CpsCategoryDto> findCategoryCampaignCounts(@Param("currentDate") int currentDate);
}
