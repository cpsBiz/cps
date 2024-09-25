package com.cps.common.constant;

public enum Constants {
//API 통신, NULL 1000
//AGENCY 2000
//MEMBER 3000
//CLICK 4000
//CAMPAIGN 5000
//CAMPAIGN_CATEGORY 5100
//매체 캠페인 승인 5200
//카테고리 7000
//노출 8000
//노출통계 8100
//도트피치 리워드 9000

    API_EXCEPTION("1000", "API_EXCEPTION"),

    AGENCY_EXCEPTION("2000", "광고주 대행사 등록 오류"),
    AGENCY_BLANK("2001", "존재하지 않은 Aagency 입니다."),
    AGENCY_NOT_SEARCH("2010", "등록 할 광고주가 없습니다."),

    MEMBER_EXCEPTION("3000", "회원정보 등록 오류"),
    MEMBER_BLANK("3001", "회원정보가 없습니다."),
    MEMBER_UPDATE_EXCEPTION("3002", "회원정보 수정 오류"),
    MEMBER_DELETE_EXCEPTION("3003", "회원정보 삭제 오류"),
    MEMBER_DUPLICATION("3004", "등록되어 있는 회원 입니다."),
    MEMBER_SEARCH_EXCEPTION("3005", "회원 조회 오류"),

    CLICK_EXCEPTION("4000", "클릭 캠페인 등록 오류"),
    CLICK_PFCODE_EXCEPTION("4011", "PF_CODE 오류"),

    CAMPAIGN_EXCEPTION("5000", "캠페인정보 등록 오류"),
    CAMPAIGN_BLANK("5001", "캠페인정보가 없습니다."),
    CAMPAIGN_UPDATE_EXCEPTION("5002", "캠페인정보 수정 오류"),
    CAMPAIGN_DELETE_EXCEPTION("5003", "캠페인정보 삭제 오류"),
    CAMPAIGN_DUPLICATION("5004", "등록되어 있는 캠페인 입니다."),
    CAMPAIGN_SEARCH_EXCEPTION("5005", "캠페인 조회 오류"),

    CAMPAIGN_CATEGORY_EXCEPTION("5100", "캠페인 카테고리 등록 오류"),
    CAMPAIGN_CATEGORY_BLANK("5101", "캠페인 카테고리 정보가 없습니다."),
    CAMPAIGN_CATEGORY_SEARCH_EXCEPTION("5105", "캠페인 카테고리 조회 오류"),

    AFFILIATE_CAMPAIGN_EXCEPTION("5200", "매체 캠페인 승인 등록 오류"),

    CATEGORY_EXCEPTION("7000", "카테고리 등록 오류"),

    VIEW_EXCEPTION("8000", "실적 데이터 등록 오류"),
    VIEW_BLANK("8001", "실적 캠페인 정보가 없습니다."),
    VIEW_SEARCH_EXCEPTION("8005", "실적 조회 오류"),

    STAT_HOUR_EXCEPTION("8100", "노출 통계 데이터 등록 오류"),
    STAT_HOUR_BLANK("8101", "노출 통계 데이터 정보가 없습니다."),

    DOTPITCH_EXCEPTION("9000", "도트피치 리워드 데이터 등록 오류"),
    DOTPITCH_BLANK("9001", "도트피치 정보가 없습니다."),
    DOTPITCH_NOT_SEARCH("9010", "등록 할 도트피치 정보가 없습니다."),

    LINKPRICE_EXCEPTION("9100", "링크프라이스 리워드 데이터 등록 오류"),
    LINKPRICE_BLANK("9101", "링크프라이스 정보가 없습니다."),
    LINKPRICE_NOT_SEARCH("9110", "등록 할 링크프라이스 정보가 없습니다.");

    private String code;
    private String value;

    Constants(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
