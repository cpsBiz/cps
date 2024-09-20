package com.cps.cpsApi.dto;

import lombok.Data;

@Data
public class CpsAffiliateCampaignDto {
    String affiliateId;
    String memberId;
    int campaignNum;
    String status;
}

