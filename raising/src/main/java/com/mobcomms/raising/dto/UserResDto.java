package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원정보")
public record UserResDto(@Schema(description = "사용자") String userInfo) {
    public static UserResDto of(UserEntity entity) {
        return  entity != null ? new UserResDto(entity.getMediaUserKey()) : null;
    }
}

@Schema(description = "유저출석정보")
record UserAttendanceResDto(
        @Schema(description = "출석수") String attendanceCount,
        @Schema(description = "출석완료여부") Boolean doneYn) {
}

@Schema(description = "사용자캐릭터정보")
record UserCharacterResDto(
        @Schema(description = "캐릭터닉네임") String characterNickName,
        @Schema(description = "캐릭터고유번호") long characterSeq,
        @Schema(description = "캐릭터이미지") String characterImg,
        @Schema(description = "캐릭터최대레벨") int maxLevel,
        @Schema(description = "사용자캐릭터레벨") int level,
        @Schema(description = "사용자캐릭터경험치") int exp) {
}

@Schema(description = "사용자 게임정보")
record UserGameInfos(
        @Schema(description = "사용자캐릭터정보") UserCharacterResDto userCharacter,
        @Schema(description = "상품정보") GoodsResDto goodsInfo,
        @Schema(description = "현재포인트") int point) {
}
