package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mission_user_character_growth_history")
public class MissionUserCharacterGrowthHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_user_character_growth_history_seq", nullable = false)
    private Long id;

    @Column(name = "mission_history_seq", nullable = false)
    private Long missionHistorySeq;

    @Column(name = "exp", nullable = false)
    private Integer exp;

    @Column(name = "point", nullable = false)
    private Integer point;

    @CreatedDate
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

}