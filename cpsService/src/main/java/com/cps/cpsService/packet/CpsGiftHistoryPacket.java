package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsGiftHistoryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 캠페인 카테고리
 * @date 2024-09-10
 */

@Data
public class CpsGiftHistoryPacket {

    public static class GiftHistoryInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftHistoryRequest {
            private String brandId;
            private String userId;
            private String affliateId;
            private String merchantId;
            private String giftYn;
            private int awardYm;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftHistoryResponse extends GenericBaseResponse<CpsGiftHistoryDto> {}

    }
}
