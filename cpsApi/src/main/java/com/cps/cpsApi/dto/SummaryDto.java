package com.cps.cpsApi.dto;

import lombok.Data;

@Data
public class SummaryDto {
    private String searchType;
    private int cnt;
    private int clickCnt;

    private int campaignNum;
    private String campaignName;

    private String searchId;
    private String searchName;

    private String site;

    private int regDay;

    public SummaryDto(int regDay, String searchType, int cnt, int clickCnt) {
        this.regDay = regDay;
        this.searchType = searchType;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
    }
    public SummaryDto(String type, int regDay, String searchType, int cnt, int clickCnt) {
        this.regDay = regDay;
        this.searchType = searchType;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
    }

    public SummaryDto(String searchId, String searchName, String searchType, int cnt, int clickCnt) {
        this.searchId = searchId;
        this.searchName = searchName;
        this.searchType = searchType;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
    }

    public SummaryDto(String site, String searchType, int cnt, int clickCnt) {
        this.site = site;
        this.searchType = searchType;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
    }

    public SummaryDto(int campaignNum, String campaignName, String searchType, int cnt, int clickCnt) {
        this.campaignNum = campaignNum;
        this.campaignName = campaignName;
        this.searchType = searchType;
        this.cnt = cnt;
        this.clickCnt = clickCnt;
    }

    public SummaryDto() {
    }
}