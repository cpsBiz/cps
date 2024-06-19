package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.UserCharacterEntity;
import com.mobcomms.raising.entity.UserCharacterPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCharacterRepository extends JpaRepository<UserCharacterEntity, UserCharacterPK> {
    List<UserCharacterEntity> findAllByIdUserSeq(long userSeq);
    UserCharacterEntity findByUserSeqAndCharacterSeq(long userSeq, long characterSeq);
}