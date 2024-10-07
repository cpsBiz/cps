package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.DotPitchRewardDto;
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
        public static class LinkPriceRequest {
            @NotBlank(message = "a_id 확인")
            private String a_id;
            @NotBlank(message = "auth_key 확인")
            private String auth_key;
            @NotBlank(message = "yyyymmdd 확인")
            private String yyyymmdd;
            private String cancel_flag;
            private int page;
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

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class LinkPriceListResponse{
            @JsonProperty("result")
            private String result;
            @JsonProperty("list_count")
            private String list_count;
            @JsonProperty("listData")
            private List<LinkData> order_list;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class LinkData {
            @JsonProperty("trlog_id")
            String trlog_id;
            @JsonProperty("m_id")
            String m_id;
            @JsonProperty("o_cd")
            String o_cd;
            @JsonProperty("p_cd")
            String p_cd;
            @JsonProperty("p_nm")
            String p_nm;
            @JsonProperty("it_cnt")
            String it_cnt;
            @JsonProperty("user_id")
            int user_id;
            @JsonProperty("status")
            int status;
            @JsonProperty("c_cd")
            String c_cd;
            @JsonProperty("create_time_stamp")
            String create_time_stamp;
            @JsonProperty("applied_pgm_id")
            String applied_pgm_id;
            @JsonProperty("yyyymmdd")
            String yyyymmdd;
            @JsonProperty("hhmiss")
            String hhmiss;
            @JsonProperty("trans_comment")
            String trans_comment;
            @JsonProperty("sales")
            String sales;
            @JsonProperty("commission")
            int commission;
            @JsonProperty("pgm_name")
            String pgm_name;
            @JsonProperty("is_pc")
            String is_pc;
            @JsonProperty("pur_rate")
            String pur_rate;
        }
    }
}
