package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.common.model.GenericPageBaseResponse;
import com.cps.cpsService.dto.CpsCampaignCategoryDto;
import com.cps.cpsService.dto.CpsCampaignDto;
import com.cps.cpsService.dto.CpsCampaignSearchDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
            @NotBlank(message = "memberId 확인")
            private String memberId;
            @NotBlank(message = "agencyId 확인")
            private String agencyId;
            private String campaignName;
            private String campaignStart;
            private String campaignEnd;
            private String url;
            private String clickUrl;
            private String category;
            private String logo;
            private String icon;
            private String campaignAuto;
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
            private String agencyId;
            private String campaignName;
            private String campaignStart;
            private String campaignEnd;
            private String clickUrl;
            private String category;
            private int page;
            private int size;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignSearchResponse extends GenericPageBaseResponse<CpsCampaignSearchDto> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignCategoryListRequest {
            List<CampaignCategoryRequest> campaignCategoryList;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignCategoryRequest {
            private int campaignNum;
            private String campaignName;
            @NotBlank(message = "category 확인")
            private String category;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignCategoryResponse extends GenericBaseResponse<CpsCampaignCategoryDto> {}
    }
}
