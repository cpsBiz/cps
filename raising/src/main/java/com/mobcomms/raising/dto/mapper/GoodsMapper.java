package com.mobcomms.raising.dto.mapper;
import com.mobcomms.raising.entity.GoodsEntity;
import com.mobcomms.raising.dto.GoodsDto;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface GoodsMapper {
    GoodsMapper INSTANCE = Mappers.getMapper(GoodsMapper.class);

    @Mapping(source = "goodsSeq", target = "id")
    GoodsEntity toEntity(GoodsDto model);

    @Mapping(source = "id", target = "goodsSeq")
    GoodsDto toModel(GoodsEntity entity);
}
