package com.cps.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CpsOneToOneDto {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class OneToOne {
        CpsInquiry cpsInquiry;
        CpsAnswer cpsAnswer;
        CpsInquiryFile fileList;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CpsAnswer {
        int inquiryNum;
        String note;
    }


    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CpsInquiryFile {
        List<String> fileName;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CpsInquiry extends CpsAnswer{
        String userId;
        String inquiryType;
        String merchantId;
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


    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class OneToOneMerchat {
        String merchantName;
        String merchantId;
    }
}
