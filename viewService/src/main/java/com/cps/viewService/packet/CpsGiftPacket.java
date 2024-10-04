package com.cps.viewService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.viewService.dto.CpsGiftDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 캠페인 즐겨 찾기
 * @date 2024-10-01
 */

@Data
public class CpsGiftPacket {

    public static class GiftInfo {
        private String memberId;

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftBrandRequest {
            private String userId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftProductRequest extends GiftBrandRequest{
            private String brandId;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftBrandResponse extends GenericBaseResponse<CpsGiftDto> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftProductResponse extends GenericBaseResponse<CpsGiftDto.GiftProductResponse> {}
    }
}
