package com.mobcomms.adPanel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AdPanelPk implements Serializable {
    @Column(name = "client_code") private String clientCode;
    @Column(name = "product_code") private String productCode;
    @Column(name = "zone_id") private String zoneId;
    @Column(name = "os_type") private String osType;
}
