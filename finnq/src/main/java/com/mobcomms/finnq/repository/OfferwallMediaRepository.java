package com.mobcomms.finnq.repository;

import com.mobcomms.finnq.entity.OfferwallMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfferwallMediaRepository extends JpaRepository<OfferwallMediaEntity, String> {
    OfferwallMediaEntity findAllByCode(String code);
}
