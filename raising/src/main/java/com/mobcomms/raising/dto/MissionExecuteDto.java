package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.MissionUserHistoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/*
 * Created by shchoi3
 * Create Date : 2024-06-21
 * class 설명, method
 * UpdateDate : 2024-06-21, 업데이트 내용
 */
@Schema(description = "사용자 미션수행 이력")
public record MissionExecuteDto (
    @Schema(description = "수행미션") long missionSeq,
    @Schema(description = "수행미션(B 미션의 경우") long missionItemSeq,
    @Schema(description = "수행캐릭터") long characterSeq
) {
    public static MissionUserHistoryEntity toEntity(MissionExecuteDto dto) {
        return  dto != null ? new MissionUserHistoryEntity(
                null,
                dto.missionSeq,
                dto.missionItemSeq,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ) : null;
    }
}
