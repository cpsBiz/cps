package com.cps.common.constant;

public enum Constants {
//API 통신, NULL 1000번대
//AGENCY 2000번대
//MEMBER 3000번대
//CLICK 4000번대
//카테고리 7000번대

    API_EXCEPTION("1000", "API_EXCEPTION"),

    AGENCY_EXCEPTION("2000", "광고주 대행사 등록 오류"),
    AGENCY_BLANK("2001", "존재하지 않은 Aagency 입니다."),

    MEMBER_EXCEPTION("3000", "회원정보 등록 오류"),
    MEMBER_BLANK("3001", "회원정보가 없습니다."),
    MEMBER_UPDATE_EXCEPTION("3002", "회원정보 수정 오류"),
    MEMBER_DELETE_EXCEPTION("3003", "회원정보 삭제 오류"),
    MEMBER_DUPLICATION("3004", "등록되어 있는 회원정보 입니다."),
    MEMBER_LIST_EXCEPTION("3005", "회원 조회 오류"),

    CLICK_EXCEPTION("4000", "클릭 캠페인 등록 오류"),

    CATEGORY_EXCEPTION("7000", "카테고리 등록 오류");

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
