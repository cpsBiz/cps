package com.cps.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpsCampaignSearchDto {
        int campaignNum;
        String merchantId;
        String adminId;
        String campaignName;
        String campaignStart;
        String campaignEnd;
        String url;
        String clickUrl;
        String category;
        String logo;
        String icon;
        String campaignAuto;
        String rewardYn;
        String pcYn;
        String mobileYn;
        String aosYn;
        String iosYn;
        int returnDay;
        String commissionSendYn;
        String whenTrans;
        String transReposition;
        String commissionPaymentStandard;
        String denyAd;
        String denyProduct;
        String notice;
        String campaignStatus;
        String memberName;
}

