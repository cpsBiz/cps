package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserRegDto(
        @Schema(description = "매체사유저키") String mediaUserKey,
        @Schema(description = "adid") String adid,
        @Schema(description = "플랫폼타입(1:ios,2:android,3:pc)") Short platform
) {
    public static UserEntity toEntity(UserRegDto dto) {
        return dto != null
                ? new UserEntity(null, dto.mediaUserKey, dto.adid, dto.platform,
                null, null, null) : null;
    }
}

