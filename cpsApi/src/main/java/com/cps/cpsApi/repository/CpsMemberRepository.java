package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsMemberEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsMemberRepository extends JpaRepository<CpsMemberEntity, String> {
    CpsMemberEntity save(CpsMemberEntity entity);

    CpsMemberEntity findByMemberIdAndManagerId(String memberId, String managerId);

    @Transactional
    void deleteByMemberId(String memberId);
}
