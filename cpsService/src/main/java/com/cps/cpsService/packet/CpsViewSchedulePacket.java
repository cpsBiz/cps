package com.cps.cpsService.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsViewrDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 노출 등록
 * @date 2024-09-11
 */

@Data
public class CpsViewSchedulePacket {

    public static class ScheduleInfo {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewScheduleRequest {
            private int dayMinute;
            private int hourMinute;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewSchduelResponse extends GenericBaseResponse<CpsViewrDto> {}


        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewScheduleMonthRequest {
            private int searchDay;
            private int serchHour;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewSchduelMonthResponse extends GenericBaseResponse<CpsViewrDto> {}
    }
}
