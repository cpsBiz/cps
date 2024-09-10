package com.cps.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenericBaseResponse<T> extends BaseResponse{

    private T Data;
    private List<T> Datas;
}
