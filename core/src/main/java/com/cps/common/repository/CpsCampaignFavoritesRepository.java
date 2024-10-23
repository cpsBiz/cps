package com.cps.common.repository;

import com.cps.common.entity.CpsCampaignFavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CpsCampaignFavoritesRepository extends JpaRepository<CpsCampaignFavoritesEntity, String> {
    CpsCampaignFavoritesEntity save(CpsCampaignFavoritesEntity cpsCampaignFavoritesEntity);
}
