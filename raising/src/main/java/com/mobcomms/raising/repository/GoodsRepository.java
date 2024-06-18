package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {
}