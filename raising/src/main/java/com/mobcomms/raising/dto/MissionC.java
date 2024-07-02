package com.mobcomms.raising.dto;

/*
 * Created by shchoi3
 * Create Date : 2024-06-20
 * class 설명, method
 * UpdateDate : 2024-06-20, 업데이트 내용
 */

import com.mobcomms.raising.entity.MissionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "사용자의 C미션 정보")
public class MissionC {
    @Schema(description = "사용할수있는 미션횟수") int missionCount;
    @Schema(description = "일일참여가능횟수") int missionDailyLimit;
    @Schema(description = "재참여가능시간") int missionInterval;
    @Schema(description = "다음미션참여까지 남은시간") int missionLeftTime;
    @Schema(description = "획득 가능경험치") int gainExp;
    @Schema(description = "획득 가능포인트") int gainPoint;

    public static MissionC of(MissionEntity entity) {
        return  entity != null ? new MissionC(
                entity.getDailyLimitCount(),
                entity.getDailyLimitCount(),
                entity.getMissionInterval(),
                0,
                entity.getGainExp(),
                entity.getGainPoint()) : null;
    }
}