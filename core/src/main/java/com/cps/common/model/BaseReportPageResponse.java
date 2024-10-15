package com.cps.common.model;

import com.cps.common.api.ResultCode;
import lombok.Data;

@Data
public class BaseReportPageResponse implements IBaseResponse{
    private String resultCode;
    private String resultMessage;
    private long totalCount;
    private long cnt;
    private long clickCnt;
    private long rewardCnt;
    private long productPrice;
    private long commission;
    private long commissionProfit;
    private long affliateCommission;
    private long userCommission;

    public void setSuccess(long totalCount, long cnt, long clickCnt, long rewardCnt, long productPrice, long commission, long commissionProfit, long affliateCommission, long userCommission) {
        this.resultCode = ResultCode.SUCCESS.getResultCode();
        this.resultMessage = ResultCode.SUCCESS.getResultMessage();
        this.totalCount = totalCount;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
        this.rewardCnt = rewardCnt;
        this.productPrice = productPrice;
        this.commission = commission;
        this.commissionProfit = commissionProfit;
        this.affliateCommission = affliateCommission;
        this.userCommission = userCommission;
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


    public static BaseReportPageResponse ok() {
        return new BaseReportPageResponse(){{
            setResultMessage(ResultCode.SUCCESS.getResultCode());
            setResultMessage(ResultCode.SUCCESS.getResultMessage());
        }};
    }

    public static BaseReportPageResponse error(String message) {
        return new BaseReportPageResponse(){{
            setResultMessage(ResultCode.ERROR.getResultCode());
            setResultMessage(message);
        }};
    }
}

