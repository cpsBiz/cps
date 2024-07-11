package com.mobcomms.shinhan.dto.packet;

import com.mobcomms.common.model.BaseRequset;
import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.shinhan.dto.PointBannerInfoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
public class PointBannerPacket {

    public static class GetPointBannerInfo {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request extends BaseRequset {
            @NotBlank(message = "userKey params is wrong")
            private String userKey;
            private String os;
            private String zoneId;
            @NotBlank(message = "adid params is wrong")
            private String adid;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<PointBannerInfoDto> {

        }

    }

    public static class GetSadariAdInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request extends BaseRequset {
            @NotBlank(message = "userKey params is wrong")
            private String userKey;
            private String os;
            @NotBlank(message = "adid params is wrong")
            private String adid;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<PointBannerInfoDto> {

        }
    }

    public static class PostUserInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request extends BaseRequset{
            @NotBlank(message = "userKey params is wrong")
            private String userKey;
            private String os;
            private String zoneId;
            private String adid;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends BaseResponse {

        }
    }

    public static class PostPoint {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request extends BaseRequset{
            @NotBlank(message = "userKey params is wrong")
            private String userKey;
            @NotBlank(message = "userKey params is wrong")
            private String zoneId;
            private String adUrl;
            private String os;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends BaseResponse{

        }
    }

}
