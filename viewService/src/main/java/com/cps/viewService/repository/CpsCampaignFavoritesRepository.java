package com.cps.viewService.repository;

import com.cps.viewService.entity.CpsCampaignFavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CpsCampaignFavoritesRepository extends JpaRepository<CpsCampaignFavoritesEntity, String> {
    CpsCampaignFavoritesEntity save(CpsCampaignFavoritesEntity cpsCampaignFavoritesEntity);
}
