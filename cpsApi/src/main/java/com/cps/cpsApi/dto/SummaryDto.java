package com.cps.cpsApi.dto;

import lombok.Data;

@Data
public class SummaryDto {
    private String keywordType;
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
    private int memberCommissionShare;
    private int confirmAffliateCommission;
    private int cancelAffliateCommission;
    private int userCommission;
    private int confirmUserCommission;
    private int cancelUserCommission;

    private String keyword;
    private String keywordName;

    private int regStart;
    private int regEnd;

    public SummaryDto(int regDay, String keywordType, int cnt, int clickCnt, int rewardCnt, int confirmRewardCnt, int cancelRewardCnt, int productPrice, int confirmProductPrice, int cancelProductPrice, int commission, int confirmCommission, int cancelCommission, int commissionProfit, int confirmCommissionProfit, int cancelCommissionProfit, int memberCommissionShare, int confirmAffliateCommission, int cancelAffliateCommission, int userCommission, int confirmUserCommission, int cancelUserCommission) {
        this.regStart = regDay;
        this.regEnd = regDay;
        this.keywordType = keywordType;
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
        this.memberCommissionShare = memberCommissionShare;
        this.confirmAffliateCommission = confirmAffliateCommission;
        this.cancelAffliateCommission = cancelAffliateCommission;
        this.userCommission = userCommission;
        this.confirmUserCommission = confirmUserCommission;
        this.cancelUserCommission = cancelUserCommission;
    }

    public SummaryDto(String keyword, String searchName, String keywordType, int cnt, int clickCnt, int rewardCnt, int confirmRewardCnt, int cancelRewardCnt, int productPrice, int confirmProductPrice, int cancelProductPrice, int commission, int confirmCommission, int cancelCommission, int commissionProfit, int confirmCommissionProfit, int cancelCommissionProfit, int memberCommissionShare, int confirmAffliateCommission, int cancelAffliateCommission, int userCommission, int confirmUserCommission, int cancelUserCommission) {
        this.keyword = keyword;
        this.keywordName = searchName;
        this.keywordType = keywordType;
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
        this.memberCommissionShare = memberCommissionShare;
        this.confirmAffliateCommission = confirmAffliateCommission;
        this.cancelAffliateCommission = cancelAffliateCommission;
        this.userCommission = userCommission;
        this.confirmUserCommission = confirmUserCommission;
        this.cancelUserCommission = cancelUserCommission;
    }

    public SummaryDto(int campaignNum, String campaignName, String keywordType, int cnt, int clickCnt, int rewardCnt, int confirmRewardCnt, int cancelRewardCnt, int productPrice, int confirmProductPrice, int cancelProductPrice, int commission, int confirmCommission, int cancelCommission, int commissionProfit, int confirmCommissionProfit, int cancelCommissionProfit, int memberCommissionShare, int confirmAffliateCommission, int cancelAffliateCommission, int userCommission, int confirmUserCommission, int cancelUserCommission) {
        this.keyword = String.valueOf(campaignNum);
        this.keywordName = campaignName;
        this.keywordType = keywordType;
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
        this.memberCommissionShare = memberCommissionShare;
        this.confirmAffliateCommission = confirmAffliateCommission;
        this.cancelAffliateCommission = cancelAffliateCommission;
        this.userCommission = userCommission;
        this.confirmUserCommission = confirmUserCommission;
        this.cancelUserCommission = cancelUserCommission;
    }

    public SummaryDto() {
    }
}