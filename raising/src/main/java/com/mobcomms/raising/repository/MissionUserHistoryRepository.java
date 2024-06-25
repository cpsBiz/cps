package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.MissionUserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;

public interface MissionUserHistoryRepository extends JpaRepository<MissionUserHistoryEntity, Long> {
    MissionUserHistoryEntity findTopByUserSeqAndCompletedYnOrderByRegDateAsc(long userSeq, String completedYn);
    List<MissionUserHistoryEntity> findTopByUserSeqAndMissionSeqAndCompletedYnOrderByRegDateAsc(
            long userSeq, long missionSeq, String completedYn);
    List<MissionUserHistoryEntity> findAllByUserSeqAndMissionSeqAndRegDateAfterOrderByRegDateAsc(
            long userSeq, long missionSeq, LocalDateTime regDate);
    }