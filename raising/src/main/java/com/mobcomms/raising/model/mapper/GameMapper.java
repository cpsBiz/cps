package com.mobcomms.raising.model.mapper;


import com.mobcomms.raising.entity.UserGameEntity;
import com.mobcomms.raising.model.GameModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mapping(source = "gameSeq", target = "id")
    UserGameEntity toEntity(GameModel model);

    @Mapping(source = "id", target = "gameSeq")
    GameModel toModel(UserGameEntity entity);
}
