package com.mobcomms.raising.service;

import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.raising.dto.MissionItemDto;
import com.mobcomms.raising.dto.mapper.MissionItemMapper;
import com.mobcomms.raising.repository.MissionItemRepository;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * 미션 아이템 등록, 수정 (admin), 미션 아이템 조회
 * UpdateDate : 2024-06-19,
 */
public class MissionItemService {

    MissionItemRepository missionitemRepository;

    //미션 아이템 목록 조회
    public GenericBaseResponse<MissionItemDto> getMissionList(){
        var result =  missionitemRepository.findByUseYn("Y");
        var response = new GenericBaseResponse<MissionItemDto>();
        response.setSuccess();
        response.setDatas(MissionItemMapper.INSTANCE.toDtoList(result));

        return response;
    }
}
