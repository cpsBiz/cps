package com.mobcomms.raising.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "로그인 응답데이타")
public record LoginResDto(
    @Schema(description = "신규유저여부") String newUserYn,
    @Schema(description = "미션정보") MissionResDto missionInfos,
    @Schema(description = "사용자게임정보") List<UserGameInfos> stageInfos
) {

}
