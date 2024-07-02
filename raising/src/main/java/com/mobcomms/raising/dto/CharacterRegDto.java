package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.CharacterEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "신규캐릭터등록")
public record CharacterRegDto(
        @Schema(description = "캐릭터명") String characterName,
        @Schema(description = "캐릭터이미지") String characterImg,
        @Schema(description = "캐릭터최대레벨") int maxLevel,
        @Schema(description = "초기노출여부") String firstViewYn) {

    public static CharacterEntity toEntity(CharacterRegDto dto) {
        return dto != null
                ? new CharacterEntity(
                        null, dto.characterName, dto.characterImg, dto.maxLevel, dto.firstViewYn,
                    null,null, null, null) : null;
    }
}
