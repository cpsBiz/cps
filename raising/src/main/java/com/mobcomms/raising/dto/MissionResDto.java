package com.mobcomms.raising.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MissionResDto(
        @Schema(description = "출석정보") AttendanceResDto attendanceInfo,
        @Schema(description = "룰렛정보") RouletteResDto rouletteMissionInfo
) {
}
