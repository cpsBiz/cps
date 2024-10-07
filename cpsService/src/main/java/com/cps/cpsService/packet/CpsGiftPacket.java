package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsGiftDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 캠페인 카테고리
 * @date 2024-09-10
 */

@Data
public class CpsGiftPacket {

    public static class GiftInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftRequest {
            private String brandId;
            private String memberId;
            private String apiType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftBrandRequest extends GiftRequest{
            private String brandName;
            private int minCnt;
            private String brandYn;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftBrandResponse extends GenericBaseResponse<CpsGiftDto.GiftBrandResponse> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftProductRequest extends GiftRequest{
            private String productId;
            private String productName;
            private String productYn;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftProductResponse extends GenericBaseResponse<CpsGiftDto.GiftProductResponse> {}
    }
}
