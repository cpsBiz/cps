package com.mobcomms.raising.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품정보")
public record GoodsResDto(
        @Schema(description = "상품이미지") String goodsImage,
        @Schema(description = "상품포인트") String requiredPoint
) {
}
