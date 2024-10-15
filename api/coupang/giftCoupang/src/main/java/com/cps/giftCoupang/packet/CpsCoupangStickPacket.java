package com.cps.giftCoupang.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.UnitDto;
import com.cps.cpsService.dto.UnitListDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 쿠팡 막대사탕
 * @date 2024-10-15
 */

@Data
public class CpsCoupangStickPacket {

    public static class CoupangStickInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CoupangStickRequest {
            private String userId;
            private String merchantId;
            private String affliateId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CoupangStickListRequest extends CoupangStickRequest {
            private int regYm;
            private int status;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CoupangStickListResponse extends GenericBaseResponse<UnitListDto> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CoupangStickResponse extends GenericBaseResponse<UnitDto> {}
    }
}
