package com.cps.common.repository;

import com.cps.common.entity.CpsCampaignCategoryEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface CpsCampaignCategoryRepository extends JpaRepository<CpsCampaignCategoryEntity, String> {
    @Modifying
    @Query("UPDATE CpsCampaignCategoryEntity C SET C.denyYn = 'N' WHERE C.campaignNum = :campaign_num AND C.category IN :categories")
    int updateDenyYn(@Param("campaign_num") int campaign_num, @Param("categories") List<String> categories);

    List<CpsCampaignCategoryEntity> findByCampaignNum(int campaignNum);
}
