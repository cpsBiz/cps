package com.cps.cpsApi.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsViewrDto;
import com.cps.cpsApi.entity.CpsViewEntity;
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
            private int minute;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class ViewSchduelResponse extends GenericBaseResponse<CpsViewrDto> {}
    }
}
