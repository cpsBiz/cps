package com.mobcomms.raising.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "미션정보")
public record MissionResDto(
        @Schema(description = "사용자출석정보") UserAttendanceResDto userAttendanceInfo,
        @Schema(description = "룰렛정보") RouletteResDto rouletteMissionInfo,
        @Schema(description = "사용자의 A미션 정보") MissionAResDto missionAResDto,
        @Schema(description = "사용자의 B미션 정보") MissionBResDto missionBResDto,
        @Schema(description = "사용자의 C미션 정보") MissionCResDto missionCResDto
) {
}

@Schema(description = "사용자의 A미션 정보")
record MissionAResDto(
        @Schema(description = "사용할수있는 미션횟수") int missionCount,
        @Schema(description = "일일참여가능횟수") int missionDailyLimit,
        @Schema(description = "재참여가능시간") int missionInterval,
        @Schema(description = "획득 가능경험치") int gainExp,
        @Schema(description = "획득 가능포인트") int gainPoint
) {
}

@Schema(description = "사용자의 B미션 정보")
record MissionBResDto(
        @Schema(description = "사용할수있는 미션횟수") int missionCount,
        @Schema(description = "일일참여가능횟수") int missionDailyLimit,
        @Schema(description = "재참여가능시간") int missionInterval,
        @Schema(description = "획득 가능경험치") int gainExp,
        @Schema(description = "획득 가능포인트") int gainPoint,
        @Schema(description = "미션서브항목") List<MissionItemResDto> missionList
) {
}

@Schema(description = "사용자의 C미션 정보")
record MissionCResDto(
        @Schema(description = "사용할수있는 미션횟수") int missionCount,
        @Schema(description = "일일참여가능횟수") int missionDailyLimit,
        @Schema(description = "재참여가능시간") int missionInterval,
        @Schema(description = "다음미션참여까지 남은시간") int missionLeftTime,
        @Schema(description = "획득 가능경험치") int gainExp,
        @Schema(description = "획득 가능포인트") int gainPoint
) {
}

@Schema(description = "B미션 항목리스트")
record MissionItemResDto(
        @Schema(description = "미션명") int missionItemName,
        @Schema(description = "미션수행여부") String missionDoneYn,
        @Schema(description = "랜딩url") String landingUrl,
        @Schema(description = "확득 가능수") int gainCount
) {}

