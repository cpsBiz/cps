package com.cps.cpsService.repository;

import com.cps.cpsService.dto.UnitDto;
import com.cps.cpsService.dto.UnitListDto;
import com.cps.cpsService.entity.CpsRewardUnitEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsRewardUnitRepository extends JpaRepository<CpsRewardUnitEntity, String> {

    CpsRewardUnitEntity findByClickNumAndStatus(int clickNum, int status);

    @Modifying
    @Transactional
    @Query("UPDATE CpsRewardUnitEntity u SET u.status = 200 WHERE u.regDay <= :regDay AND u.status = 100 ")
    void updateByRegDay(@Param("regDay") int regDay);

    @Query("SELECT new com.cps.cpsService.dto.UnitListDto(C.rewardUnitNum, C.cnt, C.stockCnt, C.totalPrice, C.productName, C.rewardCnt, C.status, C.regDay) FROM CpsRewardUnitEntity C WHERE C.userId = :userId AND C.merchantId =  :merchantId AND C.affliateId = :affliateId AND (:regYm IS NULL OR :regYm = 0 OR C.regYm = :regYm) AND C.status IN :status ")
    List<UnitListDto> findByUserIdAndStatus(@Param("userId") String userId, @Param("merchantId") String merchantId, @Param("affliateId") String affliateId, @Param("regYm") int regYm, @Param("status") List<Integer> status);

    @Query("SELECT new com.cps.cpsService.dto.UnitListDto(C.rewardUnitNum, C.cnt, C.stockCnt, C.totalPrice, C.productName, C.rewardCnt, C.status, C.regDay) FROM CpsRewardUnitEntity C WHERE C.userId = :userId AND C.merchantId =  :merchantId AND C.affliateId = :affliateId AND C.status = 200 ORDER BY C.regDate ASC")
    List<UnitListDto> findByUserIdAndStatusOrderByRegDate(@Param("userId") String userId, @Param("merchantId") String merchantId, @Param("affliateId") String affliateId);

    @Query("SELECT new com.cps.cpsService.dto.UnitDto(sum(C.cnt), sum(C.stockCnt)) FROM CpsRewardUnitEntity C WHERE C.userId = :userId AND C.merchantId = :merchantId AND C.affliateId = :affliateId AND C.status = 200")
    UnitDto findByUserIdAndAffliateId(@Param("userId") String userId, @Param("merchantId") String merchantId, @Param("affliateId") String affliateId);

    @Modifying
    @Transactional
    @Query("UPDATE CpsRewardUnitEntity u SET u.status = 210, u.stockCnt = u.cnt WHERE u.rewardUnitNum = :rewardUnitNum AND u.status = 200 ")
    void updateByRewardUnitNum210(@Param("rewardUnitNum") int rewardUnitNum);

    @Modifying
    @Transactional
    @Query("UPDATE CpsRewardUnitEntity u SET u.stockCnt = u.stockCnt + :giftCnt WHERE u.rewardUnitNum = :rewardUnitNum AND u.status = 200 ")
    void updateByRewardUnitNum200(@Param("rewardUnitNum") int rewardUnitNum, @Param("giftCnt") long giftCnt);
}
