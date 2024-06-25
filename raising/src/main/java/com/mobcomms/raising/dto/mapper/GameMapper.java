package com.mobcomms.raising.dto.mapper;


import com.mobcomms.raising.entity.UserGameEntity;
import com.mobcomms.raising.dto.GameDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mapping(source = "gameSeq", target = "id")
    UserGameEntity toEntity(GameDto model);

    @Mapping(source = "id", target = "gameSeq")
    GameDto toModel(UserGameEntity entity);
}
