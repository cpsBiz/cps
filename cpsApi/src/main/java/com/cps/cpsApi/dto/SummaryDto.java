package com.cps.cpsApi.dto;

import lombok.Data;

@Data
public class SummaryDto {
    private String keywordType;
    private int cnt;
    private int clickCnt;

    private String keyword;
    private String keywordName;

    private int regStart;
    private int regEnd;

    public SummaryDto(int regDay, String keywordType, int cnt, int clickCnt) {
        this.regStart = regDay;
        this.regEnd = regDay;
        this.keywordType = keywordType;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
    }

    public SummaryDto(String searchId, String searchName, String keywordType, int cnt, int clickCnt) {
        this.keyword = searchId;
        this.keywordName = searchName;
        this.keywordType = keywordType;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
    }

    public SummaryDto(int campaignNum, String campaignName, String keywordType, int cnt, int clickCnt) {
        this.keyword = String.valueOf(campaignNum);
        this.keywordName = campaignName;
        this.keywordType = keywordType;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
    }

    public SummaryDto() {
    }
}