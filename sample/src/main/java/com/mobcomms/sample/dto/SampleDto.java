package com.mobcomms.sample.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobcomms.common.utils.MobDateUtils;
import com.mobcomms.sample.entity.SampleEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record SampleDto (
        @Schema(description = "테스트 컬럼1")
        String testColumn1,
        @Schema(description = "테스트 컬럼2")
        String testColumn2,
        @Schema(description = "테스트 등록일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime testRegDttm)
{
    public static SampleEntity toEntity(SampleDto dto) {
        return dto != null ? new SampleEntity(dto.testColumn1(), dto.testColumn2(), dto.testRegDttm) : null;
    }
    public static SampleDto of(SampleEntity entity) {
        return  entity != null
                ? new SampleDto(entity.getTestColumn1(), entity.getTestColumn2(), entity.getTestRegDttm()) : null;
    }
}
