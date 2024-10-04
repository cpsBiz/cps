package com.cps.viewService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CpsGiftDto {
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftDtoResponse {
        String brandId;
        String memberId;
        int minCnt;
        int balanceCnt;
        String brandName;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftProductResponse extends GiftDtoResponse {
        String productId;
        String productName;
        String productYn;
    }
}

