package com.cps.cpsService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpsRewardUnitDto {
    int clickNum;
    String userId;
    int regDay;
    int regYm;
    String productName;
    long cnt;
    long totalPrice;
    long rewardCnt;
    String merchantId;
    String affliateId;
}

