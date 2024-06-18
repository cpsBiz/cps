package com.mobcomms.raising.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인요청")
public class LoginReqDto {
    @Schema(description = "유저아이디")
    private String userSeq;
    @Schema(description = "고객사코드(=매체사유저키)")
    private String mediaUserKey;
    @Schema(description = "app device id")
    private String adid;
    @Schema(description = "플렛폼타입(1:ios,2:android,3:pc)")
    private String platform;
}
