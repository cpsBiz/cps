package com.mobcomms.raising.service;

import com.mobcomms.raising.dto.UserDto;
import com.mobcomms.raising.dto.UserRegDto;
import com.mobcomms.raising.dto.UserResDto;
import com.mobcomms.raising.entity.UserEntity;
import com.mobcomms.raising.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

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
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private final UserRepository userRepository;

    public UserResDto readUser(String mediaUserKey) {
        return UserResDto.of(userRepository.findByMediaUserKey(mediaUserKey));
    }

    public UserDto createUser(UserRegDto dto) {
        String recommendCode = getRandomCode(8);
        while (isExists(recommendCode)) {
            recommendCode = getRandomCode(8);
        }

        UserEntity userEntity = UserRegDto.toEntity(dto);
        userEntity.setRecommendCode(recommendCode);

        return UserDto.of(this.insertUser(userEntity));
    }

    protected UserEntity insertUser(UserEntity entity) {
        return userRepository.save(entity);
    }

    protected boolean isExists(String recommendCode) {
        return userRepository.existsByRecommendCode(recommendCode);
    }

    private String getRandomCode(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
