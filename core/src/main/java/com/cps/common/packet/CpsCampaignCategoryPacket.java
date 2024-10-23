package com.cps.common.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.common.dto.CpsCampaignCategoryDto;
import com.cps.common.entity.CpsCampaignCategoryEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 캠페인 카테고리
 * @date 2024-09-10
 */

@Data
public class CpsCampaignCategoryPacket {

    public static class CampaignCategoryInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignCategoryRequest {
            private int categoryNum;
            private int campaignNum;
            @NotBlank(message = "category 확인")
            private String category;
            private int rs;
            @NotBlank(message = "denyYn 확인")
            private String denyYn;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<CpsCampaignCategoryDto> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignCategorySearchRequest {
            private int campaignNum;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignCategorySearchResponse extends GenericBaseResponse<CpsCampaignCategoryEntity> {}

    }
}
