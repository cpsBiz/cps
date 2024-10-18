package com.cps.cpsService.repository;

import com.cps.cpsService.dto.CpsGiftHistoryDto;
import com.cps.cpsService.entity.CpsGiftHistoryEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@Transactional
public interface CpsGiftHistoryRepository extends JpaRepository<CpsGiftHistoryEntity, String> {

    @Query("SELECT new com.cps.cpsService.dto.CpsGiftHistoryDto(A.productId, A.barcode, A.pinNo, A.awardDay, A.validDay, A.giftYn, B.brandIcon, B.brandName, B.productImageS, B.productImageL, B.productName, B.content) " +
            " FROM CpsGiftHistoryEntity A JOIN CpsGiftEntity B ON B.brandId = A.brandId AND B.productId = A.productId " +
            " WHERE A.userId = :userId AND A.merchantId = :merchantId AND affliateId = :affliateId AND A.awardYm =:awardYm AND A.giftYn IN :giftYn")
    List<CpsGiftHistoryDto> findGiftInfo(@Param("userId")String userId, @Param("merchantId")String merchantId, @Param("affliateId")String affliateId, @Param("awardYm")int awardYm, @Param("giftYn")List<String> giftYn);


    @Modifying
    @Query("UPDATE CpsGiftHistoryEntity C SET C.giftYn = 'V' WHERE C.validDay <= :validDay AND C.giftYn = 'N' ")
    int giftiConEnd(@Param("awardDay") int validDay);
}
