package com.mobcomms.shinhan.repository;

import com.mobcomms.shinhan.entity.ShinhanSampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShinhanSampleRepository extends JpaRepository<ShinhanSampleEntity, String> {
    List<ShinhanSampleEntity> findAll();
    ShinhanSampleEntity save(ShinhanSampleEntity entity);
    void deleteByTestColumn1(String testColumn1);
}
