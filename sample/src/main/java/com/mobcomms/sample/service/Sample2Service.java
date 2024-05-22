package com.mobcomms.sample.service;

import com.mobcomms.sample.dto.Sample2Dto;
import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.entity.Sample2Entity;
import com.mobcomms.sample.entity.SampleEntity;
import com.mobcomms.sample.repository.Sample2Repository;
import com.mobcomms.sample.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional("transactionManager")
@RequiredArgsConstructor
public class Sample2Service {
    private final Sample2Repository sample2Repository;

    public List<Sample2Dto> readSample() {
        return sample2Repository.findAll().stream().map(entity -> Sample2Dto.of(entity)).collect(Collectors.toList());
    }

    public Sample2Dto createSample(Sample2Dto dto) {
        return Sample2Dto.of(sample2Repository.save(Sample2Dto.toEntity(dto)));
    }
    public Sample2Dto updateSample(Sample2Dto dto) {
        return Sample2Dto.of(sample2Repository.save(Sample2Dto.toEntity(dto)));
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public Sample2Dto deleteInsertSample(Sample2Dto dto) throws Exception {
        try {
            Sample2Entity entity = Sample2Dto.toEntity(dto);
            sample2Repository.deleteBySample2PKTestPk01(entity.getTestColumn1());
            return Sample2Dto.of(sample2Repository.save(entity));
        } catch (Exception e) {
            throw e;
        }
    }
    public void deleteSample(Sample2Dto dto) {
        sample2Repository.deleteBySample2PKTestPk01(Sample2Dto.toEntity(dto).getTestColumn1());
    }
}
