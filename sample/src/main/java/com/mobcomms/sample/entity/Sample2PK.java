package com.mobcomms.sample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Sample2PK implements Serializable {
    @Column(name = "TEST_PK_01") private String testPk01;
    @Column(name = "TEST_PK_02") private String testPk02;
    @Column(name = "TEST_PK_03") private String testPk03;
}
