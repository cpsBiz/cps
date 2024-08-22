package com.mobcomms.hanapay.repository;

import com.mobcomms.hanapay.entity.OfferwallMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfferwallMediaRepository extends JpaRepository<OfferwallMediaEntity, String> {
    OfferwallMediaEntity findAllByCode(String code);
}
