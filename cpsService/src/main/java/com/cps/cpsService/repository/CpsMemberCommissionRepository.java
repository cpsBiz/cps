package com.cps.cpsService.repository;

import com.cps.cpsService.dto.CpsMemberCommissionListDto;
import com.cps.cpsService.entity.CpsRewardEntity;
import com.cps.cpsService.dto.CpsMemberCommissionDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsMemberCommissionRepository extends JpaRepository<CpsRewardEntity, String> {

    @Query(value = "SELECT new com.cps.cpsService.dto.CpsMemberCommissionListDto ( A.userId, A.regDay, A.regYm, B.campaignName, A.productName, A.productPrice, " +
            " A.userCommission, A.productCnt, A.merchantId, A.status, B.commissionPaymentStandard ) " +
            " FROM CpsRewardEntity A " +
            " JOIN CpsCampaignEntity B ON B.campaignNum = A.campaignNum " +
            " WHERE A.userId = :userId " +
            " AND A.affliateId = :affliateId " +
            " AND A.merchantId != 'coupang' " +
            " AND A.status IN :status " +
            " AND A.regYm = :regYm ORDER BY A.regDay DESC, A.campaignNum DESC, A.rewardNum DESC" )
    List<CpsMemberCommissionListDto> findRewardsByUserIdAndRegYm(@Param("userId") String userId, @Param("regYm") Integer regYm, @Param("affliateId") String affliateId, @Param("status") List<Integer> status);

    @Query(value = "SELECT new com.cps.cpsService.dto.CpsMemberCommissionDto ( " +
            "COALESCE(SUM(CASE WHEN A.status = 210 THEN A.userCommission ELSE 0 END), 0), " +
            "COALESCE(SUM(CASE WHEN A.status = 100 THEN A.userCommission ELSE 0 END), 0)) " +
            "FROM CpsRewardEntity A " +
            "WHERE A.userId = :userId " +
            "  AND A.merchantId != 'coupang' " +
            "  AND A.affliateId = :affliateId ")
    CpsMemberCommissionDto findRewardsByUserId(@Param("userId") String userId, @Param("affliateId") String affliateId);
}
