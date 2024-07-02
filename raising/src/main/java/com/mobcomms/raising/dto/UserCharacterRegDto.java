package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.CharacterEntity;
import com.mobcomms.raising.entity.UserCharacterEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserCharacterRegDto(
        @Schema(description = "캐릭터고유번호") long characterSeq,
        @Schema(description = "캐릭터명") String characterNickName,
        @Schema(description = "캐릭터레벨") int level,
        @Schema(description = "캐릭터경험치") int exp
){
    public static UserCharacterEntity toEntity(UserCharacterRegDto dto) {
        return dto != null
                ? new UserCharacterEntity(
                        null, dto.characterNickName, dto.level, dto.exp,
                    null, null, null,null) : null;
    }
 }
