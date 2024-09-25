package com.cps.agencyService.packet;

import com.cps.agencyService.dto.RewardDto;
import com.cps.common.model.GenericBaseResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 도트피치 실시간 적립
 * @date 2024-09-23
 */

@Data
public class CpsRewardLinkPricePacket {

    public static class RewardInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class RealTimeRequest {
            @NotBlank(message = "day 확인")
            private String day;
            @NotBlank(message = "time 확인")
            private String time;
            @NotBlank(message = "merchant_id 확인")
            private String merchant_id;
            @NotBlank(message = "order_code 확인")
            private String order_code;
            @NotBlank(message = "product_code 확인")
            private String product_code;
            private String product_name;
            private String category_code;
            private int item_count;
            private int price;
            private int commision;
            private String affiliate_id;
            private String affiliate_user_id;
            private String trlog_id;
            private String base_commission;
            private String incentive_commission;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class RewardResponse extends GenericBaseResponse<RewardDto> {}
    }
}
