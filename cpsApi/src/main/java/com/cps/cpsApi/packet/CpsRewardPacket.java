package com.cps.cpsApi.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.DotPitchRewardDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 리워드
 * @date 2024-09-23
 */

@Data
public class CpsRewardPacket {

    public static class RewardInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class DotPitchRequest {
            private String log_id ;
            private String log_pw;
            @NotBlank(message = "search_date 확인")
            private String search_date;
            private String search_type;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class DotPitchResponse extends GenericBaseResponse<DotPitchRewardDto> {}

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DotPitchListResponse{
            @JsonProperty("searchDate")
            private String searchDate;
            @JsonProperty("result")
            private String result;
            @JsonProperty("listData")
            private List<DotData> listData;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DotData {
            @JsonProperty("orderid")
            String orderid;
            @JsonProperty("m_name")
            String m_name;
            @JsonProperty("aff_id")
            String aff_id;
            @JsonProperty("p_name")
            String p_name;
            @JsonProperty("quantity")
            String quantity;
            @JsonProperty("price")
            String price;
            @JsonProperty("comm")
            int comm;
            @JsonProperty("commission_rate")
            String commission_rate;
            @JsonProperty("a_info")
            String a_info;
            @JsonProperty("order_flag")
            String order_flag;
        }
    }
}
