package com.cps.common.constant;

public enum Constants {
    API_EXCEPTION("1000", "API_EXCEPTION"),

    AGENCY_EXCEPTION("3000", "광고주 대행사 등록 오류"),
    AGENCY_BLANK("3001", "존재하지 않은 Aagency 입니다."),
    AGENCY_NOT_SEARCH("3010", "등록 할 광고주가 없습니다."),

    MEMBER_EXCEPTION("3000", "회원 정보 등록 오류"),
    MEMBER_BLANK("3001", "회원 정보가 없습니다."),
    MEMBER_UPDATE_EXCEPTION("3002", "회원 정보 수정 오류"),
    MEMBER_DELETE_EXCEPTION("3003", "회원 정보 삭제 오류"),
    MEMBER_DUPLICATION("3004", "등록되어 있는 회원 입니다."),
    MEMBER_SEARCH_EXCEPTION("3005", "회원 조회 오류"),

    CLICK_EXCEPTION("3000", "클릭 캠페인 등록 오류"),
    CLICK_PFCODE_EXCEPTION("3011", "PF_CODE 오류"),

    CAMPAIGN_EXCEPTION("3000", "캠페인 정보 등록 오류"),
    CAMPAIGN_BLANK("3001", "캠페인 정보가 없습니다."),
    CAMPAIGN_UPDATE_EXCEPTION("3002", "캠페인 정보 수정 오류"),
    CAMPAIGN_DELETE_EXCEPTION("3003", "캠페인 정보 삭제 오류"),
    CAMPAIGN_DUPLICATION("3004", "등록되어 있는 캠페인 입니다."),
    CAMPAIGN_SEARCH_EXCEPTION("3005", "캠페인 조회 오류"),

    CAMPAIGN_CATEGORY_EXCEPTION("3100", "캠페인 카테고리 등록 오류"),
    CAMPAIGN_CATEGORY_BLANK("3101", "캠페인 카테고리 정보가 없습니다."),
    CAMPAIGN_CATEGORY_SEARCH_EXCEPTION("3105", "캠페인 카테고리 조회 오류"),

    AFFILIATE_CAMPAIGN_EXCEPTION("3200", "매체 캠페인 승인 등록 오류"),

    CATEGORY_EXCEPTION("3000", "카테고리 등록 오류"),
    CATEGORY_BLANK("3001", "카테고리 정보가 없습니다."),
    CATEGORY_DUPLICATION("3004", "등록되어 있는 카테고리 입니다."),
    CATEGORY_SEARCH_EXCEPTION("3005", "카테고리 조회 오류"),

    VIEW_EXCEPTION("3000", "실적 데이터 등록 오류"),
    VIEW_BLANK("3001", "실적 캠페인 정보가 없습니다."),
    VIEW_SEARCH_EXCEPTION("3005", "실적 조회 오류"),

    STAT_HOUR_EXCEPTION("3100", "노출 통계 데이터 등록 오류"),
    STAT_HOUR_BLANK("3101", "노출 통계 데이터 정보가 없습니다."),

    DOTPITCH_EXCEPTION("3000", "도트피치 리워드 데이터 등록 오류"),
    DOTPITCH_BLANK("3001", "도트피치 정보가 없습니다."),
    DOTPITCH_NOT_SEARCH("3010", "등록 할 도트피치 정보가 없습니다."),

    LINKPRICE_EXCEPTION("3100", "링크프라이스 리워드 데이터 등록 오류"),
    LINKPRICE_BLANK("3101", "링크프라이스 정보가 없습니다."),
    LINKPRICE_NOT_SEARCH("3110", "등록 할 링크프라이스 정보가 없습니다."),

    INQUIRY_EXCEPTION("3200", "1:1 문의 등록 오류"),
    INQUIRY_BLANK("3201", "1:1 문의 정보가 없습니다."),
    INQUIRY_SEARCH_EXCEPTION("3205", "1:1 문의 조회 오류"),

    ANSWER_EXCEPTION("3300", "1:1 문의 답변 등록 오류"),
    ANSWER_BLANK("3301", "1:1 문의 답변  정보가 없습니다."),
    ANSWER_SEARCH_EXCEPTION("3305", "1:1 문의 답변 조회 오류"),

    FAVORITES_EXCEPTION("3400", "캠페인 즐겨찾기 등록 오류"),
    FAVORITES_DELETE_EXCEPTION("3403", "캠페인 즐겨찾기 삭제 오류"),

    MEMBER_COMMISSION_BLANK("3001", "적립 정보가 없습니다."),
    MEMBER_COMMISSION_SEARCH_EXCEPTION("3005", "적립 조회 오류"),

    GIFT_BRAND_EXCEPTION("3100", "기프트 브랜드 등록 오류"),
    GIFT_BRAND_BLANK("3101", "브랜드 정보가 없습니다."),
    GIFT_BRAND_UPDATE_EXCEPTION("3002", "기프트 브랜드  정보 수정 오류"),
    GIFT_BRAND_DELETE_EXCEPTION("3003", "기프트 브랜드  정보 삭제 오류"),
    GIFT_BRAND_SEARCH_EXCEPTION("3105", "기프트 브랜드 조회 오류"),

    GIFT_PRODUCT_EXCEPTION("3100", "기프트 상품 등록 오류"),
    GIFT_PRODUCT_BLANK("3101", "상품 정보가 없습니다."),
    GIFT_PRODUCT_UPDATE_EXCEPTION("3002", "기프트 상품 정보 수정 오류"),
    GIFT_PRODUCT_DELETE_EXCEPTION("3003", "기프트 상품 정보 삭제 오류"),
    GIFT_PRODUCT_SEARCH_EXCEPTION("3105", "기프트 상품 조회 오류");

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
