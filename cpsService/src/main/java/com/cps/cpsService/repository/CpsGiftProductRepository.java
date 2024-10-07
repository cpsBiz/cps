package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsGiftProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface CpsGiftProductRepository extends JpaRepository<CpsGiftProductEntity, String> {


}
