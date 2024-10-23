package com.cps.common.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.common.dto.CpsGiftDto;
import com.cps.common.dto.UnitDto;
import com.cps.common.dto.UnitListDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 쿠팡 막대사탕
 * @date 2024-10-15
 */

@Data
public class CoupangStickPacket {

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
        public static class CoupangStickGiftRequest extends CoupangStickRequest {
            private String brandId;
            private int cnt;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CoupangGiftResponse extends GenericBaseResponse<CpsGiftDto.GiftProductResponse> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        //막대사탕 리스트용
        public static class CoupangStickListResponse extends GenericBaseResponse<UnitListDto> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        //막대사탕 개수용
        public static class CoupangStickResponse extends GenericBaseResponse<UnitDto> {}
    }
}
