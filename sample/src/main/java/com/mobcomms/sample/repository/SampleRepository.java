package com.mobcomms.sample.repository;

import com.mobcomms.sample.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SampleRepository extends JpaRepository<SampleEntity, String> {
    List<SampleEntity> findAll();
    SampleEntity save(SampleEntity entity);
    void deleteByTestColumn1(SampleEntity entity);
}
