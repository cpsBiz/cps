package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsGiftEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@Transactional
public interface CpsGiftRepository extends JpaRepository<CpsGiftEntity, String> {
    CpsGiftEntity findByBrandIdAndProductId(String brandId, String productId);

    @Query("SELECT g FROM CpsGiftEntity g JOIN CpsGiftProductEntity p " +
            "ON g.brandId = p.brandId AND g.productId = p.productId " +
            "WHERE g.brandId = :brandId ")
    List<CpsGiftEntity> findByBrandIdAndProductId(@Param("brandId") String brandId);
}
