package com.cps.cpsViewApi.repository;

import com.cps.cpsViewApi.entity.CpsViewEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsViewRepository extends JpaRepository<CpsViewEntity, String> {
    @Query(value = "SELECT DATE_FORMAT(NOW(), '%Y%m%d') AS REG_DAY, DATE_FORMAT(NOW(), '%H') AS REG_HOUR," +
            "A.CAMPAIGN_NUM, A.CAMPAIGN_NAME," +
            "A.AGENCY_ID, A.MEMBER_ID, A.CLICK_URL, B.TYPE, " +
            ":affliateId AS AFFLIATE_ID, :zoneId AS ZONE_ID," +
            ":site AS SITE, :userId AS USER_ID, :adId AS ADID, :os AS OS, NOW() AS REG_DATE " +
            "FROM CPS_CAMPAIGN A " +
            "JOIN CPS_USER B " +
            "  ON B.MEMBER_ID = A.MEMBER_ID " +
            "WHERE A.CAMPAIGN_START <= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 0 DAY), '%Y%m%d') " +
            "AND A.CAMPAIGN_END >= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 0 DAY), '%Y%m%d') " +
            "AND A.REWARD_YN = 'Y' " +
            "AND A.CAMPAIGN_STATUS = 'Y'",
            nativeQuery = true)
    List<Object[]> findActiveCampaigns(@Param("affliateId") String affliateId, @Param("zoneId") String zoneId, @Param("site") String site, @Param("userId") String userId, @Param("adId") String adId, @Param("os") String os);
}
