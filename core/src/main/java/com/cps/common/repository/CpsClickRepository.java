package com.cps.common.repository;

import com.cps.common.dto.CommissionDto;
import com.cps.common.entity.CpsClickEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsClickRepository extends JpaRepository<CpsClickEntity, String> {
    CpsClickEntity save(CpsClickEntity entity);

    @Modifying
    @Transactional
    @Query("UPDATE CpsClickEntity C SET C.rewardYn = :rewardYn WHERE C.clickNum IN :clickNumList")
    int updateRewardYnByClickNumList(@Param("rewardYn") String rewardYn, @Param("clickNumList") List<Integer> clickNumList);

    @Query("SELECT new com.cps.common.dto.CommissionDto(a, COALESCE(c.memberCommissionShare, 70), COALESCE(c.userCommissionShare, 30), COALESCE(c.pointRate, 1.00), b.memberName) " +
            "FROM CpsClickEntity a " +
            "JOIN CpsMemberEntity b ON b.memberId = a.merchantId " +
            "LEFT JOIN CpsCampaignCommissionEntity c ON c.campaignNum = a.campaignNum AND c.affliateId = a.affliateId " +
            "WHERE a.clickNum = :clickNum")
    CommissionDto findClickCommission(@Param("clickNum") int clickNum);


}
