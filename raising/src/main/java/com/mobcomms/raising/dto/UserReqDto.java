package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.UserEntity;

public record UserReqDto(String mediaUserKey, String companyCode, String adid, String platform) {
    public static UserEntity toEntity(UserReqDto dto) {
        return dto != null ?
                new UserEntity(null, dto.mediaUserKey, dto.adid, null, null, null, null) : null;
    }
}
