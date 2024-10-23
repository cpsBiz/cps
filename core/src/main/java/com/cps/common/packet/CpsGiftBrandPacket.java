package com.cps.common.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.common.dto.CpsGiftBrandDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 캠페인 카테고리
 * @date 2024-09-10
 */

@Data
public class CpsGiftBrandPacket {

    public static class BrandInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class BradnRequest {
            private String affliateId;
            private String merchantId;
            private String brandType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftBrandResponse extends GenericBaseResponse<CpsGiftBrandDto> {}

    }
}
