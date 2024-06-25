package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.CharacterEntity;
import com.mobcomms.raising.entity.UserCharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    List<CharacterEntity> findAllByFirstViewYn(String firstViewYn);
    CharacterEntity findByIdAndFirstViewYn(Long characterSeq, String firstViewYn);
}