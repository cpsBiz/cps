package com.mobcomms.sample.model;

import com.mobcomms.common.model.BaseRequset;
import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;


public class PayboocPacket{

    //TODO Config 에서 값을 설정하도록
    public static final String DOMAIN = "Https://paybooc-api.commsad.com/api/v1/";

    public static final String GET_USERINFO_ENDPOINT = "userinfo";

    public static final String POST_USERINFO_ENDPOINT = "userinfo";

    public static class GetUserInfo{
        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Requset extends BaseRequset {
            private String user_key;
        }

        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Response  extends BaseResponse {
            private String result_message;
            private String result_code;
        }
    }

    public static class PostUserInfo{
        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Requset extends BaseRequset {
            private String adid;
            private String os;
            private String user_key;
            private String agree_terms;
        }

        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Response  extends GenericBaseResponse<UserInfo> {
            private String result_message;
            private String result_code;
        }
    }

    public static class Postpoint{
        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Requset extends BaseRequset {
            private String user_key;
            private String point;
            private String ad_id;
        }

        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Response  extends BaseResponse {
            private String result_message;
            private String result_code;
        }

    }
}
