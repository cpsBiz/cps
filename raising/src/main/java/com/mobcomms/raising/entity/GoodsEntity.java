package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "goods")
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_seq", nullable = false)
    private Long id;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "consumer_price", nullable = false)
    private Integer consumerPrice;

    @Column(name = "required_point", nullable = false)
    private Integer requiredPoint;

    @Column(name = "difficulty", nullable = false)
    private Character difficulty;

    @Column(name = "total_count", nullable = false)
    private Integer totalCount;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "goods_image", nullable = false)
    private String goodsImage;

    @Column(name = "goods_name", nullable = false, length = 55)
    private String goodsName;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "use_yn")
    private Character useYn;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false)
    private Long regUser;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

    @Column(name = "mod_user", nullable = false)
    private Long modUser;

}