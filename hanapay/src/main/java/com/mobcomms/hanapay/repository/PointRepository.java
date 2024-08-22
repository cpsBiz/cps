package com.mobcomms.hanapay.repository;

import com.mobcomms.hanapay.entity.PointEntity;
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
                      "    res = :res, " +
                      "    mod_date = NOW(), " +
                      "    unique_insert = uuid() " +
                    "WHERE point_id = :pointId " +
                    "  AND reg_date_num = :regDateNum",
            nativeQuery = true)
    void updateCodeAndModDateAndUniqueInsertByPointIdAndRegDateNum(int pointId, String regDateNum, String code, String res);

}
