package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.MissionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionItemRepository extends JpaRepository<MissionItemEntity, Long> {
}