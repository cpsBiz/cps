package com.mobcomms.raising.service;

import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.raising.entity.UserGameEntity;
import com.mobcomms.raising.repository.UserGameRepository;

public class GameService {
    //게임 생성
    //게임 업데이트
        //케럭터
        //상품키
        //포인트
        //게임 진행일
        //종료 여부

    //게임 정보 가져오기
        //케릭터 정보 가져오기
        //상품 정보 가져오기
        //출석 내역 가져오기
        //유저 미션 A,B,C 수행 내역 가져오기
        //미션 B 정보 가져오기

    private UserGameRepository userGameRepository;

    //userSeq 을 객체로 받을지 String 으로 받을지 결정해야 함
    public BaseResponse CreateGame(long userSeq){
        BaseResponse response = new BaseResponse();

        //게임 생성
        var result = userGameRepository.save(new UserGameEntity(){{
            setUserSeq(userSeq);
        }});


        if(result.getId() > 0){
            //성공 반환

        } else {
            //실패 반환

        }
        return response;
    }

    /*public BaseResponse UpdateDate(){

    }*/
}
