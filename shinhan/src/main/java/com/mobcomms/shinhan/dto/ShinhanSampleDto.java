package com.mobcomms.shinhan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobcomms.shinhan.entity.ShinhanSampleEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "샘플 DTO")
public record ShinhanSampleDto(
        @Schema(description = "테스트 컬럼1")
        String testColumn1,
        @Schema(description = "테스트 컬럼2")
        String testColumn2,
        @Schema(description = "테스트 등록일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime testRegDttm)
{
    public static ShinhanSampleEntity toEntity(ShinhanSampleDto dto) {
        return dto != null ? new ShinhanSampleEntity(dto.testColumn1(), dto.testColumn2(), dto.testRegDttm) : null;
    }
    public static ShinhanSampleDto of(ShinhanSampleEntity entity) {
        return  entity != null
                ? new ShinhanSampleDto(entity.getTestColumn1(), entity.getTestColumn2(), entity.getTestRegDttm()) : null;
    }
}
