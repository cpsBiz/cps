package com.cps.finnq.dto.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.finnq.dto.OfferwallInfoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * Created by enliple
 * Create Date : 2024-08-26
 * Class 설명, method
 */
@Data
public class OfferwallPacket {
    public static class PostOfferwall {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request {
            @NotBlank(message = "userKey params is wrong")
            private String userKey;
            private String participationSeq;
            private int userPoint;
            private String adverName;
            private String missionType;
            private String adInfo;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response {
            private boolean Result;
            private int ResultCode;
            private String ResultMsg;
        }
    }



    public static class GetOfferwall {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<OfferwallInfoDto> {

        }
    }

}
