package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.UserEntity;

public record UserReqDto() {
    public static UserEntity toEntity(UserReqDto dto) {
        return dto != null ? new UserEntity() : null;
    }
}
