package com.mobcomms.common.model;

import lombok.Data;

import java.util.List;

@Data
public class GenericBaseResponse<T> extends BaseResponse{

    private T Data;
    private List<T> Datas;
}
