package com.mobcomms.hanapay.dto.packet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobcomms.common.model.BaseRequset;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.hanapay.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * Created by enliple
 * Create Date : 2024-08-13
 * Class 설명, method
 */
@Data
public class UserPacket {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class UserPacketBaseRequset extends BaseRequset {
        @NotBlank(message = "userKey params is wrong")
        private String userKey;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends GenericBaseResponse<UserDto>{

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateUserAgreeTerms {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public  static class UpdateUserAgreeTermsRequest extends UserPacketBaseRequset{
            private String agreeTerms;
        }
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<UserDto>{

        }

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class PostUserinfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PostUserinfoRequest extends UserPacketBaseRequset {
            @NotBlank(message = "adId params is wrong")
            private String adId;
            private String os;
        }
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<UserDto>{

        }

    }
}
