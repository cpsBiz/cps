package com.cps.viewService.repository;

import com.cps.viewService.entity.CpsAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpsAnswerRepository extends JpaRepository<CpsAnswerEntity, String> {

    CpsAnswerEntity save(CpsAnswerEntity entity);

    CpsAnswerEntity findByInquiryNum(int inquiryNum);

}
