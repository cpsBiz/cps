package com.mobcomms.raising.service;

import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.common.utils.StringUtils;
import com.mobcomms.raising.entity.UserGameEntity;
import com.mobcomms.raising.model.CharacterModel;
import com.mobcomms.raising.model.GameModel;
import com.mobcomms.raising.model.GoodsModel;
import com.mobcomms.raising.model.mapper.GameMapper;
import com.mobcomms.raising.model.mapper.GoodsMapper;
import com.mobcomms.raising.repository.CharacterRepository;
import com.mobcomms.raising.repository.GoodsRepository;
import com.mobcomms.raising.repository.UserCharacterRepository;
import com.mobcomms.raising.repository.UserGameRepository;


/*
 * Created by wh
 * CreateDate : 2024-06-19
 * 게임 생성, 업데이트, 최근 게임 정보 가져오기, 게임 케릭터 정보 가져오기, 게임 상품 정보 가져오기
 * UpdateDate : 2024-06-19, mapper 적용 및 코드 정리
 */
public class GameService {

    private UserGameRepository userGameRepository;
    private UserCharacterRepository userCharacterRepository;
    private GoodsRepository goodsRepository;
    private CharacterRepository characterRepository;

    //게임 생성
    public BaseResponse createGame(long userSeq){
        //게임 생성
        var result = userGameRepository.save(new UserGameEntity(){{
            setUserSeq(userSeq);
        }});

        if(result.getId() > 0){
            return BaseResponse.ok();
        } else {
            return BaseResponse.error("Create Game Error");
        }
    }

    //게임 업데이트
    public BaseResponse updateGame(GameModel model) throws Exception{
        if(model.getGameSeq() == 0)
            throw new IllegalArgumentException("GameSeq is worng");

        var entity = userGameRepository.findById(model.getGameSeq()).orElseThrow(()->new Exception("Entity not found"));

        //케럭터
        if(model.getCharacterSeq() > 0 ){
            entity.setCharacterSeq(model.getCharacterSeq());
        }
        //상품키
        if(model.getGoodsSeq() > 0){
            entity.setGoodsSeq(model.getGoodsSeq());
        }
        //포인트
        if(model.getPoint() > 0){
            entity.setPoint(model.getPoint());
        }
        //게임 진행일
        if(model.getPlayDate() != null){
            entity.setPlayDate(model.getPlayDate());
        }
        //종료 여부
        if(!StringUtils.isNullOrEmpty(model.getEndYn())){
            entity.setEndYn(model.getEndYn());
        }

        //게임 업데이트
        var result = userGameRepository.save(entity);

        if(result.getId() > 0){
            return BaseResponse.ok();
        } else {
            return BaseResponse.error("Update Game Error");
        }
    }

    //최근 게임 정보 가져 오기
    public GenericBaseResponse<GameModel> getGame(GameModel model){
        if(model.getUserSeq() == 0)
            throw new IllegalArgumentException("UserSeq is worng");

        GenericBaseResponse<GameModel> response = new GenericBaseResponse<>();
        var entity = userGameRepository.findTopByUserSeqOrderByPlayDateDesc(model.getUserSeq());
        if(entity != null){
            var data = GameMapper.INSTANCE.toModel(entity);
            response.setSuccess();
            response.setData(data);

            /*response.setData(new GameModel(){{
                setCharacterSeq(result.getCharacterSeq());
                setEndYn(result.getEndYn());
                setGameSeq(result.getId());
                setGoodsSeq(result.getGoodsSeq());
                setPlayDate(result.getPlayDate());
                setPoint(result.getPoint());
                setUserSeq(result.getUserSeq());
            }});*/
        }

        return  response;
    }

    //게임 케릭터 정보 가져오기
    public GenericBaseResponse<CharacterModel> getGameCharacter(GameModel model){
        GenericBaseResponse<CharacterModel> response = new GenericBaseResponse<>();
        var entity = userCharacterRepository.findByIdUserSeqAndIdCharacterSeq(model.getUserSeq(),model.getCharacterSeq());

        if(entity != null){
            var character = characterRepository.findById(entity.getId().getCharacterSeq()).orElse(null);

            response.setData(new CharacterModel(){{
                setCharacterSeq(entity.getId().getCharacterSeq());
                setCharacterImage(character.getCharacterImg());
                setCharacterName(entity.getCharacterNickName());
            }});
        }
        return response;
    }

    //게임 상품 정보 가져오기
    public GenericBaseResponse<GoodsModel> getGameGoods(GameModel model){
        if(model.getGoodsSeq() == 0)
            throw new IllegalArgumentException("GoodsSeq is worng");

        GenericBaseResponse<GoodsModel> response = new GenericBaseResponse<>();
        //상품 정보 가져오기
        var entity = goodsRepository.findById(model.getGoodsSeq()).orElse(null);

        var data =  GoodsMapper.INSTANCE.toModel(entity);
        response.setData(data);

        return response;
    }


}
