package com.mobcomms.raising.service;

import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.raising.dto.MissionItemDto;
import com.mobcomms.raising.dto.mapper.MissionItemMapper;
import com.mobcomms.raising.entity.MissionEntity;
import com.mobcomms.raising.entity.MissionItemEntity;
import com.mobcomms.raising.repository.MissionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * 미션 아이템 등록, 수정 (admin), 미션 아이템 조회
 * UpdateDate : 2024-06-19,
 */
@RequiredArgsConstructor
@Service
public class MissionItemService {

    private final MissionItemRepository missionItemRepository;

    //미션 아이템 목록 조회
    public GenericBaseResponse<MissionItemDto> getMissionList(){
        var response = new GenericBaseResponse<MissionItemDto>();
        response.setSuccess();
        response.setDatas(MissionItemMapper.INSTANCE.toDtoList(this.selectMissionItemList()));

        return response;
    }

    protected List<MissionItemEntity> selectMissionItemList() {
        return missionItemRepository.findByUseYn("Y");
    }

    protected List<MissionItemEntity> selectMissionItemList(Long missionItemSeq) {
        return  missionItemRepository.findByIdAndUseYn(missionItemSeq, "Y");
    }

}
