package com.mobcomms.raising.service;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * 미션 수행 히스토리
 * UpdateDate : 2024-06-19,
 */

import com.mobcomms.raising.dto.MissionDto;
import com.mobcomms.raising.dto.MissionExecuteDto;
import com.mobcomms.raising.entity.MissionUserHistoryEntity;
import com.mobcomms.raising.repository.MissionUserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//TODO : 미션 수행 히스토리 등록, 업데이트
@RequiredArgsConstructor
@Service
public class MissionUserHistoryService {

    private final GameService gameService;
    private final MissionUserHistoryRepository missionUserHistoryRepository;

    public void createMissionUserHistory(Long userSeq, MissionExecuteDto dto) {
        MissionUserHistoryEntity entity = MissionExecuteDto.toEntity(dto);
        entity.setUserSeq(userSeq);
        entity.setCompletedYn("N");
        entity.setGameSeq(gameService.selectGameSeq(userSeq, dto.characterSeq()).getId());
        entity.setRegUser(1l);
        entity.setModUser(1l);
        this.insertMissionUserHistory(entity);
    }


    // 제일 오래전에 수행한 미션정보를 가져온다
    protected MissionUserHistoryEntity selectMissionUserHistory(long userSeq, String completedYn) {
        return missionUserHistoryRepository.findTopByUserSeqAndCompletedYnOrderByRegDateAsc(userSeq, completedYn);
    }

    // 먹이를 줄수있는 미션
    protected List<MissionUserHistoryEntity> selectMissionUserHistory(long userSeq, long missionSeq, String completedYn) {
        return missionUserHistoryRepository.findTopByUserSeqAndMissionSeqAndCompletedYnOrderByRegDateAsc(
                userSeq, missionSeq, completedYn
        );
    }

    protected List<MissionUserHistoryEntity> selectMissionUserHistory(long userSeq, long missionSeq, LocalDateTime regDate) {
        return missionUserHistoryRepository.findAllByUserSeqAndMissionSeqAndRegDateAfterOrderByRegDateAsc(
                userSeq, missionSeq, regDate);
    }

    // 사용상태변경(먹이제공시 - 포인트 및 경험치 적립, 상태 N으로 변경)
    protected MissionUserHistoryEntity updateMissionUserHistory(MissionUserHistoryEntity entity) {
        entity.setCompletedYn("Y");
        return this.insertMissionUserHistory(entity);
    }

    // 미션을 수행
    protected MissionUserHistoryEntity insertMissionUserHistory(MissionUserHistoryEntity entity) {
        return missionUserHistoryRepository.save(entity);
    }


}
