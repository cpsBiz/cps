package com.cps.common.repository;

import com.cps.common.entity.CpsInquiryEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CpsInquiryRepository extends JpaRepository<CpsInquiryEntity, String> {

    CpsInquiryEntity save(CpsInquiryEntity entity);

    CpsInquiryEntity findByInquiryNum(int inquiryNum);

    @Modifying
    @Transactional
    @Query("UPDATE CpsInquiryEntity C SET C.answerYn = 'Y' WHERE C.inquiryNum IN :inquiryNum")
    int updateAnswerYnByInquiryNum(@Param("inquiryNum") Integer inquiryNum);

}
