package com.cps.cpsService.repository;

import com.cps.cpsService.dto.CommissionDto;
import com.cps.cpsService.entity.CpsClickEntity;
import com.cps.cpsService.entity.CpsMemberEntity;
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

    @Query("SELECT new com.cps.cpsService.dto.CommissionDto(a, COALESCE(c.memberCommissionShare, 7), COALESCE(c.userCommissionShare, 3), b.memberName) " +
            "FROM CpsClickEntity a " +
            "JOIN CpsMemberEntity b ON b.memberId = a.memberId " +
            "LEFT JOIN CpsCampaignCommissionEntity c ON c.campaignNum = a.campaignNum " +
            "WHERE a.clickNum = :clickNum")
    CommissionDto findClickCommission(@Param("clickNum") int clickNum);


}
