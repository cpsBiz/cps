package com.cps.cpsApi.repository;

import com.cps.cpsApi.entity.CpsAnswerEntity;
import com.cps.cpsApi.entity.CpsInquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsAnswerRepository extends JpaRepository<CpsAnswerEntity, String> {

    CpsAnswerEntity save(CpsAnswerEntity entity);

    CpsAnswerEntity findByInquiryNum(int inquiryNum);

}
