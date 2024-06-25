package com.mobcomms.raising.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/*
 * Created by shchoi3
 * Create Date : 2024-06-24
 * class 설명, method
 * UpdateDate : 2024-06-24, 업데이트 내용
 */
@Data
@Builder
public class SaveResDto {
    @Schema(description = "총 적립포인트") int userGamePoint;
    @Schema(description = "상품수령가능여부") String goodsDoneYn;
    @Schema(description = "경험치") int exp;
    @Schema(description = "레벨") int level;
    @Schema(description = "레벨업여부") String levelUpYn;
    @Schema(description = "경험치 퍼센티지") float expRate;
    @Schema(description = "포인트 퍼센티지") float pointRate;
    @Schema(description = "만렙여부") String maxLevelYn;
    @Schema(description = "미션 먹이주기 횟수(메인하단") int missionCount;
    @Schema(description = "미션 일일참여 잔여횟수") int dailyLimitCount;
}
