package com.mobcomms.sample.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SAMPLE_2")
@EntityListeners(AuditingEntityListener.class)
public class Sample2Entity{
    @EmbeddedId private Sample2PK sample2PK;
    @Column(name = "TEST_COLUMN_01") private String testColumn1;
    @Column(name = "TEST_REG_DTTM", nullable = false)
    @CreatedDate
    private LocalDateTime testRegDttm;
    @Column(name = "TEST_ALT_DTTM", nullable = false)
    @LastModifiedDate
    private LocalDateTime testAltDttm;
}
