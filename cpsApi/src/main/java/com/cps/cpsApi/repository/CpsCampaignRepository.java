package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsCampaignEntity;
import com.cps.cpsApi.entity.CpsMemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsCampaignRepository extends JpaRepository<CpsCampaignEntity, String> {
    CpsCampaignEntity save(CpsCampaignEntity entity);

    CpsCampaignEntity findByCampaignNum(int campaignNum);

    @Transactional
    void deleteByCampaignNum(int campaignNum);
}
