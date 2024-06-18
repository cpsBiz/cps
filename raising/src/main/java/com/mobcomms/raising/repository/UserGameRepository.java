package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.UserGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameRepository extends JpaRepository<UserGameEntity, Long> {
}