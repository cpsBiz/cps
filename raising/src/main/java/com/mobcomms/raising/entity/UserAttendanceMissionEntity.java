package com.mobcomms.raising.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "user_attendance_mission")
public class UserAttendanceMissionEntity {
    @EmbeddedId
    private UserAttendanceMissionPK id;

    @Column(name = "attendance_mission_seq", nullable = false)
    private Long attendanceMissionSeq;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false, length = 20)
    private String regUser;

}