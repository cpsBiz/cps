package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsGiftHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface CpsGiftHistoryRepository extends JpaRepository<CpsGiftHistoryEntity, String> {
}
