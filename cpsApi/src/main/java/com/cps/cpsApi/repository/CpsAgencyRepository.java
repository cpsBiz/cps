package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsAgencyEntity;
import com.cps.cpsApi.entity.CpsUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsAgencyRepository extends JpaRepository<CpsAgencyEntity, String> {
    CpsAgencyEntity save(CpsAgencyEntity entity);

    CpsAgencyEntity findByMemberId(String memberId);

    @Transactional
    void deleteByMemberId(String memberId);
}
