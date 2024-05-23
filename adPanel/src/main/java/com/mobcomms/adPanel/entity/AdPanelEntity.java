package com.mobcomms.adPanel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "ad_panel")
@IdClass(AdPanelPk.class)
public class AdPanelEntity {
    @Id
    @Column(name = "client_code") private String clientCode;
    @Id
    @Column(name = "product_code") private String productCode;
    @Id
    @Column(name = "zone_id") private String zoneId;
    @Id
    @Column(name = "os_type") private String osType;
    @Column(name = "use_yn") private String useYn;
    @Column(name = "width") private String width;
    @Column(name = "height") private String height;
    @Column(name = "view_index") private String viewIndex;
    @Column(name = "reg_date", updatable=false) private LocalDateTime regDttm;
    @Column(name = "mod_date") private LocalDateTime modDttm;
    @Column(name = "mod_userkey") private String modUserKey;

}
