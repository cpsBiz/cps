package com.cps.viewService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CpsMemberCommissionListDto {

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

