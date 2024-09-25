package com.cps.agencyService.repository;

import com.cps.agencyService.dto.CommissionDto;
import com.cps.agencyService.entity.CpsClickEntity;
import com.cps.agencyService.entity.CpsCampaignCommissionEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsClickRepository extends JpaRepository<CpsClickEntity, String> {
    CpsClickEntity save(CpsClickEntity entity);

    CpsClickEntity findByClickNum(int clickNum);

    @Modifying
    @Transactional
    @Query("UPDATE CpsClickEntity C SET C.rewardYn = :rewardYn WHERE C.clickNum IN :clickNumList")
    int updateRewardYnByClickNumList(@Param("rewardYn") String rewardYn, @Param("clickNumList") List<Integer> clickNumList);

    @Query("SELECT new com.cps.agencyService.dto.CommissionDto(a, COALESCE(c.memberCommissionShare, 0), COALESCE(c.userCommissionShare, 0), b.memberName) " +
            "FROM CpsClickEntity a " +
            "JOIN CpsUserEntity b ON b.memberId = a.memberId " +
            "LEFT JOIN CpsCampaignCommissionEntity c ON c.campaignNum = a.campaignNum " +
            "WHERE a.clickNum = :clickNum")
    CommissionDto findClickCommission(@Param("clickNum") int clickNum);
}
