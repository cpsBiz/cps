package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.GoodsEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName      : com.mobcomms.raising.dto
 * fileName         : GoodsDto
 * author           : shchoi3
 * date             : 2024-06-19
 * description
 * ======================================================
 * DATE             AUTHOR              NOTE
 * ------------------------------------------------------
 * 2024-06-19          shchoi3         최초 생성
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodsDto {
    @Schema(description = "상품고유번호")
    private Long goodsSeq;
    @Schema(description = "가격")
    private int price;
    @Schema(description = "난이도")
    private String difficulty;
    @Schema(description = "남은수량")
    private int count;
    @Schema(description = "상품이미지")
    private String goodsImage;
    @Schema(description = "상품명")
    private String goodsName;
    @Schema(description = "상품설명")
    private String description;

    public static GoodsDto of(GoodsEntity entity) {
        return  entity != null
                ? new GoodsDto(
                    entity.getId(),
                    entity.getPrice(),
                    entity.getDifficulty(),
                    entity.getCount(),
                    entity.getGoodsImage(),
                    entity.getGoodsName(),
                    entity.getDescription()) : null;
    }
}
