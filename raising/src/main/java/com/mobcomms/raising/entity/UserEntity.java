package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq", nullable = false)
    private Long id;

    @Column(name = "media_user_key", nullable = false)
    private String mediaUserKey;

    @Column(name = "adid", length = 55)
    private String adid;

    @Column(name = "platform")
    private Short platform;

    @Column(name = "recommand_code", nullable = false, length = 8)
    private String recommendCode;

    @Column(name = "last_login_date")
    private Instant lastLoginDate;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;
}