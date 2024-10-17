package com.cps.cpsService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpsGiftHistoryDto {
    String productId;
    String barcode;
    String pinNo;
    int awardDay;
    int validDay;
    String giftYn;
    String brandIcon;
    String brandName;
    String productImageS;
    String productImageL;
    String productName;
    String content;
}

