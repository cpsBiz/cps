package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.CharacterHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterHistoryRepository extends JpaRepository<CharacterHistoryEntity, Long> {
}