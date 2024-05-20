package com.mobcomms.sample.repository;

import com.mobcomms.sample.entity.Sample2Entity;
import com.mobcomms.sample.entity.Sample2PK;
import com.mobcomms.sample.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Sample2Repository extends JpaRepository<Sample2Entity, Sample2PK> {
    List<Sample2Entity> findAll();
    Sample2Entity save(Sample2Entity entity);
    void deleteBySample2PKTestPk01(String testColumn1);
}
