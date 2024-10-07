package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsMemberSiteEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CpsMemberSiteRepository extends JpaRepository<CpsMemberSiteEntity, String> {
    CpsMemberSiteEntity save(CpsMemberSiteEntity entity);

    List<CpsMemberSiteEntity> findByMemberId(String memberId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CpsMemberSiteEntity C WHERE C.memberId = :memberId")
    int deleteByMemberId(@Param("memberId") String memberId);
}
