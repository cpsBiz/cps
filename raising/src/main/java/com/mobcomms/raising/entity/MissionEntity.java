package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "mission")
public class MissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_seq", nullable = false)
    private Long id;

    @Column(name = "mission_name", nullable = false, length = 55)
    private String missionName;

    @Column(name = "mission_type", nullable = false)
    private String missionType;

    @Column(name = "mission_interval", nullable = false)
    private Integer missionInterval;

    @Column(name = "gain_exp", nullable = false)
    private Integer gainExp;

    @Column(name = "gain_point", nullable = false)
    private Integer gainPoint;

    @Column(name = "daily_limit_count", nullable = false)
    private Integer dailyLimitCount;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false)
    private Long regUser;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

    @Column(name = "mod_user", nullable = false)
    private Long modUser;
}