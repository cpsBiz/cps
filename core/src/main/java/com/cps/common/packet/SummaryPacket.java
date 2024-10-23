package com.cps.common.packet;

import com.cps.common.model.GenericReportPageBaseResponse;
import com.cps.common.dto.SummaryDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 노출 등록
 * @date 2024-09-11
 */

@Data
public class SummaryPacket {

    public static class SummaryInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class SummaryRequest {
            @NotBlank(message = "dayType 확인")
            private String dayType;

            private int regStart; //검색 시작일자 or 시작년월
            private int regEnd; //검색 종료일자 or 종료년월

            @NotBlank(message = "searchType 확인")
            private String searchType; //일반 검색 구분 (일별 DAY, 월별 MON, 광고주 MEMBER, 캠페인 CAMPAIGN, 사이트 SITE, 광고주 대행사 AGC, 매체 대행사 AGC2, 매체 : AFL)
            private String os; //영역 검색 구분
            private String cancelYn;  //상태 검색 구분

            private String keywordType;
            private String keyword;

            private String type;
            private String searchId;

            private int page;
            private int size;
            private String orderBy;
            private String orderByName;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class SummaryResponse extends GenericReportPageBaseResponse<SummaryDto> {}
    }
}
