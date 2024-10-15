package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsCampaignCommissionDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

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
            @NotBlank(message = "affliateId 확인")
            private String affliateId;
            private String status;
            private int memberCommissionShare;
            private int userCommissionShare;
            private BigDecimal pointRate;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<CpsCampaignCommissionDto> {}
    }
}
