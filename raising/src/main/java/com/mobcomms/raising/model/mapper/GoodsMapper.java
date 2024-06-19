package com.mobcomms.raising.model.mapper;
import com.mobcomms.raising.entity.GoodsEntity;
import com.mobcomms.raising.entity.UserGameEntity;
import com.mobcomms.raising.model.GoodsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface GoodsMapper {
    GoodsMapper INSTANCE = Mappers.getMapper(GoodsMapper.class);

    @Mapping(source = "goodsSeq", target = "id")
    GoodsEntity toEntity(GoodsModel model);

    @Mapping(source = "id", target = "goodsSeq")
    GoodsModel toModel(GoodsEntity entity);
}
