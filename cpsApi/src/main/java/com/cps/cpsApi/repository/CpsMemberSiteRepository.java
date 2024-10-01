package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsMemberSiteEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CpsMemberSiteRepository extends JpaRepository<CpsMemberSiteEntity, String> {
    CpsMemberSiteEntity save(CpsMemberSiteEntity entity);

    List<CpsMemberSiteEntity> findByMemberId(String memberId);

    @Transactional
    void deleteByMemberId(String memberId);
}
