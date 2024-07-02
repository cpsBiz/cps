package com.mobcomms.shinhan.dto.packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
/*
 * Created by enliple
 * Create Date : 2024-06-28
 * Class 설명, method
 * UpdateDate : 2024-06-28, 업데이트 내용
 */
public class CoupangPacket {

    public static class GetCoupangAdInfo{
        @Data
        @EqualsAndHashCode(callSuper=false)
        public  static class Request{

            private String deviceId;
            private String subId;
            private String imageSize;
        }

        @Data
        @EqualsAndHashCode(callSuper=false)
        public  static class Response {
            private String rCode;
            private String rMessage;
            private List<CoupangAdInfoItem> data;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class CoupangAdInfoItem{
        private String productId;
        private String productImage;
        private String productUrl;
    }
}
