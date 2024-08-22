package com.mobcomms.hanapay.dto.packet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/*
 * Created by enliple
 * Create Date : 2024-08-16
 * Class 설명, method
 * UpdateDate : 2024-08-16
 */
public class HanaPacket {

    public static class GetHanaInfo {

        @Data
        public static class Request {
            private String Adv_enpc;
            private String Adv_enc_nm;
            private String usn;
            private String quantity;
            private String campaign_key;
            private String reward_key;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Response {
            @JsonProperty("DATA")
            private ResultList data;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResultList{
        @JsonProperty("resultList")
        private List<Result> resultList;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result{
        @JsonProperty("ResultMsg")
        private String resultMsg;
        @JsonProperty("ResultCode")
        private String resultCode;
    }
}
