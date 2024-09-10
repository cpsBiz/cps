package com.cps.common.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    @Schema(description = "결과코드")
    private String resultCode;
    @Schema(description = "결과메세지")
    private String resultMessage;
    @Schema(description = "응답데이타")
    private T data;

    public static ApiResponse setResponseCode(ResultCode resultCode) {
        return ApiResponse.builder()
                .resultCode(resultCode.getResultCode())
                .resultMessage(resultCode.getResultMessage())
                .build();
    }

    public static ApiResponse setErrorMassage(String massage) {
        return ApiResponse.builder()
                .resultCode(ResultCode.ERROR.getResultCode())
                .resultMessage(massage)
                .build();
    }

    public static <T> ApiResponse ok(T result) {
        return ApiResponse.builder()
                .resultCode(ResultCode.SUCCESS.getResultCode())
                .resultMessage(ResultCode.SUCCESS.getResultCode())
                .data(result).build();
    }

    public static <T> ApiResponse ok() {
        return ApiResponse.builder().resultCode(ResultCode.SUCCESS.getResultCode()).data(null).build();
    }

    public static <T> ApiResponse error(String message) {
        return ApiResponse.builder()
                .resultCode(ResultCode.ERROR.getResultCode())
                .resultMessage(ResultCode.ERROR.getResultMessage())
                .build();
    }

    public static <T> ApiResponse error() {
        return ApiResponse.builder().resultCode(ResultCode.ERROR.getResultCode()).data(null).build();
    }


    public static ApiResponse noData() {
        return ApiResponse.builder()
                .resultCode(ResultCode.NO_DATA.getResultCode())
                .resultMessage(ResultCode.NO_DATA.getResultCode())
                .build();
    }
}
