package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<MissionEntity, Long> {
}