package com.mobcomms.raising.service;

import com.mobcomms.raising.entity.MissionUserCharacterGrowthHistoryEntity;
import com.mobcomms.raising.repository.MissionUserCharacterGrowthHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * Created by
 * Create Date :
 * 유저 캐릭터 레벨, Game Point 히스트리, 등록
 * UpdateDate :
 */
//TODO 등록
@RequiredArgsConstructor
@Service
public class MissionUserCharacterGrowthHistoryService {

    private final MissionUserCharacterGrowthHistoryRepository missionUserCharacterGrowthHistoryRepository;

    protected MissionUserCharacterGrowthHistoryEntity insertMissionUserCharacterGrowthHistory(
            MissionUserCharacterGrowthHistoryEntity entity
    ) {
        return missionUserCharacterGrowthHistoryRepository.saveAndFlush(entity);
    }
}
