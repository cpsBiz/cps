package com.cps.rewardCoupang.packet;

import com.cps.cpsService.dto.RewardDto;
import com.cps.common.model.GenericBaseResponse;
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
            private String R_Keyid;
            private String R_Ordid;
            private String R_Date;
            private String R_Gubun;
            private String R_Mid;
            private String R_Aid;
            private String R_ProdNm;
            private int R_Quantity;
            private int R_OrdPrice;
            private float R_CommRate;
            private int R_CommPrice;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class RewardResponse extends GenericBaseResponse<RewardDto> {}
    }
}
