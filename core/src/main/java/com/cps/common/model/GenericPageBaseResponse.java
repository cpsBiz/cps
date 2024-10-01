package com.cps.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenericPageBaseResponse<T> extends BasePageResponse{
    private List<T> Datas;
}
