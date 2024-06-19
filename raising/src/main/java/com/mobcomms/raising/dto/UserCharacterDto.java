package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.CharacterEntity;
import com.mobcomms.raising.entity.UserCharacterEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자캐릭터")
public record UserCharacterDto(
        @Schema(description = "캐릭터고유번호") long characterSeq,
        @Schema(description = "캐릭터닉네임") String characterNickName,
        @Schema(description = "캐릭터이미지") String characterImg,
        @Schema(description = "캐릭터최대레벨") int maxLevel,
        @Schema(description = "캐릭터레벨") int level,
        @Schema(description = "캐릭터경험치") int point) {

    public static UserCharacterDto of(UserCharacterEntity userCharacterEntity, CharacterEntity characterEntity) {
        return  userCharacterEntity != null && characterEntity != null
                ? new UserCharacterDto(
                        userCharacterEntity.getId().getCharacterSeq(),
                        userCharacterEntity.getCharacterNickName(),
                        characterEntity.getCharacterImg(),
                        characterEntity.getMaxLevel(),
                        userCharacterEntity.getLevel(),
                        userCharacterEntity.getExp()) : null;
    }

}
