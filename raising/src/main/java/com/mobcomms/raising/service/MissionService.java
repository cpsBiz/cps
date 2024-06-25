package com.mobcomms.raising.service;

import com.mobcomms.raising.dto.*;
import com.mobcomms.raising.entity.MissionEntity;
import com.mobcomms.raising.entity.MissionUserHistoryEntity;
import com.mobcomms.raising.repository.MissionRepository;
import com.mobcomms.raising.repository.MissionUserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * 미션 조회, 미션 수행 내역 조회, 미션 등록, 미션 수정,
 * UpdateDate : 2024-06-19,
 */
@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionUserHistoryService missionUserHistoryService;
    private final MissionItemService missionItemService;
    private final UserCharacterService userCharacterService;

    private final MissionRepository missionRepository;

    public MissionDto readMission() {
        return this.mergeMissionDtoByType(this.selectMissionList());
    }

    public MissionDto missionExecute(Long userSeq, MissionExecuteDto dto)  {
        missionUserHistoryService.createMissionUserHistory(userSeq, dto);
        return readMission();
    }

    //미션 수행 내역 조회
    protected List<MissionEntity> selectMissionList() {
        return missionRepository.findAll(Sort.by("missionType").ascending());
    }

    protected MissionEntity selectMission(Long missionSeq) {
        return missionRepository.findById(missionSeq).get();
    }

    private MissionDto mergeMissionDtoByType( List<MissionEntity> missionEntities) {
        MissionA missionA =
                missionEntities.stream()
                        .filter(entity -> entity.getMissionType().equals("A"))
                        .map(entity ->  MissionA.of(entity))
                        .findFirst().get();

        MissionB missionB =
                missionEntities.stream()
                        .filter(entity -> entity.getMissionType().equals("B"))
                        .map(entity -> MissionB.of(entity, missionItemService.selectMissionItemList(entity.getId())))
                        .findFirst().get();

        MissionC missionC =
                missionEntities.stream()
                        .filter(entity -> entity.getMissionType().equals("C"))
                        .map(entity -> MissionC.of(entity))
                        .findFirst().get();

        return MissionDto.builder().missionA(missionA).missionB(missionB).missionC(missionC).build();
    }

    //미션 등록

    //미션 수정


    // 남은 미션 정보.
    protected int getRemainingMissionCount(Long userSeq, Long missionSeq) {
        MissionEntity missionEntity = this.selectMission(missionSeq);
        // 오늘 미션을 몇번 수행했는지
        List<MissionUserHistoryEntity> missionUserHistoryEntityList =
                missionUserHistoryService.selectMissionUserHistory(
                        userSeq, missionSeq, LocalDate.now().atStartOfDay(ZoneId.of("Asia/Seoul")).toLocalDateTime());

        return missionEntity.getDailyLimitCount() - missionUserHistoryEntityList.size();
    }

}
