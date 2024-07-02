package com.mobcomms.raising.dto.packet;

/*
 * Created by shchoi3
 * Create Date : 2024-06-20
 * class 설명, method
 * UpdateDate : 2024-06-20, 업데이트 내용
 */

import com.mobcomms.common.model.BaseRequset;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.raising.dto.MissionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class MissionPacket {

    public static class ReadMission {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request extends BaseRequset {
        }
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<MissionDto> {

        }
    }

}
