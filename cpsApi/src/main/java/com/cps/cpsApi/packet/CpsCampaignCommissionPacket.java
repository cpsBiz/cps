package com.cps.cpsApi.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsCampaignCommissionDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 노출 등록
 * @date 2024-09-11
 */

@Data
public class CpsCampaignCommissionPacket {

    public static class AffiliateCampaignInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class AffiliateCampaignRequest {
            private int campaignNum;
            @NotBlank(message = "affiliateId 확인")
            private String memberId;
            private String status;
            private int memberCommissionShare;
            private int userCommissionShare;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<CpsCampaignCommissionDto> {}
    }
}
