package com.mobcomms.shinhan.service;

import com.mobcomms.shinhan.dto.ShinhanSampleDto;
import com.mobcomms.shinhan.entity.ShinhanSampleEntity;
import com.mobcomms.shinhan.repository.ShinhanSampleRepository;
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
public class ShinhanSampleService {
    private final ShinhanSampleRepository sampleRepository;

    public List<ShinhanSampleDto> readSample() {
        return sampleRepository.findAll().stream().map(entity -> ShinhanSampleDto.of(entity)).collect(Collectors.toList());
    }

    public ShinhanSampleDto createSample(ShinhanSampleDto dto) {
        return ShinhanSampleDto.of(sampleRepository.save(ShinhanSampleDto.toEntity(dto)));
    }
    public ShinhanSampleDto updateSample(ShinhanSampleDto dto) {
        return ShinhanSampleDto.of(sampleRepository.save(ShinhanSampleDto.toEntity(dto)));
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public ShinhanSampleDto deleteInsertSample(ShinhanSampleDto dto) throws Exception {
        try {
            ShinhanSampleEntity entity = ShinhanSampleDto.toEntity(dto);
            sampleRepository.deleteByTestColumn1(entity.getTestColumn1());
            return ShinhanSampleDto.of(sampleRepository.save(entity));
        } catch (Exception e) {
            throw e;
        }
    }
    public void deleteSample(ShinhanSampleDto dto) {
        sampleRepository.deleteByTestColumn1(ShinhanSampleDto.toEntity(dto).getTestColumn1());
    }
}
