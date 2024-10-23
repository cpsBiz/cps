package com.cps.common.repository;

import com.cps.common.entity.CpsCampaignCommissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsCampaignCommissionRepository extends JpaRepository<CpsCampaignCommissionEntity, String> {
    CpsCampaignCommissionEntity save(CpsCampaignCommissionEntity entity);
}
