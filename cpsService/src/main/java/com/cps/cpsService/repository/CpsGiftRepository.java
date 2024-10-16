package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsGiftEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface CpsGiftRepository extends JpaRepository<CpsGiftEntity, String> {
    CpsGiftEntity findByBrandIdAndProductId(String brandId, String productId);
}
