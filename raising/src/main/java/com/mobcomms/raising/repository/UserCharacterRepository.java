package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.UserCharacterEntity;
import com.mobcomms.raising.entity.UserCharacterPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCharacterRepository extends JpaRepository<UserCharacterEntity, UserCharacterPK> {
    List<UserCharacterEntity> findAllByIdUserSeq(Long id);
    UserCharacterEntity findByIdUserSeqAndIdCharacterSeq(long userSeq, long characterSeq);
}