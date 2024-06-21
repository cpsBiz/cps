package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.MissionUserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionUserHistoryRepository extends JpaRepository<MissionUserHistoryEntity, Long> {
    MissionUserHistoryEntity findTopByUserSeqAndCompletedYnOrderByRegDateAsc(long userSeq, String completedYn);
}