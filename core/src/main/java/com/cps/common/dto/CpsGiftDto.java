package com.cps.common.dto;

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
        //상품 등록 수정 삭제
        String productId;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftProbabilityResponse extends GiftProductResponse {
        //상품당첨
        String userId;
        String productImageS;
        String productImageL;
        String brandIcon;
    }
}
