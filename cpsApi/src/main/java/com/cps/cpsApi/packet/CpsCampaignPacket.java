package com.cps.cpsApi.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsCampaignDto;
import com.cps.cpsApi.entity.CpsCampaignEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 캠페인
 * @date 2024-09-10
 */

@Data
public class CpsCampaignPacket {

    public static class CampaignInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignRequest {
            private int campaignNum;
            @NotBlank(message = "managerId 확인")
            private String managerId;
            @NotBlank(message = "memberId 확인")
            private String memberId;
            private String campaignName;
            private String campaignStart;
            private String campaignEnd;
            private String clickUrl;
            private String category;
            private String logo;
            private String icon;
            private String campaignYn;
            private String rewardYn;
            private String pcYn;
            private String mobileYn;
            private String aodYn;
            private String iosYn;
            private int returnDay;
            private String commissionSendYn;
            private String whenTrans;
            private String transReposition;
            private String commissionPaymentStandard;
            private String denyAd;
            private String denyProduct;
            private String notice;
            private String campaignStatus;
            private String apiType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<CpsCampaignDto> {}


        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignSearchRequest {
            private int campaignNum;
            private String memberId;
            private String managerId;
            private String campaignName;
            private String campaignStart;
            private String campaignEnd;
            private String clickUrl;
            private String category;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignSearchResponse extends GenericBaseResponse<CpsCampaignEntity> {}

    }
}
