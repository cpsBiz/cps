package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}