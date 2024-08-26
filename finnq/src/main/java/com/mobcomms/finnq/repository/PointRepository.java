package com.mobcomms.finnq.repository;

import com.mobcomms.finnq.entity.PointEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointRepository extends JpaRepository<PointEntity, String> {
    List<PointEntity> findAllByUserIdAndRegDateNum(String userId, String RegDateNum);

    List<PointEntity> findAllByUserIdAndRegDateNumAndCode(String userId, String RegDateNum, String code);

    List<PointEntity> findAllByUserIdAndRegDateNumAndCodeAndType(String userId, String RegDateNum, String code, String type);

    PointEntity save(PointEntity entity);

    @Transactional
    @Modifying
    @Query(value = "UPDATE api_point " +
            "SET code = :code, " +
            "    unique_insert = uuid(), " +
            "    mod_date = NOW() " +
            "WHERE code =  '0' AND unique_insert = '0' " +
            "  AND reg_date_num = :regDateNum AND DATE_SUB(NOW(), INTERVAL 15 SECOND)  > reg_date",
            nativeQuery = true)
    void updateCodeAndModDateAndUniqueInsertByStatusDttm(String regDateNum, String code);
}
