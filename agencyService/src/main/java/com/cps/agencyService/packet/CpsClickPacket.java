package com.cps.agencyService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.agencyService.dto.ClickDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
            @NotBlank(message = "memberId 확인")
            private String memberId;
            private String type;
            private String site;
            private String os;
            @NotBlank(message = "userId 확인")
            private String userId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<ClickDto> {}
    }
}