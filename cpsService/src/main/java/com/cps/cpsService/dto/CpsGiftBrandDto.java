package com.cps.cpsService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CpsGiftBrandDto {
    String brandId;
    String brandName;
    String brandLogo;
    int minCnt;
}

