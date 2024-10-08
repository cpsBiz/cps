package com.cps.clickCoupang.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.ClickDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 회원 가입
 * @date 2024-09-03
 */

@Data
public class CpsClickCoupangClickPacket {

    public static class CoupangClickInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CoupangClickRequest {
            private String lpttag;
            private String subid;
            private String subparam;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CoupangClickResponse extends GenericBaseResponse<ClickDto> {}
    }
}
