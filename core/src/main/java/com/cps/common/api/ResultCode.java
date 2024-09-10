package com.cps.common.api;

import lombok.Getter;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * 공통 반환 처리 class
 * UpdateDate : 2024-06-27,REQUEST_DATA_ERROR 추가
 */

@Getter
public enum ResultCode {
    SUCCESS("0000", "SUCCESS"),
    ERROR("9999", "ERROR"),
    REQUEST_DATA_ERROR("2000", "REQUEST DATA ERROR"),
    NO_DATA("1000", "데이터가 없습니다.");

    String resultCode;
    String resultMessage;

    ResultCode(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
