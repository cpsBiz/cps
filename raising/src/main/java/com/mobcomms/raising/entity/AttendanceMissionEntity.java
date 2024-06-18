package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "attendance_mission")
public class AttendanceMissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_mission_seq", nullable = false)
    private Long id;

    @Column(name = "period", nullable = false)
    private Integer period;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "special_point", nullable = false)
    private Integer specialPoint;

    @Lob
    @Column(name = "consistency_yn")
    private String consistencyYn;

    @Lob
    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false, length = 20)
    private String regUser;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

    @Column(name = "mod_user", nullable = false, length = 20)
    private String modUser;

}