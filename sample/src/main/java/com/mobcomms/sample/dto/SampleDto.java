package com.mobcomms.sample.dto;

import com.mobcomms.sample.entity.SampleEntity;

public record SampleDto (String testColumn1, String testColumn2){
    public static SampleEntity toEntity(SampleDto dto) {
        return dto != null ? new SampleEntity(dto.testColumn1(), dto.testColumn2()) : null;
    }
    public static SampleDto of(SampleEntity entity) {
        return  entity != null ? new SampleDto(entity.getTestColumn1(), entity.getTestColumn2()) : null;
    }
}
