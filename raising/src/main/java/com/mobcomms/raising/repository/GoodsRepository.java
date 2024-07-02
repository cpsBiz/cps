package com.mobcomms.raising.repository;

import com.mobcomms.raising.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {
    List<GoodsEntity> findByUseYnAndTotalCountGreaterThan(String useYn, int totalCount);
}