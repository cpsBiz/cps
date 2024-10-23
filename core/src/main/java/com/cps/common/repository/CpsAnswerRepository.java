package com.cps.common.repository;

import com.cps.common.entity.CpsAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsAnswerRepository extends JpaRepository<CpsAnswerEntity, String> {

    CpsAnswerEntity save(CpsAnswerEntity entity);

    CpsAnswerEntity findByInquiryNum(int inquiryNum);

}
