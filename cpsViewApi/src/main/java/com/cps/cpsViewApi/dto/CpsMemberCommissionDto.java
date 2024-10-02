package com.cps.cpsViewApi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CpsMemberCommissionDto {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class MemberCommissionList {
        String userId;
        int regDay;
        int regYm;
        String campaignName;
        String productName;
        int productPrice;
        int userCommission;
        int productCnt;
        String memberId;
        int status;
        String commissionPaymentStandard;
    }
}

