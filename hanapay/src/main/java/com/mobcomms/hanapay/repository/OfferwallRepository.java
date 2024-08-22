package com.mobcomms.hanapay.repository;

import com.mobcomms.hanapay.entity.OfferwallEntity;
import com.mobcomms.hanapay.entity.PointEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferwallRepository extends JpaRepository<OfferwallEntity, String> {
    OfferwallEntity save(OfferwallEntity entity);

    long countByUserIdAndMediaAndAdIdAndCode(String userId, String media, String adId, String code);
}
