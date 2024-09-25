package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsCampaignCommissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsCampaignCommissionRepository extends JpaRepository<CpsCampaignCommissionEntity, String> {
    CpsCampaignCommissionEntity save(CpsCampaignCommissionEntity entity);
}
