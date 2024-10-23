package com.cps.common.repository;

import com.cps.common.entity.CpsMemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsMemberRepository extends JpaRepository<CpsMemberEntity, String> {
    CpsMemberEntity save(CpsMemberEntity entity);

    CpsMemberEntity findByMemberId(String memberId);

    @Transactional
    void deleteByMemberId(String memberId);
}
