package com.mobcomms.raising.dto.mapper;
import com.mobcomms.raising.entity.GoodsEntity;
import com.mobcomms.raising.dto.GoodsDto;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

public interface GoodsMapper {
    GoodsMapper INSTANCE = Mappers.getMapper(GoodsMapper.class);

    @Mapping(source = "goodsSeq", target = "id")
    GoodsEntity toEntity(GoodsDto model);

    @Mapping(source = "id", target = "goodsSeq")
    GoodsDto toDto(GoodsEntity entity);

    List<GoodsDto> toDtoList(List<GoodsEntity> entityList);
}
