package com.cps.common.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.common.dto.ClickDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 회원 가입
 * @date 2024-09-03
 */

@Data
public class CpsClickPacket {

    public static class ClickInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ClickRequest {
            private int campaignNum;
            @NotBlank(message = "affliateId 확인")
            private String affliateId;
            @NotBlank(message = "zoneId 확인")
            private String zoneId;
            @NotBlank(message = "agencyId 확인")
            private String agencyId;
            @NotBlank(message = "merchantId 확인")
            private String merchantId;
            private String type;
            private String site;
            private String clickUrl;
            private String os;
            @NotBlank(message = "userId 확인")
            private String userId;
            private String adId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<ClickDto> {}
    }
}
