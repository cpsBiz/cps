package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsUserRepository extends JpaRepository<CpsUserEntity, String> {
    CpsUserEntity save(CpsUserEntity entity);

    CpsUserEntity findByMemberId(String memberId);

    @Transactional
    void deleteByMemberId(String memberId);
}
