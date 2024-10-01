package com.cps.cpsApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpsCategoryDto {
    String category;
    String categoryName;
    int categoryRank;
    long campaignCnt;
}

