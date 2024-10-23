package com.cps.common.repository;

import com.cps.common.dto.CpsCampaignMerchantDto;
import com.cps.common.entity.CpsCampaignEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsCampaignRepository extends JpaRepository<CpsCampaignEntity, String> {
    CpsCampaignEntity save(CpsCampaignEntity entity);

    CpsCampaignEntity findByCampaignNum(int campaignNum);


    @Query("SELECT new com.cps.common.dto.CpsCampaignMerchantDto(MAX(a.merchantId), b.memberName) " +
            "FROM CpsCampaignEntity a " +
            "JOIN CpsMemberEntity b ON b.memberId = a.merchantId " +
            " group by b.memberName order by b.memberName asc")
    List<CpsCampaignMerchantDto> findByMerchant();



    @Transactional
    void deleteByCampaignNum(int campaignNum);

    @Modifying
    @Transactional
    @Query("UPDATE CpsCampaignEntity C SET C.category = :category WHERE C.campaignNum IN :campaignNum")
    int updateCategoryByCampaignNum(@Param("category") String category, @Param("campaignNum") Integer campaignNum);


}
