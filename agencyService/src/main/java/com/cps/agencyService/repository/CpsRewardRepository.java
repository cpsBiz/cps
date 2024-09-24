package com.cps.agencyService.repository;

import com.cps.agencyService.entity.CpsRewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsRewardRepository extends JpaRepository<CpsRewardEntity, String> {
    CpsRewardEntity save(CpsRewardEntity entity);

    CpsRewardEntity findByClickNum(int clickNum);
}
