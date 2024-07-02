package com.mobcomms.shinhan.repository;

import com.mobcomms.shinhan.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    //유저키에 해당 유저 정보 가져오기
    UserInfoEntity findByUserKey(String userKey);
}
