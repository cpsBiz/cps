package com.mobcomms.common.api;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("1000", "SUCCESS"),
    ERROR("2000", "ERROR"),
    NO_DATA("0000", "데이터가 없습니다.");

    String resultCode;
    String resultMessage;

    ResultCode(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
