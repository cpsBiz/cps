package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsViewEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CpsViewScheduleRepository extends JpaRepository<CpsViewEntity, String> {
    @Query(value = "INSERT INTO SUMMARY_HOUR (REG_DAY, REG_YM, REG_HOUR, CAMPAIGN_NUM, MEMBER_ID, AGENCY_ID, AFFLIATE_ID, ZONE_ID, SITE, OS, CNT, TYPE) " +
            "SELECT REG_DAY, SUBSTR(REG_DAY,1,6) AS REG_YM,  REG_HOUR, " +
            "       CAMPAIGN_NUM, MEMBER_ID, AGENCY_ID, AFFLIATE_ID, ZONE_ID, SITE, OS, COUNT(1) AS CNT, TYPE  " +
            "  FROM CPS_VIEW " +
            " WHERE REG_DAY = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :minutes MINUTE), '%Y%m%d') " +
            "   AND REG_HOUR = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :minutes MINUTE), '%H') " +
            " GROUP BY CAMPAIGN_NUM, MEMBER_ID, AGENCY_ID, AFFLIATE_ID, ZONE_ID, SITE, OS " +
            "    ON DUPLICATE KEY UPDATE CNT = VALUES(CNT), MOD_DATE = NOW() ", nativeQuery = true)
    void insertSummaryViewHour(@Param("minutes") int minutes);


    @Query(value = "INSERT INTO SUMMARY_HOUR (REG_DAY, REG_YM, REG_HOUR, CAMPAIGN_NUM, MEMBER_ID, AGENCY_ID, AFFLIATE_ID, ZONE_ID, SITE, OS, CLICK_CNT, TYPE) " +
            "SELECT REG_DAY, SUBSTR(REG_DAY,1,6) AS REG_YM,  REG_HOUR, " +
            "       CAMPAIGN_NUM, MEMBER_ID, AGENCY_ID, AFFLIATE_ID, ZONE_ID, SITE, OS, COUNT(1) AS CLICK_CNT, TYPE  " +
            "  FROM CPS_CLICK " +
            " WHERE REG_DAY = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :minutes MINUTE), '%Y%m%d') " +
            "   AND REG_HOUR = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :minutes MINUTE), '%H') " +
            " GROUP BY CAMPAIGN_NUM, MEMBER_ID, AGENCY_ID, AFFLIATE_ID, ZONE_ID, SITE, OS " +
            "    ON DUPLICATE KEY UPDATE CLICK_CNT = VALUES(CLICK_CNT), MOD_DATE = NOW() ", nativeQuery = true)
    void insertSummaryClickHour(@Param("minutes") int minutes);


    @Query(value = "INSERT INTO SUMMARY_DAY (REG_DAY, REG_YM, CAMPAIGN_NUM, MEMBER_ID, AGENCY_ID, AFFLIATE_ID, ZONE_ID, SITE, OS, CLICK_CNT, TYPE) " +
            "SELECT A.REG_DAY, A.REG_YM, A.CAMPAIGN_NUM, A.MEMBER_ID, A.AGENCY_ID, A.AFFLIATE_ID, A.ZONE_ID, A.SITE, A.OS, SUM(A.CLICK_CNT) AS CLICK_CNT, " +
            "       IFNULL((SELECT C.TYPE FROM CPS_USER C WHERE C.MEMBER_ID = A.MEMBER_ID),'NON_TYPE') AS TYPE " +
            "  FROM SUMMARY_HOUR A" +
            " WHERE A.REG_DAY = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :minutes MINUTE), '%Y%m%d') " +
            " GROUP BY A.CAMPAIGN_NUM, A.MEMBER_ID, A.AGENCY_ID, A.AFFLIATE_ID, A.ZONE_ID, A.SITE, A.OS " +
            "    ON DUPLICATE KEY UPDATE CLICK_CNT = VALUES(CLICK_CNT), MOD_DATE = NOW() ",
            nativeQuery = true)
    void insertSummaryClickDay(@Param("minutes") int minutes);

    @Query(value = "INSERT INTO SUMMARY_DAY (REG_DAY, REG_YM, CAMPAIGN_NUM, MEMBER_ID, AGENCY_ID, AFFLIATE_ID, ZONE_ID, SITE, OS, CNT, CAMPAIGN_NAME, MEMBER_NAME, AGENCY_NAME, AFFLIATE_NAME, TYPE) " +
            "SELECT A.REG_DAY, A.REG_YM,  " +
            "       A.CAMPAIGN_NUM, A.MEMBER_ID, A.AGENCY_ID, A.AFFLIATE_ID, A.ZONE_ID, A.SITE, A.OS , SUM(A.CNT) AS CNT, B.CAMPAIGN_NAME, " +
            "       (SELECT C.COMPANY_NAME FROM CPS_USER C WHERE C.MEMBER_ID = A.MEMBER_ID AND C.TYPE = 'MEMBER') AS MEMBER_NAME, " +
            "       (SELECT C.COMPANY_NAME FROM CPS_USER C WHERE C.MEMBER_ID = A.AGENCY_ID AND C.TYPE = 'AGENCY') AS AGENCY_NAME, " +
            "       (SELECT C.COMPANY_NAME FROM CPS_USER C WHERE C.MEMBER_ID = A.AFFLIATE_ID AND C.TYPE = 'AFFLIATE') AS AFFLIATE_NAME, " +
            "       IFNULL((SELECT C.TYPE FROM CPS_USER C WHERE C.MEMBER_ID = A.MEMBER_ID),'NON_TYPE') AS TYPE " +
            "  FROM SUMMARY_HOUR A" +
            "  JOIN CPS.CPS_CAMPAIGN B ON A.CAMPAIGN_NUM = B.CAMPAIGN_NUM " +
            " WHERE A.REG_DAY = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :minutes MINUTE), '%Y%m%d') " +
            " GROUP BY A.CAMPAIGN_NUM, A.MEMBER_ID, A.AGENCY_ID, A.AFFLIATE_ID, A.ZONE_ID, A.SITE, A.OS " +
            "    ON DUPLICATE KEY UPDATE CNT = VALUES(CNT), MOD_DATE = NOW() ",
            nativeQuery = true)
    void insertSummaryViewDay(@Param("minutes") int minutes);
}
