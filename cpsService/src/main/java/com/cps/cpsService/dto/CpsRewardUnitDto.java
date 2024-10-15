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
    long unitCnt;
    long unitPrice;
    int rewardUnitNum;

    public CpsRewardUnitDto(Long unitPrice, Long unitCnt) {
        this.unitPrice = (unitPrice != null) ? unitPrice : 0L;
        this.unitCnt = (unitCnt != null) ? unitCnt : 0L;
    }
}

