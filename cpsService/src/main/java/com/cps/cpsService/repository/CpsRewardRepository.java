package com.cps.cpsService.repository;

import com.cps.cpsService.dto.CpsRewardUnitDto;
import com.cps.cpsService.entity.CpsRewardEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CpsRewardRepository extends JpaRepository<CpsRewardEntity, String> {
    CpsRewardEntity save(CpsRewardEntity entity);

    @Query("SELECT e FROM CpsRewardEntity e WHERE e.clickNum = ?1 AND e.orderNo = ?2 AND e.productCode = ?3 AND e.status = 100")
    CpsRewardEntity findByClickNumAndOrderNoAndProductCodeExcludingStatus(int clickNum, String orderNo, String productCode);

    @Query("SELECT new com.cps.cpsService.dto.CpsRewardUnitDto(c.clickNum, c.userId, c.regDay, c.regYm, "
            + "MAX(CASE WHEN c.status = 100 THEN c.productName ELSE '' END), "
            + "FLOOR(COALESCE(SUM(CASE WHEN c.status = 100 THEN c.productPrice ELSE 0 END), 0) / 10000), "
            + "COALESCE(SUM(CASE WHEN c.status = 100 THEN c.productPrice ELSE 0 END), 0), "
            + "COALESCE(SUM(CASE WHEN c.status = 100 THEN 1 ELSE 0 END), 0), "
            + "c.merchantId, c.affliateId, COALESCE(d.cnt, 0), COALESCE(d.totalPrice, 0), COALESCE(d.rewardUnitNum, 0) ) "
            + "FROM CpsRewardEntity c "
            + "LEFT JOIN CpsRewardUnitEntity d ON d.clickNum = c.clickNum AND d.status = 100 "
            + "WHERE c.clickNum = :clickNum "
            + "GROUP BY c.clickNum, c.userId, c.merchantId, c.regYm, c.regDay")
    CpsRewardUnitDto findByRewardClickNum(@Param("clickNum") int clickNum);
}
