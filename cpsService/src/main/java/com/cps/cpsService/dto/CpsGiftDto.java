package com.cps.cpsService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CpsGiftDto {
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftDtoResponse {
        String brandId;
        String merchantId;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftBrandResponse extends GiftDtoResponse {
        String brandName;
        int minCnt;
        String brandYn;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftProductResponse extends GiftDtoResponse {
        String productId;
        String productName;
        String productYn;
    }
}

