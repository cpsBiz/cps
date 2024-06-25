package com.mobcomms.raising.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/*
 * Created by shchoi3
 * Create Date : 2024-06-21
 * class 설명, method
 * UpdateDate : 2024-06-21, 업데이트 내용
 */
public record SaveDto(
        @Schema(description = "미션수행히스토리 고유번호") long missionHistorySeq,
        @Schema(description = "획득 경험치") int exp,
        @Schema(description = "획득 포인트") int point
) {
}
