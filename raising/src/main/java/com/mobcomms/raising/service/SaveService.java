package com.mobcomms.raising.service;

import com.mobcomms.raising.entity.MissionUserCharacterGrowthHistoryEntity;
import com.mobcomms.raising.entity.MissionUserHistoryEntity;
import com.mobcomms.raising.entity.UserCharacterEntity;
import com.mobcomms.raising.repository.MissionUserCharacterGrowthHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * 적립
 * UpdateDate : 2024-06-19, 업데이트 내용
 */
//TODO : 적립
/*
* 	캐릭터 레벨에 따른, 미션 정보에 따른, 가중치를 계산 로직
	-> UserCharacterService.유저 캐릭터 업데이트
	-> GameService.업데이트
            -> point history insert
            -> level history insert
*/
@RequiredArgsConstructor
@Service
public class SaveService
{
    private final MissionUserHistoryService missionUserHistoryService;
    private final UserCharacterService userCharacterService;
    private final GameService gameService;
    private final MissionUserCharacterGrowthHistoryService missionUserCharacterGrowthHistoryService;


    public void save(long userSeq, long characterSeq) throws Exception{
        // 사용여부확인
        MissionUserHistoryEntity entity =
                missionUserHistoryService.selectMissionUserHistory(userSeq, "N");

        if(entity == null) {
            // 남은 먹이 없음
            System.out.println("남은먹이 없음");
            throw new Exception();
        } else {
            // 먹이 줌...
            // getPointByLevel
            int point = 2;
            // getExpByLevel
            int exp = 2;

            MissionUserCharacterGrowthHistoryEntity missionUserCharacterGrowthHistoryEntity =
                    new MissionUserCharacterGrowthHistoryEntity();
            missionUserCharacterGrowthHistoryEntity.setMissionHistorySeq(entity.getId());
            missionUserCharacterGrowthHistoryEntity.setExp(exp);
            missionUserCharacterGrowthHistoryEntity.setPoint(point);

            missionUserCharacterGrowthHistoryService.insertMissionUserCharacterGrowthHistory(
                    missionUserCharacterGrowthHistoryEntity
            );

            entity.setCompletedYn("Y");
            missionUserHistoryService.updateMissionUserHistory(entity);

            System.out.println("미션 정보저장");

            // 경험치 적립 호출 user_character..
            // getLevelByPoint
            int level = 1;

            UserCharacterEntity userCharacterEntity =
                    userCharacterService.selectUserCharacter(userSeq, characterSeq);
            userCharacterEntity.setLevel(level);
            userCharacterEntity.setExp(userCharacterEntity.getExp() + exp);
            userCharacterService.insertUserCharacter(userCharacterEntity);

            // point 적립호출 user_game
            //gameService.updateGame(null);

        }
/*

        // 적립상태 변경 필요 
        
        // 적립 로그
        missionUserCharacterGrowthHistoryService.insertMissionUserCharacterGrowthHistory(null);

 */
    }

    // 레벨에의한 획득 포인트
    private int getPointByLevel(int level) {
        return 0;
    }

    // 레벨에의한 획득경험치
    private int getExpByLevel(int level) {
        return 0;
    }

    // 경험치에 의한 레벨
    private int getLevelByPoint(long userSeq) {
        return 0;
    }

}
