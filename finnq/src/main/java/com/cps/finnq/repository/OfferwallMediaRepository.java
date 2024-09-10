package com.cps.finnq.repository;

import com.cps.finnq.entity.OfferwallMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfferwallMediaRepository extends JpaRepository<OfferwallMediaEntity, String> {
    OfferwallMediaEntity findAllByCode(String code);
}
