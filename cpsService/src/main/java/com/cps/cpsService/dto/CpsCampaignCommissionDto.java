package com.cps.cpsService.dto;

import lombok.Data;

@Data
public class CpsCampaignCommissionDto {
    int campaignNum;
    String merchantId;
    int memberCommissionShare;
    int userCommissionShare;
    String status;
}

