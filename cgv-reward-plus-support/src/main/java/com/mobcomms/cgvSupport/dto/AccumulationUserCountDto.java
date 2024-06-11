package com.mobcomms.cgvSupport.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "유저적립카우트 DTO")
public record AccumulationUserCountDto(
        @Schema(description = "집계일")
        String reg,
        @Schema(description = "적립유저수")
        String cnt)
{
    public static AccumulationUserCountDto of(Map<String, Object> map) {
        return  map != null
                ? new AccumulationUserCountDto(String.valueOf(map.get("reg")), String.valueOf(map.get("cnt"))) : null;
    }
}
