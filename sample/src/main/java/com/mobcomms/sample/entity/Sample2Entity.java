package com.mobcomms.sample.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SAMPLE_2")
public class Sample2Entity{
    @EmbeddedId private Sample2PK sample2PK;
    @Column(name = "TEST_COLUMN_01") private String testColumn1;
    @Column(name = "TEST_REG_DTTM") private LocalDateTime testRegDttm;
    @Column(name = "TEST_ALT_DTTM") private LocalDateTime testAltDttm;
}
