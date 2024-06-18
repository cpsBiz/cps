package com.mobcomms.raising.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
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
    private Boolean platform;

    @Column(name = "recommand_code", nullable = false, length = 8)
    private String recommandCode;

    @Column(name = "last_login_date")
    private Instant lastLoginDate;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    public Instant getRegDate() {
        return regDate;
    }

    public void setRegDate(Instant regDate) {
        this.regDate = regDate;
    }

    public Instant getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getRecommandCode() {
        return recommandCode;
    }

    public void setRecommandCode(String recommandCode) {
        this.recommandCode = recommandCode;
    }

    public Boolean getPlatform() {
        return platform;
    }

    public void setPlatform(Boolean platform) {
        this.platform = platform;
    }

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public String getMediaUserKey() {
        return mediaUserKey;
    }

    public void setMediaUserKey(String mediaUserKey) {
        this.mediaUserKey = mediaUserKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}