package com.cps.common.model;

import com.cps.common.api.ResultCode;
import lombok.Data;

@Data
public class BaseResponse implements IBaseResponse{
    private String resultCode;
    private String resultMessage;

    public void setSuccess(){
        this.resultCode = ResultCode.SUCCESS.getResultCode();
        this.resultMessage = ResultCode.SUCCESS.getResultMessage();
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


    public static BaseResponse ok() {
        return new BaseResponse(){{
            setResultMessage(ResultCode.SUCCESS.getResultCode());
            setResultMessage(ResultCode.SUCCESS.getResultMessage());
        }};
    }

    public static BaseResponse error(String message) {
        return new BaseResponse(){{
            setResultMessage(ResultCode.ERROR.getResultCode());
            setResultMessage(message);
        }};
    }
}

