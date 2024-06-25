package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "roulette")
public class RouletteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roulette_seq", nullable = false)
    private Long id;

    @Column(name = "memo_title", nullable = false, length = 100)
    private String memoTitle;

    @Column(name = "memo", nullable = false, length = 500)
    private String memo;

    @Column(name = "success_message", nullable = false)
    private String successMessage;

    @Column(name = "failure_message", nullable = false)
    private String failureMessage;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "reg_user", nullable = false, length = 20)
    private String regUser;

    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    @Column(name = "mod_user", nullable = false, length = 20)
    private String modUser;

}