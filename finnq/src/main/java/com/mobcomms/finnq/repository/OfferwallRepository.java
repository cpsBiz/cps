package com.mobcomms.finnq.repository;

import com.mobcomms.finnq.entity.OfferwallEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferwallRepository extends JpaRepository<OfferwallEntity, String> {
    OfferwallEntity save(OfferwallEntity entity);

    long countByUserIdAndMediaAndAdIdAndCode(String userId, String media, String adId, String code);
}
