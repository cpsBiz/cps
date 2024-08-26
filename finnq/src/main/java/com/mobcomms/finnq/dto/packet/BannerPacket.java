package com.mobcomms.finnq.dto.packet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/*
 * Created by enliple
 * Create Date : 2024-08-26
 * Class 설명, method
 */
@Data
public class BannerPacket {
    public static class Banner {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request {
            @NotBlank(message = "userKey params is wrong")
            private String userKey;
            private String type;
            private String os;
            private String adId;
            private String coupangPoint;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class IndexRequest extends Request {
            private String useYn;
            private String mobcommsPoint;
            private String mobcommsUseYn;
            private String coupangUseYn;
            private String subIdAos;
            private String subIdIos;
            private String houseIos;
            private String houseAos;
            private int resPointCnt;
            private String serverType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CoupangRequest extends Request {
            private String mobon;
            private String endPoint;
            private String authorization;
            private String coupangData;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Response {
            @JsonProperty("data")
            private List<Result> data;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Result{
            @JsonProperty("productImage")
            private String productImage;
            @JsonProperty("productUrl")
            private String productUrl;

            public void setProductImage(String productImage) {
                if (productImage != null) {
                    productImage = productImage.replaceAll("\\\\\"", "");
                    productImage = productImage.replaceAll("'", "");
                    productImage = productImage.replaceAll("\n", "\\n");
                }
                this.productImage = productImage;
            }

            public void setProductUrl(String productUrl) {
                if (productUrl != null) {
                    productUrl = productUrl.replaceAll("\\\\\"", "");
                    productUrl = productUrl.replaceAll("'", "");
                    productUrl = productUrl.replaceAll("\n", "\\n");
                }
                this.productUrl = productUrl;
            }
        }
    }

}
