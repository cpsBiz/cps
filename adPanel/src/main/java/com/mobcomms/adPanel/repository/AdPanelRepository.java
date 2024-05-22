package com.mobcomms.adPanel.repository;

import com.mobcomms.adPanel.entity.AdPanelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdPanelRepository extends JpaRepository<AdPanelEntity, String> {
    List<AdPanelEntity> findAll();

    AdPanelEntity save(AdPanelEntity entity);

    List<AdPanelEntity> findAllByClientCodeAndProductCodeAndZoneIdAndOsType(String clientCode, String productCode, String zoneId, String osType);

    void delete(AdPanelEntity entity);
}
