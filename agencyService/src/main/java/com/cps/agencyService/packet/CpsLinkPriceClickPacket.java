package com.cps.agencyService.packet;

import com.cps.agencyService.dto.ClickDto;
import com.cps.common.model.GenericBaseResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 회원 가입
 * @date 2024-09-03
 */

@Data
public class CpsLinkPriceClickPacket {

    public static class LinkPriceClickInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class LinkPriceClickRequest {
            private String m;
            private String a;
            private String l;
            private String u_id;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CpsLinkPriceResponse extends GenericBaseResponse<ClickDto> {}
    }
}
