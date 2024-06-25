package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.MissionEntity;
import com.mobcomms.raising.entity.MissionItemEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/*
 * Created by shchoi3
 * Create Date : 2024-06-20
 * class 설명, method
 * UpdateDate : 2024-06-20, 업데이트 내용
 */
@Data
@Builder
@Schema(description = "사용자의 B미션 정보")
public class MissionB {
    @Schema(description = "사용할수있는 미션횟수") int missionCount;
    @Schema(description = "일일참여가능횟수") int missionDailyLimit;
    @Schema(description = "재참여가능시간") int missionInterval;
    @Schema(description = "획득 가능경험치") int gainExp;
    @Schema(description = "획득 가능포인트") int gainPoint;
    @Schema(description = "미션서브항목")
    List<MissionItemEntity> missionList;

    public static MissionB of(MissionEntity entity, List<MissionItemEntity> entities) {
        return  entity != null ? new MissionB(
                entity.getDailyLimitCount(),
                entity.getDailyLimitCount(),
                entity.getMissionInterval(),
                entity.getGainExp(),
                entity.getGainPoint(),
                entities
                ) : null;
    }
}
