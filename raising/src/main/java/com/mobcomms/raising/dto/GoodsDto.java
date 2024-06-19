package com.mobcomms.raising.dto;

import lombok.Data;

@Data
public class GoodsDto {
    private long goodsSeq;
    private int price;
    private String goodsName;
    private String goodsImage;
    private int consumerPrice;
    private int requiredPoint;
    private String difficulty;
    private int totalCount;
    private int count;
    private String useYn;
}
