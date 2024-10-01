package com.cps.common.model;

import com.cps.common.api.ResultCode;
import lombok.Data;

@Data
public class BasePageResponse implements IBaseResponse{
    private String resultCode;
    private String resultMessage;
    private long totalPage;

    public void setSuccess(long totalPage){
        this.resultCode = ResultCode.SUCCESS.getResultCode();
        this.resultMessage = ResultCode.SUCCESS.getResultMessage();
        this.totalPage = totalPage;
    }

    public void setError(String errorMsg){
        this.resultCode = ResultCode.ERROR.getResultCode();
        this.resultMessage = errorMsg;
    }

    public void setApiMessage(String code, String errorMsg){
        this.resultCode = code;
        this.resultMessage = errorMsg;
    }

    public void setRequestError(String errorMsg){
        this.resultCode = ResultCode.REQUEST_DATA_ERROR.getResultCode();
        this.resultMessage = errorMsg;
    }


    public static BasePageResponse ok() {
        return new BasePageResponse(){{
            setResultMessage(ResultCode.SUCCESS.getResultCode());
            setResultMessage(ResultCode.SUCCESS.getResultMessage());
        }};
    }

    public static BasePageResponse error(String message) {
        return new BasePageResponse(){{
            setResultMessage(ResultCode.ERROR.getResultCode());
            setResultMessage(message);
        }};
    }
}

