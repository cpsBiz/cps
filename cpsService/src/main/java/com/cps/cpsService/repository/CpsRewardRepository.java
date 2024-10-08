package com.cps.cpsService.repository;

import com.cps.cpsService.dto.CpsRewardUnitDto;
import com.cps.cpsService.entity.CpsRewardEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CpsRewardRepository extends JpaRepository<CpsRewardEntity, String> {
    CpsRewardEntity save(CpsRewardEntity entity);

    CpsRewardEntity findByClickNum(int clickNum);

    CpsRewardEntity findByClickNumAndOrderNoAndProductCode(int clickNum, String orderNo, String productCode);

    @Query("SELECT new com.cps.cpsService.dto.CpsRewardUnitDto(c.clickNum, c.userId, c.regDay, c.regYm, "
            + "MAX(CASE WHEN c.status != 310 THEN c.productName ELSE '' END), "
            + "FLOOR(COALESCE(SUM(CASE WHEN c.status != 310 THEN c.productPrice ELSE 0 END), 0) / 10000), "
            + "COALESCE(SUM(CASE WHEN c.status != 310 THEN c.productPrice ELSE 0 END), 0), "
            + "COALESCE(SUM(CASE WHEN c.status != 310 THEN 1 ELSE 0 END), 0), "
            + "c.merchantId, c.affliateId) "
            + "FROM CpsRewardEntity c "
            + "WHERE c.clickNum = :clickNum "
            + "GROUP BY c.clickNum, c.userId, c.merchantId, c.regYm, c.regDay")
    CpsRewardUnitDto findByRewardClickNum(@Param("clickNum") int clickNum);
}
