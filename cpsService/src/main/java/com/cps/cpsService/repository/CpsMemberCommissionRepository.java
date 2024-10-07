package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsRewardEntity;
import com.cps.cpsService.dto.CpsMemberCommissionDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CpsMemberCommissionRepository extends JpaRepository<CpsRewardEntity, String> {
    @Query(value = "SELECT A.USER_ID, A.REG_DAY, A.REG_YM, B.CAMPAIGN_NAME, A.PRODUCT_NAME, A.PRODUCT_PRICE, A.USER_COMMISSION, A.PRODUCT_CNT, A.MEMBER_ID, A.STATUS, B.COMMISSION_PAYMENT_STANDARD " +
            "FROM CPS_REWARD A " +
            "JOIN CPS_CAMPAIGN B ON B.CAMPAIGN_NUM = A.CAMPAIGN_NUM " +
            "WHERE A.USER_ID = :userId " +
            "AND A.AFFLIATE_ID = :affliateId " +
            "AND A.STATUS IN :status " +
            "AND A.REG_YM = :regYm",
            nativeQuery = true)
    List<Object[]> findRewardsByUserIdAndRegYm(@Param("userId") String userId, @Param("regYm") Integer regYm, @Param("affliateId") String affliateId, @Param("status") List<Integer> status);

    @Query(value = "SELECT new com.cps.cpsService.dto.CpsMemberCommissionDto ( " +
            "COALESCE(SUM(CASE WHEN A.status = 310 THEN A.userCommission ELSE 0 END), 0), " +
            "COALESCE(SUM(CASE WHEN A.status IN (100, 200) THEN A.userCommission ELSE 0 END), 0)) " +
            "FROM CpsRewardEntity A " +
            "WHERE A.userId = :userId " +
            "AND A.affliateId = :affliateId")
    CpsMemberCommissionDto findRewardsByUserId(@Param("userId") String userId, @Param("affliateId") String affliateId);
}
