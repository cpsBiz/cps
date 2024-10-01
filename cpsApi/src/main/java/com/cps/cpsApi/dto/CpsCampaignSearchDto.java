package com.cps.cpsApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpsCampaignSearchDto {
        int campaignNum;
        String memberId;
        String agencyId;
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
}

