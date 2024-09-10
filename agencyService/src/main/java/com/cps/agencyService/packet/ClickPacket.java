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
public class ClickPacket {

    public static class ClickInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ClickRequest {
            @NotBlank(message = "campaignNum 확인")
            @Size(max=20, message="20자 이내로 입력")
            private String campaignNum;
            @NotBlank(message = "memberId 확인")
            private String memberId;
            @NotBlank(message = "affliateId 확인")
            private String affliateId;
            @NotBlank(message = "zoneId 확인")
            private String zoneId;
            @NotBlank(message = "userId 확인")
            private String userId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<ClickDto> {}
    }
}
