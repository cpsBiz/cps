package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsCampaignCommissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsCampaignCommissionRepository extends JpaRepository<CpsCampaignCommissionEntity, String> {
    CpsCampaignCommissionEntity save(CpsCampaignCommissionEntity entity);
}
