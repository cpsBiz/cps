package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsGiftProductEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


@Transactional
public interface CpsGiftProductRepository extends JpaRepository<CpsGiftProductEntity, String> {

}
