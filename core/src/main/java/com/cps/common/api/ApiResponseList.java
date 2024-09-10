package com.cps.common.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class ApiResponseList<T> {
    @Schema(description = "결과코드")
    private String resultCode;
    @Schema(description = "결과메세지")
    private String resultMessage;
    @Schema(description = "응답데이타 리스트")
    private List<T> datas;

    public static ApiResponseList setResponseCode(ResultCode resultCode) {
        return ApiResponseList.builder()
                .resultCode(resultCode.getResultCode())
                .resultMessage(resultCode.getResultMessage())
                .build();
    }

    public static ApiResponseList setErrorMassage(String massage) {
        return ApiResponseList.builder()
                .resultCode(ResultCode.ERROR.getResultCode())
                .resultMessage(massage)
                .build();
    }

    public static <T> ApiResponseList<T> ok(List<T> result) {
        return ApiResponseList.<T>builder()
                .resultCode(ResultCode.SUCCESS.getResultCode())
                .resultMessage(ResultCode.SUCCESS.getResultMessage())
                .datas(Optional.ofNullable(result).orElse(List.of()))
                .build();
    }

    public static <T> ApiResponseList<T> ok() {
        return ApiResponseList.<T>builder().resultCode(ResultCode.SUCCESS.getResultCode()).datas(List.of()).build();
    }

    public static <T> ApiResponseList<T> error(String message) {
        return ApiResponseList.<T>builder()
                .resultCode(ResultCode.ERROR.getResultCode())
                .resultMessage(ResultCode.ERROR.getResultMessage())
                .datas(List.of())
                .build();
    }

    public static <T> ApiResponseList<T> error() {
        return ApiResponseList.<T>builder().resultCode(ResultCode.ERROR.getResultCode()).datas(List.of()).build();
    }

    public static ApiResponseList noData() {
        return ApiResponseList.builder()
                .resultCode(ResultCode.NO_DATA.getResultCode())
                .resultMessage(ResultCode.NO_DATA.getResultCode())
                .datas(List.of())
                .build();
    }
}