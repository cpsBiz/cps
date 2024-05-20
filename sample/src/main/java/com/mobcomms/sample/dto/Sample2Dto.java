package com.mobcomms.sample.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobcomms.sample.entity.Sample2Entity;
import com.mobcomms.sample.entity.Sample2PK;
import com.mobcomms.sample.entity.SampleEntity;

import java.time.LocalDateTime;

public record Sample2Dto(
        String testPk01,
        String testPk02,
        String testPk03,
        String testColumn1,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime testRegDttm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime testAltDttm
) {
    public static Sample2Entity toEntity(Sample2Dto dto) {
        return dto != null
                ? new Sample2Entity(
                        new Sample2PK(dto.testPk01, dto.testPk02, dto.testPk03),
                        dto.testColumn1,
                        dto.testRegDttm,
                        dto.testAltDttm) : null;
    }
    public static Sample2Dto of(Sample2Entity entity) {
        return  entity != null
                ? new Sample2Dto(
                        entity.getSample2PK().getTestPk01(),
                        entity.getSample2PK().getTestPk02(),
                        entity.getSample2PK().getTestPk03(),
                entity.getTestColumn1(),
                entity.getTestRegDttm(),
                entity.getTestAltDttm()) : null;
    }
}
