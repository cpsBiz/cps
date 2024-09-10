package com.cps.finnq.repository;

import com.cps.finnq.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findAllByUserUuid(String userId);

    UserEntity save(UserEntity entity);
}
