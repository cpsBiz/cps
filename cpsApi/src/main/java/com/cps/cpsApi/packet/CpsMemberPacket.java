package com.cps.cpsApi.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsMemberDto;
import com.cps.cpsApi.entity.CpsMemberEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 회원 가입
 * @date 2024-09-03
 */

@Data
public class CpsMemberPacket {

    public static class MemberInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class MemberRequest {
            @NotBlank(message = "memberId 확인")
            private String memberId;
            @NotBlank(message = "managerId 확인")
            private String managerId;
            @NotBlank(message = "memberPw 확인")
            private String memberPw;
            private String companyName;
            private String type;
            private String status;
            private String managerName;
            private String officePhone;
            private String phone;
            private String mail;
            private String address;
            private String buisnessNumber;
            private String category;
            private String categoryName;
            private String rewardYn;
            private String mobileYn;
            private int returnDay;
            private String url;
            private String logo;
            private String clickUrl;
            private String whenTrans;
            private String transReposition;
            private String denyAd;
            private String denyProduct;
            private String notice;
            private String commissionPaymentStandard;
            private String apiType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<CpsMemberDto> {}


        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class AgencyMemberRequest {
            @NotBlank(message = "agencyId 확인")
            private String agencyId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Domain {
            private String domain;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class MemberSearcgRequest {
            private String memberId;
            private String managerId;
            private String companyName;
            private String type;
            private String status;
            private String managerName;
            private String officePhone;
            private String phone;
            private String mail;
            private String address;
            private String buisnessNumber;
            private String category;
            private String rewardYn;
            private String mobileYn;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class MemberSearchResponse extends GenericBaseResponse<CpsMemberEntity> {}

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class LinkPriceAgencyResponse {
            @JsonProperty("merchant_id")
            private String merchantId;
            @JsonProperty("merchant_name")
            private String merchantName;
            @JsonProperty("merchant_url")
            private String merchantUrl;
            @JsonProperty("merchant_logo")
            private String merchantLogo;
            @JsonProperty("category_id")
            private String categoryId;
            @JsonProperty("category_name")
            private String categoryName;
            @JsonProperty("subscript")
            private String subscript;
            @JsonProperty("deeplink_yn")
            private String deeplinkYn;
            @JsonProperty("pc_yn")
            private String pc_yn;
            @JsonProperty("mobile_yn")
            private String mobileYn;
            @JsonProperty("max_commission_pc")
            private String maxCommissionPc;
            @JsonProperty("max_commission_mobile")
            private String maxCommissionMobile;
            @JsonProperty("return_day")
            private int returnDay;
            @JsonProperty("reward_yn")
            private String rewardYn;
            @JsonProperty("click_url")
            private String clickUrl;
            @JsonProperty("merchant_desc")
            private String merchantDesc;
            @JsonProperty("when_trans")
            private String whenTrans;
            @JsonProperty("trans_reposition")
            private String transReposition;
            @JsonProperty("commission_payment_standard")
            private String commissionPaymentStandard;
            @JsonProperty("deny_product")
            private String denyProduct;
            @JsonProperty("deny_ad")
            private String denyAd;
            @JsonProperty("notice")
            private String notice;
            @JsonProperty("app_ios_yn")
            private String appIosYn;
            @JsonProperty("app_android_yn")
            private String appnAdroidYn;
            @JsonProperty("agencyId")
            private String agencyId;
        }

    }
}
