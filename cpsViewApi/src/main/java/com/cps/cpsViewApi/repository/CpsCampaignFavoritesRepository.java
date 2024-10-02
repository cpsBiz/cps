package com.cps.cpsViewApi.repository;

import com.cps.cpsViewApi.entity.CpsCampaignFavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CpsCampaignFavoritesRepository extends JpaRepository<CpsCampaignFavoritesEntity, String> {
    CpsCampaignFavoritesEntity save(CpsCampaignFavoritesEntity cpsCampaignFavoritesEntity);
}
