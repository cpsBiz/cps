package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mission_user_history")
public class MissionUserHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_history_seq", nullable = false)
    private Long id;

    @Column(name = "mission_seq", nullable = false)
    private Long missionSeq;

    @Column(name = "mission_item_seq")
    private Long missionItemSeq;

    @Column(name = "user_seq", nullable = false)
    private Long userSeq;

    @Column(name = "game_seq", nullable = false)
    private Long gameSeq;

    @Lob
    @Column(name = "completed_yn")
    private String completedYn;

    @CreatedDate
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "reg_user", nullable = false)
    private Long regUser;

    @LastModifiedDate
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    @Column(name = "mod_user", nullable = false)
    private Long modUser;

}