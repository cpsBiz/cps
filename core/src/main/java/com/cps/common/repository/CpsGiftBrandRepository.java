package com.cps.common.repository;

import com.cps.common.entity.CpsGiftBrandEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Transactional
public interface CpsGiftBrandRepository extends JpaRepository<CpsGiftBrandEntity, String> {
    List<CpsGiftBrandEntity> findByAffliateIdAndMerchantIdAndBrandTypeAndBrandYn(String affliateId, String merchantId, String brandType, String brandYn);
}
