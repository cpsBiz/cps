package com.cps.cpsService.repository;

import com.cps.cpsService.dto.CpsRewardUnitDto;
import com.cps.cpsService.entity.CpsRewardUnitEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CpsRewardUnitRepository extends JpaRepository<CpsRewardUnitEntity, String> {
}
