package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsCampaignEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CpsCampaignRepository extends JpaRepository<CpsCampaignEntity, String> {
    CpsCampaignEntity save(CpsCampaignEntity entity);

    CpsCampaignEntity findByCampaignNum(int campaignNum);

    @Transactional
    void deleteByCampaignNum(int campaignNum);

    @Modifying
    @Transactional
    @Query("UPDATE CpsCampaignEntity C SET C.category = :category WHERE C.campaignNum IN :campaignNum")
    int updateCategoryByCampaignNum(@Param("category") String category, @Param("campaignNum") Integer campaignNum);


}
