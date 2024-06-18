package com.mobcomms.raising.service;

import com.mobcomms.raising.dto.UserReqDto;
import com.mobcomms.raising.dto.UserResDto;
import com.mobcomms.raising.entity.UserEntity;
import com.mobcomms.raising.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResDto readUser(String mediaUserKey) {
        return UserResDto.of(userRepository.findByMediaUserKey());
    }
}
