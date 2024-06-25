package com.mobcomms.raising.service;

import com.mobcomms.raising.dto.SaveResDto;
import com.mobcomms.raising.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
//@Transactional("transactionManager")
@Service
public class SaveService
{
    private final MissionService missionService;
    private final MissionUserHistoryService missionUserHistoryService;
    private final UserCharacterService userCharacterService;
    private final GameService gameService;
    private final GoodsService goodsService;
    private final MissionUserCharacterGrowthHistoryService missionUserCharacterGrowthHistoryService;


    public SaveResDto save(long userSeq, long characterSeq, long missionSeq) throws Exception{
        // 사용여부확인
        List<MissionUserHistoryEntity> entities =
                missionUserHistoryService.selectMissionUserHistory(userSeq, missionSeq, "N");

        if(entities == null || entities.size() == 0) {
            // 남은 먹이 없음
            System.out.println("남은먹이 없음");
            throw new Exception();
        } else {
            // 먹이 줌...
            MissionUserHistoryEntity entity = entities.get(0);

            // getPointByLevel
            int point = this.getPointByLevel(1);
            // getExpByLevel
            int exp = this.getExpByLevel(1);

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

            UserCharacterEntity characterEntity =  userCharacterService.selectUserCharacter(userSeq, characterSeq);
            int beforeLevel = characterEntity.getLevel();
            // 경험치 적립 호출 user_character..
            // getLevelByPoint
            int level = getLevelByPoint(exp);
            // 레벨업 여부
            String levelUpYn = beforeLevel == level ? "N" : "Y";

            // 레밸변경 확인
            UserCharacterEntity userCharacterEntity =
                    userCharacterService.selectUserCharacter(userSeq, characterSeq);
            userCharacterEntity.setLevel(level);
            userCharacterEntity.setExp(userCharacterEntity.getExp() + exp);
            userCharacterEntity = userCharacterService.insertUserCharacter(userCharacterEntity);

            // point 적립호출 user_game
            UserGameEntity userGameEntity = gameService.selectGameSeq(userSeq, characterSeq);
            userGameEntity.setPoint(userGameEntity.getPoint() + userCharacterEntity.getExp());
            float pointRate = 0f;
            if(userGameEntity != null) {
                userGameEntity = gameService.insertUserGame(userGameEntity);
                // 상품포인트 정보 필요
                GoodsEntity goodsEntity = goodsService.selectGoods(userGameEntity.getGoodsSeq());
                pointRate = (float)Math.ceil((float)userGameEntity.getPoint() / goodsEntity.getRequiredPoint() * 10000) / 100f;
            }

            // 미션 정보
            return SaveResDto.builder()
                    .userGamePoint(userGameEntity.getPoint())
                    .expRate(0f)  // 레벨업까지의 경험치
                    .exp(userCharacterEntity.getExp())
                    .goodsDoneYn(userGameEntity.getEndYn())
                    .level(userCharacterEntity.getLevel())
                    .levelUpYn(levelUpYn)
                    .pointRate(pointRate)  // 상품 수령까지의 경험치 비율
                    .maxLevelYn("N")  // 맥스레벨 여부(이건 필요없음)
                    .missionCount(entities.size() - 1)  // 위에 목록에서 하나 사용함
                    .dailyLimitCount(missionService.getRemainingMissionCount(userSeq, missionSeq))
                    .build();
        }
    }


    // 레벨에의한 획득 포인트
    private int getPointByLevel(int level) {
        return 2;
    }

    // 레벨에의한 획득경험치
    private int getExpByLevel(int level) {
        return 4;
    }

    // 경험치에 의한 레벨
    private int getLevelByPoint(long userSeq) {
        return 1;
    }

}
