package com.mobcomms.raising.dto;

import com.mobcomms.raising.entity.UserCharacterEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserCharacterModDto(
        @Schema(description = "변경할 캐릭터고유번호") long oldCharacterSeq,
        @Schema(description = "변경될 캐릭터고유번호") long newCharacterSeq
){
}
