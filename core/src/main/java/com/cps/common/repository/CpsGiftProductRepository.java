package com.cps.common.repository;

import com.cps.common.entity.CpsGiftProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Transactional
public interface CpsGiftProductRepository extends JpaRepository<CpsGiftProductEntity, String> {
    List<CpsGiftProductEntity> findByBrandIdAndAffliateIdAndMerchantId(String brandId, String affliateId, String merchantId);
}
