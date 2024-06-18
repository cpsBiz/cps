package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.UserAttendanceMissionEntity;
import com.mobcomms.raising.entity.UserAttendanceMissionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAttendanceMissionRepository extends JpaRepository<UserAttendanceMissionEntity, UserAttendanceMissionPK> {
}