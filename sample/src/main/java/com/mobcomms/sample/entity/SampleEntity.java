package com.mobcomms.sample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "test_table")
public class SampleEntity {
    @Id
    @Column(name = "TEST_COLUMN_01") private String testColumn1;
    @Column(name = "TEST_COLUMN_02") private String testColumn2;
}
