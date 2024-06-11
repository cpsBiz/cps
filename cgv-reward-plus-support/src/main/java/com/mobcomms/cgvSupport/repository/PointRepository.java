package com.mobcomms.cgvSupport.repository;

import com.mobcomms.cgvSupport.entity.PointEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface PointRepository extends JpaRepository<PointEntity, String> {
    @Query(value =
            "SELECT A.reg, COUNT(A.cnt) AS cnt " +
                    "FROM (SELECT DATE_FORMAT(reg_date, '%Y%m%d') AS reg, member_pk, COUNT(*) AS cnt " +
                    "      FROM point " +
                    "      WHERE reg_date > :startDate AND reg_date < :endDate " +
                    "      GROUP BY DATE_FORMAT(reg_date, '%Y%m%d'), member_pk) AS A " +
                    "GROUP BY A.reg", nativeQuery = true)
    List<Map<String, Object>> findPointUserCount(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
