package com.mobcomms.shinhan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "SAMPLE")
public class ShinhanSampleEntity {
    @Id
    @Column(name = "TEST_COLUMN_01") private String testColumn1;
    @Column(name = "TEST_COLUMN_02") private String testColumn2;
    @Column(name = "TEST_REG_DTTM") private LocalDateTime testRegDttm;
}
