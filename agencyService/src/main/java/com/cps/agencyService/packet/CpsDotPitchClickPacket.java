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
public class CpsDotPitchClickPacket {

    public static class DotPitchClickInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class DotPitchClickRequest {
            private String pf_code;
            private String keyid;
            private String turl;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class DotPitchClickResponse extends GenericBaseResponse<ClickDto> {}
    }
}
