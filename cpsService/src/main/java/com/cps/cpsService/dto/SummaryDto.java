package com.cps.cpsService.dto;

import lombok.Data;

@Data
public class SummaryDto {

    private String keyWord;
    private String keyWordName;
    private int cnt;
    private int clickCnt;
    private int rewardCnt;
    private int confirmRewardCnt;
    private int cancelRewardCnt;
    private int productPrice;
    private int confirmProductPrice;
    private int cancelProductPrice;
    private int commission;
    private int confirmCommission;
    private int cancelCommission;
    private int commissionProfit;
    private int confirmCommissionProfit;
    private int cancelCommissionProfit;
    private int affliateCommission;
    private int confirmAffliateCommission;
    private int cancelAffliateCommission;
    private int userCommission;
    private int confirmUserCommission;
    private int cancelUserCommission;

    public SummaryDto(int keyWord, int searchKeyName, int cnt, int clickCnt, int rewardCnt, int confirmRewardCnt, int cancelRewardCnt, int productPrice, int confirmProductPrice, int cancelProductPrice, int commission, int confirmCommission, int cancelCommission, int commissionProfit, int confirmCommissionProfit, int cancelCommissionProfit, int memberCommissionShare, int confirmAffliateCommission, int cancelAffliateCommission, int userCommission, int confirmUserCommission, int cancelUserCommission) {
        this.keyWord = String.valueOf(keyWord);
        this.keyWordName = String.valueOf(keyWord);
        this.cnt = cnt;
        this.clickCnt = clickCnt;
        this.rewardCnt = rewardCnt;
        this.confirmRewardCnt = confirmRewardCnt;
        this.cancelRewardCnt = cancelRewardCnt;
        this.productPrice = productPrice;
        this.confirmProductPrice = confirmProductPrice;
        this.cancelProductPrice = cancelProductPrice;
        this.commission = commission;
        this.confirmCommission = confirmCommission;
        this.cancelCommission = cancelCommission;
        this.commissionProfit = commissionProfit;
        this.confirmCommissionProfit = confirmCommissionProfit;
        this.cancelCommissionProfit = cancelCommissionProfit;
        this.affliateCommission = affliateCommission;
        this.confirmAffliateCommission = confirmAffliateCommission;
        this.cancelAffliateCommission = cancelAffliateCommission;
        this.userCommission = userCommission;
        this.confirmUserCommission = confirmUserCommission;
        this.cancelUserCommission = cancelUserCommission;
    }

    public SummaryDto(int keyWord, String keyWordName, int cnt, int clickCnt, int rewardCnt, int confirmRewardCnt, int cancelRewardCnt, int productPrice, int confirmProductPrice, int cancelProductPrice, int commission, int confirmCommission, int cancelCommission, int commissionProfit, int confirmCommissionProfit, int cancelCommissionProfit, int affliateCommission, int confirmAffliateCommission, int cancelAffliateCommission, int userCommission, int confirmUserCommission, int cancelUserCommission) {
        this.keyWord = String.valueOf(keyWord);
        this.keyWordName = keyWordName;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
        this.rewardCnt = rewardCnt;
        this.confirmRewardCnt = confirmRewardCnt;
        this.cancelRewardCnt = cancelRewardCnt;
        this.productPrice = productPrice;
        this.confirmProductPrice = confirmProductPrice;
        this.cancelProductPrice = cancelProductPrice;
        this.commission = commission;
        this.confirmCommission = confirmCommission;
        this.cancelCommission = cancelCommission;
        this.commissionProfit = commissionProfit;
        this.confirmCommissionProfit = confirmCommissionProfit;
        this.cancelCommissionProfit = cancelCommissionProfit;
        this.affliateCommission = affliateCommission;
        this.confirmAffliateCommission = confirmAffliateCommission;
        this.cancelAffliateCommission = cancelAffliateCommission;
        this.userCommission = userCommission;
        this.confirmUserCommission = confirmUserCommission;
        this.cancelUserCommission = cancelUserCommission;
    }



    public SummaryDto(String keyWord, String keyWordName, int cnt, int clickCnt, int rewardCnt, int confirmRewardCnt, int cancelRewardCnt, int productPrice, int confirmProductPrice, int cancelProductPrice, int commission, int confirmCommission, int cancelCommission, int commissionProfit, int confirmCommissionProfit, int cancelCommissionProfit, int affliateCommission, int confirmAffliateCommission, int cancelAffliateCommission, int userCommission, int confirmUserCommission, int cancelUserCommission) {
        this.keyWord = keyWord;
        this.keyWordName = keyWordName;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
        this.rewardCnt = rewardCnt;
        this.confirmRewardCnt = confirmRewardCnt;
        this.cancelRewardCnt = cancelRewardCnt;
        this.productPrice = productPrice;
        this.confirmProductPrice = confirmProductPrice;
        this.cancelProductPrice = cancelProductPrice;
        this.commission = commission;
        this.confirmCommission = confirmCommission;
        this.cancelCommission = cancelCommission;
        this.commissionProfit = commissionProfit;
        this.confirmCommissionProfit = confirmCommissionProfit;
        this.cancelCommissionProfit = cancelCommissionProfit;
        this.affliateCommission = affliateCommission;
        this.confirmAffliateCommission = confirmAffliateCommission;
        this.cancelAffliateCommission = cancelAffliateCommission;
        this.userCommission = userCommission;
        this.confirmUserCommission = confirmUserCommission;
        this.cancelUserCommission = cancelUserCommission;
    }
    public SummaryDto() {
    }
}