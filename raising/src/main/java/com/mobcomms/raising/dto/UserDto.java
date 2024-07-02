package com.mobcomms.raising.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobcomms.raising.entity.CharacterEntity;
import com.mobcomms.raising.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserDto(
        @Schema(description = "유저고유번호") Long userSeq,
        @Schema(description = "매체사유저키") String mediaUserKey,
        @Schema(description = "adid") String adid,
        @Schema(description = "플랫폼타입(1:ios,2:android,3:pc)") Short platform,
        @Schema(description = "추천인코드") String recommendCode,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "마지막로그인시간") LocalDateTime lastLoginDate
) {
    public static UserDto of(UserEntity entity) {
        return  entity != null
                ? new UserDto(
                    entity.getId(),
                    entity.getMediaUserKey(),
                    entity.getAdid(),
                    entity.getPlatform(),
                    entity.getRecommendCode(),
                    entity.getLastLoginDate()) : null;
    }
}
