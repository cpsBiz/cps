package com.cps.cpsApi.repository;

import com.cps.cpsApi.dto.CommissionDto;
import com.cps.cpsApi.entity.CpsClickEntity;
import com.cps.cpsApi.entity.CpsCampaignCommissionEntity;
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

    @Query("SELECT new com.cps.cpsApi.dto.CommissionDto(a, COALESCE(b.memberCommissionShare, 0), COALESCE(b.userCommissionShare, 0)) " +
            "FROM CpsClickEntity a " +
            "LEFT JOIN CpsCampaignCommissionEntity b ON b.campaignNum = a.campaignNum " +
            "WHERE a.clickNum = :clickNum")
    CommissionDto findClickCommission(@Param("clickNum") int clickNum);


}
