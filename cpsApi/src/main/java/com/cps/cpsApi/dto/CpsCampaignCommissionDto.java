package com.cps.cpsApi.dto;

import lombok.Data;

@Data
public class CpsCampaignCommissionDto {
    int campaignNum;
    String memberId;
    int memberCommissionShare;
    int userCommissionShare;
    String status;
}

