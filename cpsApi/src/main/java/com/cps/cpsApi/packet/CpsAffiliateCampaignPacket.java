package com.cps.cpsApi.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsAffiliateCampaignDto;
import com.cps.cpsApi.entity.CpsViewEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 노출 등록
 * @date 2024-09-11
 */

@Data
public class CpsAffiliateCampaignPacket {

    public static class CpsAffiliateCampaignInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CpsAffiliateCampaignRequest {
            @NotBlank(message = "affiliateId 확인")
            private String affiliateId;
            @NotBlank(message = "memberId 확인")
            private String memberId;
            private int campaignNum;
            private String status;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<CpsAffiliateCampaignDto> {}
    }
}
