package com.cps.common.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.common.dto.CpsGiftDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 캠페인 카테고리
 * @date 2024-09-10
 */

@Data
public class CpsGiftPacket {

    public static class GiftInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftRequest {
            private String brandId;
            private String affliateId;
            private String merchantId;
            private String apiType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftBrandRequest extends GiftRequest{
            private String brandType;
            private String brandName;
            private String brandLogo;
            private int minCnt;
            private String brandYn;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftBrandResponse extends GenericBaseResponse<CpsGiftDto.GiftBrandResponse> {}

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class GiftProductRequest extends GiftRequest{
            private String productId;
            private String probabilities;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CpsGiftRequest {
            private String brandId;
            private String productId;
            private String brandName;
            private String productName;
            private String productImageS;
            private String productImageL;
            private String brandIcon;
            private int realPrice;
            private int salePrice;
            private String discountRate;
            private int discountPrice;
            private int limitDay;
            private int validDay;
            private int endDay;
            private String content;
            private String apiType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        //상품 등록 수정 삭제, 기프트 상품 등록 수정 삭제
        public static class GiftProductResponse extends GenericBaseResponse<CpsGiftDto.GiftProductResponse> {}

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ShowBizListRequest{
            private String api_code;
            private String custom_auth_code;
            private String custom_auth_token;
            private String dev_yn;
            private String start;
            private String size;
        }

        //기프티쇼 쿠폰
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ShowBizListCouponResponse{
            @JsonProperty("code")
            private String code;
            @JsonProperty("message")
            private String message;
            @JsonProperty("result")
            private ShowBizCouponResult showBizCouponResult;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ShowBizCouponResult{
            @JsonProperty("code")
            private String code;
            @JsonProperty("message")
            private String message;
            @JsonProperty("result")
            private ShowBizCouponData result;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ShowBizCouponData {
            @JsonProperty("orderNo")
            private String orderNo;
            @JsonProperty("pinNo")
            private String pinNo;
            @JsonProperty("couponImgUrl")
            private String couponImgUrl;
        }
        //쿠폰

        //기프티쇼 상품 리스트
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ShowBizListResponse{
            @JsonProperty("code")
            private String code;
            @JsonProperty("message")
            private String message;
            @JsonProperty("result")
            private Result result;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Result {
            @JsonProperty("listNum")
            private int listNum;
            @JsonProperty("goodsList")
            private List<ShowBizData> goodsList;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ShowBizData {
            @JsonProperty("rmIdBuyCntFlagCd")
            private String rmIdBuyCntFlagCd;

            @JsonProperty("discountRate")
            private String discountRate;

            @JsonProperty("mdCode")
            private String mdCode;

            @JsonProperty("endDate")
            private String endDate;

            @JsonProperty("affliateId")
            private String affliateId;

            @JsonProperty("discountPrice")
            private int discountPrice;

            @JsonProperty("mmsGoodsImg")
            private String mmsGoodsImg;

            @JsonProperty("srchKeyword")
            private String srchKeyword;

            @JsonProperty("limitDay")
            private int limitDay;

            @JsonProperty("content")
            private String content;

            @JsonProperty("goodsImgB")
            private String goodsImgB;

            @JsonProperty("goodsTypeNm")
            private String goodsTypeNm;

            @JsonProperty("exhGenderCd")
            private String exhGenderCd;

            @JsonProperty("exhAgeCd")
            private String exhAgeCd;

            @JsonProperty("validPrdDay")
            private int validPrdDay;

            @JsonProperty("goodsComName")
            private String goodsComName;

            @JsonProperty("goodsName")
            private String goodsName;

            @JsonProperty("mmsReserveFlag")
            private String mmsReserveFlag;

            @JsonProperty("popular")
            private int popular;

            @JsonProperty("goodsStateCd")
            private String goodsStateCd;

            @JsonProperty("brandCode")
            private String brandCode;

            @JsonProperty("goodsNo")
            private String goodsNo;

            @JsonProperty("brandName")
            private String brandName;

            @JsonProperty("mmsBarcdCreateYn")
            private String mmsBarcdCreateYn;

            @JsonProperty("salePrice")
            private int salePrice;

            @JsonProperty("brandIconImg")
            private String brandIconImg;

            @JsonProperty("goodsComId")
            private String goodsComId;

            @JsonProperty("rmCntFlag")
            private String rmCntFlag;

            @JsonProperty("saleDateFlagCd")
            private String saleDateFlagCd;

            @JsonProperty("contentAddDesc")
            private String contentAddDesc;

            @JsonProperty("goodsCode")
            private String goodsCode;

            @JsonProperty("goodsTypeDtlNm")
            private String goodsTypeDtlNm;

            @JsonProperty("category1Seq")
            private int category1Seq;

            @JsonProperty("goodsImgS")
            private String goodsImgS;

            @JsonProperty("affiliate")
            private String affiliate;

            @JsonProperty("validPrdTypeCd")
            private String validPrdTypeCd;

            @JsonProperty("saleDateFlag")
            private String saleDateFlag;

            @JsonProperty("realPrice")
            private int realPrice;
        }
        //상품 리스트
    }
}
