package com.cps.cpsService.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsInquiryPk implements Serializable {
    @Column(name = "INQUIRY_NUM") private int inquiryNum;
    @Column(name = "USER_ID") private String userId;
}
