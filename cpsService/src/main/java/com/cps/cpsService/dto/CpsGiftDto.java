package com.cps.cpsService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CpsGiftDto {
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftDtoResponse {
        String brandId;
        String affliateId;
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
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftProbabilityResponse extends GiftProductResponse {
        //상품당첨
        String userId;
        String productId;
    }
}

