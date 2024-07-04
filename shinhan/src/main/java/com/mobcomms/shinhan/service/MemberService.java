package com.mobcomms.shinhan.service;

import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.common.utils.StringUtils;
import com.mobcomms.shinhan.dto.UserDto;
import com.mobcomms.shinhan.entity.UserInfoEntity;
import com.mobcomms.shinhan.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
@RequiredArgsConstructor
@Service
public class MemberService {
    //TODO : try catch 으로 BaseResponse return

    private final UserInfoRepository userInfoRepository;
    //회원 가입 or 업데이트
    public void joinOrEdit(UserDto model) {
        //회원 가입 or 업데이트 로직
        try {
            var result = userInfoRepository.findByUserKey(model.getUserKey());
            if(result == null){
                var entity = new UserInfoEntity();
                entity.setUserKey(model.getUserKey());
                entity.setUserAppOs(model.getUserAppOs());
                entity.setRegDate(LocalDateTime.now());
                entity.setEditDate(LocalDateTime.now());
                entity.setAdid(model.getAdid());
                userInfoRepository.save(entity);
            } else {
                result.setAdid(model.getAdid());
                result.setEditDate(LocalDateTime.now());
                userInfoRepository.save(result);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
