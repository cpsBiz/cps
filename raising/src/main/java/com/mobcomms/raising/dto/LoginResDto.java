package com.mobcomms.raising.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답데이타")
public record LoginResDto(
    @Schema(description = "신규유저여부") Boolean newUserYn,
    @Schema(description = "미션정보") MissionResDto missionInfos
) {

}
