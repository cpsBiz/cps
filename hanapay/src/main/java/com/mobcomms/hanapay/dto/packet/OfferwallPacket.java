package com.mobcomms.hanapay.dto.packet;

import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.hanapay.dto.OfferwallInfoDto;
import com.mobcomms.hanapay.dto.PointInfoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * Created by enliple
 * Create Date : 2024-08-19
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
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<OfferwallInfoDto> {}
    }



    public static class GetOfferwall {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<OfferwallInfoDto> {

        }
    }

}
