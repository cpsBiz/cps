package com.cps.cpsService.repository;

import com.cps.cpsService.entity.CpsAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsAnswerRepository extends JpaRepository<CpsAnswerEntity, String> {

    CpsAnswerEntity save(CpsAnswerEntity entity);

    CpsAnswerEntity findByInquiryNum(int inquiryNum);

}
