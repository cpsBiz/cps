package com.cps.agencyService.repository;

import com.cps.agencyService.entity.ClickEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickRepository extends JpaRepository<ClickEntity, String> {
    ClickEntity save(ClickEntity entity);

}
