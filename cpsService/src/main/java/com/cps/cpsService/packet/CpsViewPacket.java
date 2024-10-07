package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsViewDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 노출 등록
 * @date 2024-09-11
 */

@Data
public class CpsViewPacket {

    public static class ViewInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewRequest {
            private String affliateId;
            private String zoneId;
            private String site;
            private String userId;
            private String adId;
            private String os;
            private String category;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewResponse extends GenericBaseResponse<CpsViewDto> {}



        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewScheduleRequest {
            private int minute;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewSchduelResponse extends GenericBaseResponse<CpsViewDto> {}
    }
}
