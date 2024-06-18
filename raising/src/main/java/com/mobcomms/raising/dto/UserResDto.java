package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.UserEntity;

public record UserResDto() {
    public static UserResDto of(UserEntity entity) {
        return  entity != null ? new UserResDto() : null;
    }
}

