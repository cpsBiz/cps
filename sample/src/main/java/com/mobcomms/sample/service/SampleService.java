package com.mobcomms.sample.service;

import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.entity.SampleEntity;
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
public class SampleService {
    private final SampleRepository sampleRepository;

    public List<SampleDto> readSample() {
        return sampleRepository.findAll().stream().map(entity -> SampleDto.of(entity)).collect(Collectors.toList());
    }

    public SampleDto createSample(SampleDto dto) {
        return SampleDto.of(sampleRepository.save(SampleDto.toEntity(dto)));
    }
    public SampleDto updateSample(SampleDto dto) {
        return SampleDto.of(sampleRepository.save(SampleDto.toEntity(dto)));
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public SampleDto deleteInsertSample(SampleDto dto) throws Exception {
        try {
            SampleEntity entity = SampleDto.toEntity(dto);
            sampleRepository.deleteByTestColumn1(entity.getTestColumn1());
            return SampleDto.of(sampleRepository.save(entity));
        } catch (Exception e) {
            throw e;
        }
    }
    public void deleteSample(SampleDto dto) {
        sampleRepository.deleteByTestColumn1(SampleDto.toEntity(dto).getTestColumn1());
    }
}
