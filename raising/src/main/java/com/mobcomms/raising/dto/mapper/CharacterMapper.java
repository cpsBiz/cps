package com.mobcomms.raising.dto.mapper;

import com.mobcomms.raising.dto.CharacterDto;
import com.mobcomms.raising.entity.CharacterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import java.util.List;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * CharacterMapper toEntity, toDto, toEntityList, toDtoList
 * UpdateDate : 2024-06-19, 업데이트 내용
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CharacterMapper {
    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    @Mapping(source = "characterSeq", target = "id")
    @Mapping(source = "characterImage", target = "characterImg")
    CharacterEntity toEntity(CharacterDto model);

    @Mapping(source = "id", target = "characterSeq")
    CharacterDto toDto(CharacterEntity entity);

    List<CharacterEntity> toEntityList(List<CharacterDto> modelList);

    List<CharacterDto> toDtoList(List<CharacterEntity> entityList);
}