package com.mobcomms.raising.dto;


import com.mobcomms.raising.entity.CharacterEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "캐릭터목록")
public record CharacterDto(
        @Schema(description = "캐릭터고유번호") long characterSeq,
        @Schema(description = "캐릭터명") String characterName,
        @Schema(description = "캐릭터이미지") String characterImg,
        @Schema(description = "캐릭터최대레벨") int maxLevel) {

    public static CharacterDto of(CharacterEntity entity) {
        return  entity != null
                ? new CharacterDto(
                        entity.getId(),
                        entity.getCharacterName(),
                        entity.getCharacterImg(),
                        entity.getMaxLevel()) : null;
    }
}
