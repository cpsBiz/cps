package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.common.model.GenericPageBaseResponse;
import com.cps.cpsService.dto.CpsOneToOneDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 문의 등록/답변
 *
 * @date 2024-10-01
 */

@Data
public class CpsOnetoOnePacket {

    public static class CpsOnetoOneInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class OnetoOneRequest {
            private int inquiryNum;
            private String note;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Inquiry extends OnetoOneRequest{
            private String userId;
            private String inquiryType;
            private int campaignNum;
            private String merchantId;
            private String purpose;
            private int regDay;
            private String userName;
            private String orderNo;
            private String productCode;
            private String currency;
            private String payment;
            private int productPrice;
            private int productCnt;
            private String email;
            private String information;
            private String answerYn;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class InquiryResponse extends GenericBaseResponse<CpsOneToOneDto.CpsInquiry> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class InquiryPageResponse extends GenericPageBaseResponse<CpsOneToOneDto.CpsInquiry> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class OneToOneResponse extends GenericBaseResponse<CpsOneToOneDto.OneToOne> {}


        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class InquirySearchRequest {
            public String searchKeyword;
            public String answerYn;
            public String category;
            private int page;
            private int size;
        }

    }
}
