package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_character")
@EntityListeners(AuditingEntityListener.class)
public class UserCharacterEntity {
    @EmbeddedId
    private UserCharacterPK id;

    @Column(name = "character_nick_name", nullable = false, length = 20)
    private String characterNickName;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "exp", nullable = false)
    private Integer exp;

    @Column(name = "reg_date", nullable = false)
    @CreatedDate
    private Instant regDate;

    @Column(name = "reg_user", nullable = false)
    private String regUser;

    @Column(name = "mod_date", nullable = false)
    @LastModifiedDate
    private Instant modDate;

    @Column(name = "mod_user", nullable = false)
    private String modUser;

}