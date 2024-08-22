package com.mobcomms.hanapay.repository;

import com.mobcomms.hanapay.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findAllByUserUuid(String userId);

    UserEntity save(UserEntity entity);
}
