package com.mobcomms.finnq.dto.packet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.finnq.dto.OfferwallInfoDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/*
 * Created by enliple
 * Create Date : 2024-08-26
 * Class 설명, method
 * UpdateDate : 2024-08-26
 */
public class FinnqPacket {

    public static class GetFinnqInfo {
        @Data
        @Builder
        public static class Request {
            private String trsnKey;
            private String alinCd;
            private String userId;
            private String amt;
            private String adId;
            private String adCode;
            private String adTitle;
            private String adInfo;
            private String hmac;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Response {
            @JsonProperty("rsltCd")
            private String rsltCd;
        }
    }
}
