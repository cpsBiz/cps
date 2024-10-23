package com.cps.common.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.common.dto.CpsCampaignFavoritesDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 캠페인 즐겨 찾기
 * @date 2024-10-01
 */

@Data
public class CpsCampaignFavoritesPacket {

    public static class CampaignFavoritesInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignFavoritesRequest {
            private String userId;
            private String affliateId;
            private int campaignNum;
            private String apiType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CampaignFavoritesResponse extends GenericBaseResponse<CpsCampaignFavoritesDto> {}
    }
}
