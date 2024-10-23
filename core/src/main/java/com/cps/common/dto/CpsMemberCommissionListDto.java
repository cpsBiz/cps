package com.cps.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpsMemberCommissionListDto {
    String userId;
    int regDay;
    int regYm;
    String campaignName;
    String productName;
    int productPrice;
    int userCommission;
    int productCnt;
    String merchantId;
    int status;
    String commissionPaymentStandard;
}

