package com.mobcomms.common.model;

import lombok.Data;

import java.util.List;

@Data
public class BaseResponse implements IBaseResponse{
    private String resultCode;
    private String resultMessage;

}

