package com.mobcomms.raising.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "캐릭터리스트")
public record CharacterResDto(@Schema(description = "캐릭터리스트") List<CharacterInfo> characterInfoList) {}

@Schema(description = "캐릭터정보")
record CharacterInfo(
        @Schema(description = "캐릭터고유번호") long characterSeq,
        @Schema(description = "캐릭터명") String characterName,
        @Schema(description = "캐릭터이미지") String characterImg,
        @Schema(description = "캐릭터최대레벨") int maxLevel
) {}
