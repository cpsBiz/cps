package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsAffilateCampaignEntity;
import com.cps.cpsApi.entity.CpsCampaignEntity;
import com.cps.cpsApi.entity.CpsViewEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsAffliateCampaignRepository extends JpaRepository<CpsAffilateCampaignEntity, String> {
    CpsAffilateCampaignEntity save(CpsAffilateCampaignEntity entity);
}
