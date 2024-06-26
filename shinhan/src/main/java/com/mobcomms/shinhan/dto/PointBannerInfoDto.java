package com.mobcomms.shinhan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;
/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
@Data
public class PointBannerInfoDto {
    @Schema(description = "유저 키")
    private String userKey;
    private String badgeImageUrl;
    private String pointUnit;
    private Integer bannerPoint;
    private Integer bannerFrequency;
    private Integer userFrequency;
    private String adType;
    private String zoonId;
    private List<AdInfo> AdInfos;

    @Data
    public static class AdInfo {
        private String adUrl;
        private String adImageUrl;
    }
}
