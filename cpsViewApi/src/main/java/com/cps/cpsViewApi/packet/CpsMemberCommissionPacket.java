package com.cps.cpsViewApi.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsViewApi.dto.CpsMemberCommissionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 회원 적립금 조회
 * @date 2024-10-02
 */

@Data
public class CpsMemberCommissionPacket {

    public static class MemberCommissionInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class MemberCommissionRequest {
            private String userId;
            private String affliateId;
            private int regYm;
            private int status;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class MemberCommissionResponse extends GenericBaseResponse<CpsMemberCommissionDto.MemberCommissionList> {}
    }
}
