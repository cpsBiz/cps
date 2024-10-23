package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsViewEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsViewRepository extends JpaRepository<CpsViewEntity, String> {
    @Query(value = "SELECT DATE_FORMAT(NOW(), '%Y%m%d') AS REG_DAY, DATE_FORMAT(NOW(), '%H') AS REG_HOUR," +
            " A.CAMPAIGN_NUM, A.CAMPAIGN_NAME," +
            " A.ADMIN_ID, A.MERCHANT_ID, A.CLICK_URL, A.MOBILE_CLICK_URL, A.ICON, A.LOGO, B.TYPE, B.MEMBER_NAME, " +
            " :affliateId AS AFFLIATE_ID, :zoneId AS ZONE_ID," +
            " :site AS SITE, :userId AS USER_ID, :adId AS ADID, :os AS OS, " +
            " IFNULL((SELECT 'FAVORITE' FROM CPS_CAMPAIGN_FAVORITES C WHERE C.CAMPAIGN_NUM = A.CAMPAIGN_NUM AND C.AFFLIATE_ID = :affliateId AND C.USER_ID = :userId),'NON_FAVORITE') AS FAVORITE, " +
            " COALESCE(A.COMMISSION_MOBILE, 2), COALESCE(A.COMMISSION_PC, 2), COALESCE(C.MEMBER_COMMISSION_SHARE, 70), COALESCE(C.USER_COMMISSION_SHARE, 30), D.CAMPAIGN_RANK  " +
            " FROM CPS_CAMPAIGN A " +
            " JOIN CPS_MEMBER B " +
            "  ON B.MEMBER_ID = A.MERCHANT_ID " +
            " LEFT JOIN CPS_CAMPAIGN_COMMISSION C " +
            "  ON C.CAMPAIGN_NUM = A.CAMPAIGN_NUM AND C.AFFLIATE_ID = :affliateId " +
            " JOIN CPS_CAMPAIGN_RANK D ON D.CAMPAIGN_NUM = A.CAMPAIGN_NUM AND D.CATEGORY = :category AND D.AFFLIATE_ID = :affliateId " +
            " WHERE A.CAMPAIGN_START <= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 0 DAY), '%Y%m%d') " +
            " AND A.CAMPAIGN_END >= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 0 DAY), '%Y%m%d') " +
            " AND A.REWARD_YN = 'Y' " +
            " AND A.CAMPAIGN_STATUS = 'Y' ORDER BY D.CAMPAIGN_RANK ASC ",
            nativeQuery = true)
    List<Object[]> findActiveCampaigns(@Param("affliateId") String affliateId, @Param("zoneId") String zoneId, @Param("site") String site, @Param("userId") String userId, @Param("adId") String adId, @Param("os") String os, @Param("category") String category);

    @Query(value = "SELECT DATE_FORMAT(NOW(), '%Y%m%d') AS REG_DAY, DATE_FORMAT(NOW(), '%H') AS REG_HOUR," +
            " A.CAMPAIGN_NUM, A.CAMPAIGN_NAME," +
            " A.ADMIN_ID, A.MERCHANT_ID, A.CLICK_URL, A.MOBILE_CLICK_URL, A.ICON, A.LOGO, B.TYPE, B.MEMBER_NAME, " +
            " :affliateId AS AFFLIATE_ID, :zoneId AS ZONE_ID," +
            " :site AS SITE, :userId AS USER_ID, :adId AS ADID, :os AS OS, 'FAVORITE' AS FAVORITE, " +
            " COALESCE(A.COMMISSION_MOBILE, 2), COALESCE(A.COMMISSION_PC, 2), COALESCE(D.MEMBER_COMMISSION_SHARE, 70), COALESCE(D.USER_COMMISSION_SHARE, 30), E.CAMPAIGN_RANK  " +
            " FROM CPS_CAMPAIGN A " +
            " JOIN CPS_MEMBER B " +
            "   ON B.MEMBER_ID = A.MERCHANT_ID " +
            " JOIN CPS_CAMPAIGN_FAVORITES C " +
            "   ON C.CAMPAIGN_NUM = A.CAMPAIGN_NUM AND C.AFFLIATE_ID = :affliateId AND C.USER_ID = :userId " +
            " LEFT JOIN CPS_CAMPAIGN_COMMISSION D " +
            "   ON D.CAMPAIGN_NUM = A.CAMPAIGN_NUM AND D.AFFLIATE_ID = :affliateId " +
            "  JOIN (SELECT E.CAMPAIGN_NUM, MIN(E.CAMPAIGN_RANK) AS CAMPAIGN_RANK FROM CPS_CAMPAIGN_RANK E WHERE E.AFFLIATE_ID = :affliateId GROUP BY E.CAMPAIGN_NUM) E ON E.CAMPAIGN_NUM = A.CAMPAIGN_NUM " +
            " WHERE A.CAMPAIGN_START <= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 0 DAY), '%Y%m%d') " +
            " AND A.CAMPAIGN_END >= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 0 DAY), '%Y%m%d') " +
            " AND A.REWARD_YN = 'Y' " +
            " AND A.CAMPAIGN_STATUS = 'Y' " +
            " AND C.USER_ID = :userId ORDER BY E.CAMPAIGN_RANK ASC ",
            nativeQuery = true)
    List<Object[]> findActiveFavoritesCampaigns(@Param("affliateId") String affliateId, @Param("zoneId") String zoneId, @Param("site") String site, @Param("userId") String userId, @Param("adId") String adId, @Param("os") String os);
}
