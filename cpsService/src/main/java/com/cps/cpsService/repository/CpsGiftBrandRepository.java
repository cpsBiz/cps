package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsGiftBrandEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface CpsGiftBrandRepository extends JpaRepository<CpsGiftBrandEntity, String> {


}
