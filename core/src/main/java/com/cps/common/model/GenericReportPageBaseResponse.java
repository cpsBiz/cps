package com.cps.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenericReportPageBaseResponse<T> extends BaseReportPageResponse{
    private List<T> Datas;
}
