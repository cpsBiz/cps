package com.cps.common.repository;

import com.cps.common.entity.CpsInquiryFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CpsInquiryFileRepository extends JpaRepository<CpsInquiryFileEntity, String> {
    List<CpsInquiryFileEntity> findByInquiryNum(int inquiryNum);
}
