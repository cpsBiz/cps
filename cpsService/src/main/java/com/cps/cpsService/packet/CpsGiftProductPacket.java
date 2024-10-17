package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsGiftBrandDto;
import com.cps.cpsService.dto.CpsGiftProductDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 캠페인 카테고리
 * @date 2024-09-10
 */

@Data
public class CpsGiftProductPacket {

    public static class GiftProductInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftProductRequest {
            private String brandId;
            private String affliateId;
            private String merchantId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftProductResponse extends GenericBaseResponse<CpsGiftProductDto> {}

    }
}
