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
import java.util.Date;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "store_cgv_stock")
@EntityListeners(AuditingEntityListener.class)
public class StoreCgvStockEntity {
    @Id
    @Column(name = "pk")
    private Long pk;

    @Column(name = "goods_pk")
    private Integer goodsPk;

    @Column(name = "coupon_no")
    private String couponNo;

    @Column(name = "price")
    private String price;

    @Column(name = "expire_date")
    private Date expireDate;

    @Convert(converter = StateConverter.class)
    @Column(name = "state")
    private StateEnum state;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    private LocalDateTime modDate;
}
