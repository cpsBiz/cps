package com.mobcomms.cgvSupport.repository;

import com.mobcomms.cgvSupport.entity.StoreCgvStockDisposeHistoryEntity;
import com.mobcomms.cgvSupport.entity.StoreCgvStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StoreCgvStockDisposeHistoryRepository extends JpaRepository<StoreCgvStockDisposeHistoryEntity, String> {

}
