package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsGiftBrandEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface CpsGiftBrandRepository extends JpaRepository<CpsGiftBrandEntity, String> {


}
