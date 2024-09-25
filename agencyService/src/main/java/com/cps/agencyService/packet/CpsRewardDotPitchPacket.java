package com.cps.agencyService.packet;

import com.cps.agencyService.dto.RewardDto;
import com.cps.common.model.GenericBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 도트피치 실시간 적립
 * @date 2024-09-23
 */

@Data
public class CpsRewardDotPitchPacket {

    public static class RewardInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class RealTimeRequest {
            @NotBlank(message = "R_Keyid 확인")
            @JsonProperty("R_Keyid")
            private String R_Keyid;
            @NotBlank(message = "R_Ordid 확인")
            @JsonProperty("R_Ordid")
            private String R_Ordid;
            @NotBlank(message = "R_Date 확인")
            @JsonProperty("R_Date")
            private String R_Date;
            @NotBlank(message = "R_Gubun 확인")
            @JsonProperty("R_Gubun")
            private String R_Gubun;
            @JsonProperty("R_Mid")
            private String R_Mid;
            @JsonProperty("R_Aid")
            private String R_Aid;
            @JsonProperty("R_ProdNm")
            private String R_ProdNm;
            @JsonProperty("R_Quantity")
            private int R_Quantity;
            @JsonProperty("R_OrdPrice")
            private int R_OrdPrice;
            @JsonProperty("R_CommRate")
            private int R_CommRate;
            @JsonProperty("CommRate")
            private float CommRate;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class RewardResponse extends GenericBaseResponse<RewardDto> {}
    }
}
