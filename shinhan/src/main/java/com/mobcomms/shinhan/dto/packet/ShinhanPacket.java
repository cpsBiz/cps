package com.mobcomms.shinhan.dto.packet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
 * Created by enliple
 * Create Date : 2024-07-04
 * Class 설명, method
 * UpdateDate : 2024-07-04, 업데이트 내용
 */
public class ShinhanPacket {

    public static class SendShinhan {
        @Data
        public static class Request {
            private Header dataHeader;
            private Body dataBody;
        }

        @Data
        public static class Response {
            @JsonProperty("dataHeader")
            private ResponseHeader dataHeader;
            @JsonProperty("dataBody")
            private ResponseBody dataBody;
        }
    }

    @Data
    public static class Header {
        private String reqKey;
    }

    @Data
    public static class Body {
        private String ecrClnn;
        private String afoCVl;
        private String pnt;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseHeader {
        @JsonProperty("successCode")
        private String successCode;
        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("resultMessage")
        private String resultMessage;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseBody {
        @JsonProperty("ecrClnn")
        private String ecrClnn;
        @JsonProperty("afoCVl")
        private String afoCVl;
        @JsonProperty("pnt")
        private String pnt;
        @JsonProperty("rcd")
        private String rcd;
    }
}
