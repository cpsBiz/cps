package com.cps.cpsApi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
public class CpsOneToOneDto {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class OneToOne {
        CpsInquiry cpsInquiry;
        CpsAnswer cpsAnswer;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CpsAnswer {
        int inquiryNum;
        String note;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CpsInquiry extends CpsAnswer{
        String userId;
        String inquiryType;
        String memberId;
        String purpose;
        int regDay;
        String userName;
        String orderNo;
        String productCode;
        String currency;
        String payment;
        int productPrice;
        int productCnt;
        String email;
        String information;
        String answerYn;
        private LocalDateTime regDate;
    }
}
