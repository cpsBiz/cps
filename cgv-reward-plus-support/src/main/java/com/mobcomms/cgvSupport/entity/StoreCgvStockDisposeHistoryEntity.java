package com.mobcomms.cgvSupport.entity;

import com.mobcomms.cgvSupport.enums.StateEnum;
import com.mobcomms.cgvSupport.enums.converter.StateConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "store_cgv_stock_dispose_history")
@EntityListeners(AuditingEntityListener.class)
public class StoreCgvStockDisposeHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_seq")
    private String historySeq;

    @Column(name = "coupon_no")
    private String couponNo;

    @Convert(converter = StateConverter.class)
    @Column(name = "pre_state")
    private StateEnum preState;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
