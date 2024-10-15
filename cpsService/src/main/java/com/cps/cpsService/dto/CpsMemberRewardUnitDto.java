package com.cps.cpsService.dto;

import lombok.Data;

@Data
public class CpsMemberRewardUnitDto {
    Long cnt;
    Long expectedCnt;

    public CpsMemberRewardUnitDto(Long cnt, Long expectedUserCommission) {
        this.cnt = (cnt != null) ? cnt : 0L;
        this.expectedCnt = (expectedUserCommission != null) ? expectedUserCommission : 0L;
    }
}

