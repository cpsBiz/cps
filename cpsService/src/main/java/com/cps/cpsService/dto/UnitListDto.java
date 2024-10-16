package com.cps.cpsService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitListDto {
    int rewardUnitNum;
    Long cnt;
    int stockCnt;
    Long totalPrice;
    String productName;
    Long rewardCnt;
    int status;
    int regDay;
}

