package com.mobcomms.shinhan.repository;

import com.mobcomms.shinhan.entity.PointBannerSettingEntity;
import com.mobcomms.shinhan.entity.PointEntity;
import com.mobcomms.shinhan.entity.UserInfoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 *
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
public interface PointRepository extends JpaRepository<PointEntity, Long> {
    List<PointEntity> findFirstByUserKeyAndCodeAndStatsDttmOrderByRegDateDesc(String userKey, String code, int statsDttm);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ckd_api_point " +
            "SET code = :code, " +
            "    shinhan_code = :shinhan_code, " +
            "    transaction_id = uuid(), " +
            "    mod_date = NOW() " +
            "WHERE code =  '0' AND shinhan_code = 'Fail' AND transaction_id = '0' " +
            "  AND stats_dttm = :stats_dttm AND DATE_SUB(NOW(), INTERVAL 15 SECOND)  > reg_date",
            nativeQuery = true)
    void updateCodeAndModDateAndUniqueInsertByStatusDttm(String stats_dttm, String code, String shinhan_code);
}


