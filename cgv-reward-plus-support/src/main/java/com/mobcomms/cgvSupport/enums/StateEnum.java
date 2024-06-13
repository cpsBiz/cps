package com.mobcomms.cgvSupport.enums;

import com.mobcomms.common.enums.EnumType;

public enum StateEnum implements EnumType {
    AVAILABLE("0"),
    PURCHASED("1"),
    ISSUED("2"),
    DISCARDED("3"),
    REFUNDED("4");

    private final String code;

    StateEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static StateEnum fromCode(String code) {
        for (StateEnum value : StateEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
