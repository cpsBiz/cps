package com.cps.finnq.dto.packet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.cps.common.model.BaseRequset;
import com.cps.common.model.GenericBaseResponse;
import com.cps.finnq.dto.PointBoxDto;
import com.cps.finnq.dto.PointInfoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * Created by enliple
 * Create Date : 2024-08-26
 * Class 설명, method
 */
@Data
public class PointPacket {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class PointPacketBaseRequset extends BaseRequset {
        @NotBlank(message = "userKey params is wrong")
        private String userKey;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends GenericBaseResponse<PointBoxDto> {

    }

    public static class PostPoint {
        @Data
        @EqualsAndHashCode(callSuper = false)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PostPointRequest extends PointPacketBaseRequset {
            @NotBlank(message = "adName params is wrong")
            private String adName;
            @NotBlank(message = "anickAdId params is wrong")
            private String anickAdId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<PointBoxDto> {

        }
    }

    public static class GetPointSetting {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<PointInfoDto> {

        }
    }
}