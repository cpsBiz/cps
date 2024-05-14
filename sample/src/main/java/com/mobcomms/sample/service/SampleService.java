package com.mobcomms.sample.service;

import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.entity.SampleEntity;
import com.mobcomms.sample.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository sampleRepository;

    public List<SampleDto> readSample() {
        return sampleRepository.findAll().stream().map(entity -> SampleDto.of(entity)).collect(Collectors.toList());
    }

    public SampleDto createSample(SampleEntity entity) {
        return SampleDto.of(sampleRepository.save(entity));
    }
    public SampleDto updateSample(SampleEntity entity) {
        return SampleDto.of(sampleRepository.save(entity));
    }
    public SampleDto deleteInsertSample(SampleEntity entity) {
        try {
            sampleRepository.deleteByTestColumn1(entity);
            return SampleDto.of(sampleRepository.save(entity));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteSample(SampleEntity entity) {
        sampleRepository.deleteByTestColumn1(entity);
    }
}
