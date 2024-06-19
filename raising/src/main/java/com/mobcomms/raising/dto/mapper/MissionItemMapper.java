package com.mobcomms.raising.dto.mapper;
import com.mobcomms.raising.dto.MissionItemDto;
import com.mobcomms.raising.entity.MissionEntity;
import com.mobcomms.raising.entity.MissionItemEntity;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


public interface MissionItemMapper {
    MissionItemMapper INSTANCE = Mappers.getMapper(MissionItemMapper.class);

    @Mapping(source = "missionSeq", target = "id")
    MissionItemEntity toEntity(MissionItemDto model);

    @Mapping(source = "id", target = "missionSeq")
    MissionItemDto toDto(MissionItemEntity entity);

    List<MissionItemDto> toDtoList(List<MissionItemEntity> entityList);
}
