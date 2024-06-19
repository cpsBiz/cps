package com.mobcomms.raising.service;

import com.mobcomms.raising.dto.UserResDto;
import com.mobcomms.raising.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * 사용자 서비스, 회원 등록, 회원 정보 수정, 회원 조회
 * UpdateDate : 2024-06-19,
 */
//TODO : 회원 등록, 회원 정보 수정
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserResDto readUser(String mediaUserKey) {
        return UserResDto.of(userRepository.findByMediaUserKey(mediaUserKey));
    }
}
