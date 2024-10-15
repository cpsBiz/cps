package com.cps.cpsService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDto {
    Long cnt;
    Long stockCnt;

    public Long getCnt() {
        return cnt != null ? cnt : 0L;
    }

    public Long getStockCnt() {
        return stockCnt != null ? stockCnt : 0L;
    }
}

