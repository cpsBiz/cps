package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.MissionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MissionItemRepository extends JpaRepository<MissionItemEntity, Long> {
    List<MissionItemEntity> findByUseYn(String useYn);
    List<MissionItemEntity> findByIdAndUseYn(Long missionItemSeq, String useYn);
}