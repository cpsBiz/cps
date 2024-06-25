package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notice")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_seq", nullable = false)
    private Long id;

    @Column(name = "notice_title", nullable = false, length = 100)
    private String noticeTitle;

    @Lob
    @Column(name = "notice_content", nullable = false)
    private String noticeContent;

    @Lob
    @Column(name = "top_yn")
    private String topYn;

    @Lob
    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "start_date", length = 20)
    private String startDate;

    @Column(name = "end_date", length = 20)
    private String endDate;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "reg_user", nullable = false, length = 20)
    private String regUser;

    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    @Column(name = "mod_user", nullable = false, length = 20)
    private String modUser;

}