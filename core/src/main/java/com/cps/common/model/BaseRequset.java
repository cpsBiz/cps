package com.cps.common.model;

import lombok.Data;

@Data
public class BaseRequset implements IBaseRequset {
    private String clientCode;
    private String productCode;


}
