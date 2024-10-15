package com.cps.cpsService.dto;

import lombok.Data;

@Data
public class CpsMemberCommissionDto {
    long userCommission;
    long expectedUserCommission;

    public CpsMemberCommissionDto(Long userCommission, Long expectedUserCommission) {
        this.userCommission = (userCommission != null) ? userCommission : 0L;
        this.expectedUserCommission = (expectedUserCommission != null) ? expectedUserCommission : 0L;
    }
}

