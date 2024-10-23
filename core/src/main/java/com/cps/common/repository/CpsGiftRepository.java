package com.cps.common.repository;

import com.cps.common.dto.CpsGiftProductDto;
import com.cps.common.entity.CpsGiftEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@Transactional
public interface CpsGiftRepository extends JpaRepository<CpsGiftEntity, String> {
    CpsGiftEntity findByBrandIdAndProductId(String brandId, String productId);

    @Query("SELECT new com.cps.common.dto.CpsGiftProductDto(A.productImageS, A.productImageL, A.brandName, A.productName, A.productId ) " +
            " FROM CpsGiftEntity A JOIN CpsGiftProductEntity B ON B.brandId = A.brandId AND B.productId = A.productId AND B.affliateId = :affliateId AND B.merchantId = :merchantId " +
            " WHERE A.brandId = :brandId ")
    List<CpsGiftProductDto> findByBrandIdAndProductId(@Param("brandId")String brandId, @Param("affliateId")String affliateId, @Param("merchantId")String merchantId);
}
