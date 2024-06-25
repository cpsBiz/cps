package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`character`")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_seq", nullable = false)
    private Long id;

    @Column(name = "character_name", nullable = false, length = 20)
    private String characterName;

    @Column(name = "character_img", nullable = false)
    private String characterImg;

    @Column(name = "max_level", nullable = false)
    private Integer maxLevel;

    @Lob
    @Column(name = "first_view_yn")
    private String firstViewYn;

    @CreatedDate
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "reg_user", nullable = false)
    private String regUser;

    @LastModifiedDate
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    @Column(name = "mod_user", nullable = false)
    private String modUser;

}