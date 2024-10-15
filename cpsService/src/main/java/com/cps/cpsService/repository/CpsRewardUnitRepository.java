package com.cps.cpsService.repository;

import com.cps.cpsService.dto.CpsRewardUnitDto;
import com.cps.cpsService.entity.CpsRewardUnitEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CpsRewardUnitRepository extends JpaRepository<CpsRewardUnitEntity, String> {
    @Modifying
    @Transactional
    @Query("UPDATE CpsRewardUnitEntity u SET u.status = 210 WHERE u.regDay = :regDay")
    void updateByRegDay(@Param("regDay") int regDay);
}
