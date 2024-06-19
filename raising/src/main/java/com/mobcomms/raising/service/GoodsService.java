package com.mobcomms.raising.service;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * 상품 조회, 상품 등록, 상품 수정, 상품 핀 등록, 상품 핀 수정
 * UpdateDate : 2024-06-19, 업데이트 내용
 */

import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.raising.dto.GoodsDto;
import com.mobcomms.raising.dto.mapper.GoodsMapper;
import com.mobcomms.raising.repository.GoodsRepository;

//TODO : 상품 핀 등록, 상품 핀 수정
public class GoodsService {

    private GoodsRepository goodsRepository;

    //선택 가능한 상품 목록 조회
    public GenericBaseResponse<GoodsDto> getGoodsList(){
        var result =  goodsRepository.findByUseYnAndTotalCountGreaterThan("Y", 0);
        var response = new GenericBaseResponse<GoodsDto>();
        response.setSuccess();
        response.setDatas(GoodsMapper.INSTANCE.toDtoList(result));

        return response;
    }

    //상품 등록
    public BaseResponse createGoods(GoodsDto model){
        var entity = GoodsMapper.INSTANCE.toEntity(model);
        var result = goodsRepository.save(entity);

        if(result.getId() > 0){
            return BaseResponse.ok();
        } else {
            return BaseResponse.error("Create Goods Error");
        }
    }

    //상품 수정
    public BaseResponse updateGoods(GoodsDto model) throws Exception{
        if(model.getGoodsSeq() == 0)
            throw new IllegalArgumentException("GoodsSeq is worng");

        var entity = goodsRepository.findById(model.getGoodsSeq()).orElseThrow(()->new Exception("Entity not found"));

        //상품명
        entity.setGoodsName(model.getGoodsName() != null ? model.getGoodsName() : entity.getGoodsName());

        //상품 이미지
        entity.setGoodsImage(model.getGoodsImage() != null ? model.getGoodsImage() : entity.getGoodsImage());

        //전체 수량
        entity.setCount(model.getCount() > 0 ? model.getCount() : entity.getCount());

        //사용여부
        entity.setUseYn(model.getUseYn() != null ? model.getUseYn() : entity.getUseYn());

        var result = goodsRepository.save(entity);

        if(result.getId() > 0){
            return BaseResponse.ok();
        } else {
            return BaseResponse.error("Update Goods Error");
        }
    }
}
