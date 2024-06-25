package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.MissionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/*
 * Created by shchoi3
 * Create Date : 2024-06-21
 * class 설명, method
 * UpdateDate : 2024-06-21, 업데이트 내용
 */
@Data
@Builder
@Schema(description = "사용자의 A미션 정보")
public class MissionA {
    @Schema(description = "사용할수있는 미션횟수") int missionCount;
    @Schema(description = "일일참여가능횟수") int missionDailyLimit;
    @Schema(description = "재참여가능시간") int missionInterval;
    @Schema(description = "획득 가능경험치") int gainExp;
    @Schema(description = "획득 가능포인트") int gainPoint;

    public static MissionA of(MissionEntity entity) {
        return  entity != null ? new MissionA(
                entity.getDailyLimitCount(),
                entity.getDailyLimitCount(),
                entity.getMissionInterval(),
                entity.getGainExp(),
                entity.getGainPoint()) : null;
    }
}
