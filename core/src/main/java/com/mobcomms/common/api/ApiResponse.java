package com.mobcomms.common.api;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String resultCode;
    private String resultMessage;
    private T data;
}
