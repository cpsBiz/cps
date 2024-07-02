package com.mobcomms.common.model;

import com.mobcomms.common.api.ResultCode;
import lombok.Data;

@Data
public class BaseRequset implements IBaseRequset {
    private String clientCode;
    private String productCode;


}
