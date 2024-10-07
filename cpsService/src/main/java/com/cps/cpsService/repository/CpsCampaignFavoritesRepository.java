package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsCampaignFavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CpsCampaignFavoritesRepository extends JpaRepository<CpsCampaignFavoritesEntity, String> {
    CpsCampaignFavoritesEntity save(CpsCampaignFavoritesEntity cpsCampaignFavoritesEntity);
}
