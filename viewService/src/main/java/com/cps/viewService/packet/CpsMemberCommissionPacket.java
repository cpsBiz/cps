package com.cps.viewService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.viewService.dto.CpsMemberCommissionDto;
import com.cps.viewService.dto.CpsMemberCommissionListDto;
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
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class MemberCommissionListRequest extends MemberCommissionRequest {
            private int regYm;
            private int status;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class MemberCommissionListResponse extends GenericBaseResponse<CpsMemberCommissionListDto.MemberCommissionList> {}


        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class MemberCommissionResponse extends GenericBaseResponse<CpsMemberCommissionDto> {}
    }
}
