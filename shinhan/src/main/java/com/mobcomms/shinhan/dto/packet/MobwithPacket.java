package com.mobcomms.shinhan.dto.packet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/*
 * Created by enliple
 * Create Date : 2024-06-27
 * Class 설명, method
 * UpdateDate : 2024-06-27, 업데이트 내용
 */
public class MobwithPacket {


    public static class GetMobwithAdInfo{

        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Response{
            private String code;
            private String message;
            private MobwithAdInfo data;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class  MobwithAdInfo {
        private String zone;
        private String adType;
        private int adCnt;
        private String logoDefault;
        private String logoSub;
        private String logoUrl;
        private String siteUrl;
        private String impUrl;
        private List<MobwithAdInfoItem> data;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class MobwithAdInfoItem{
        private String pCode;
        private String pNm;
        @JsonProperty("pImg")
        private String pImg;
        @JsonProperty("pUrl")
        private String pUrl;
        private String aLogoDefault;
        private String aLogoSub;
        private String aNm;
        private String aTitle;
        private String aUrl;
        private String desc;
        private String aDesc1;
        private String aDesc2;
        private String aDesc3;
        private String pImgBkg;
        private String pPrice;
        private MobwithAdInfoItemMobile mobile;
        private String custom01;
        private String custom02;
        private String custom03;
        private String custom04;
        private String custom05;
        private String custom06;
        private String custom07;
        private String custom08;
        private String custom09;
        private String custom10;


    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class MobwithAdInfoItemMobile{
        private String mImg250_250;
        private String mImg120_600;
        private String mImg728_90;
        private String mImg320_100;
        private String mImg720_120;
        private String mImg160_600;
        private String mImg300_250;
        private String mImg320_480;
    }

}
