package com.mobcomms.cgvSupport.repository;

import com.mobcomms.cgvSupport.entity.StoreCgvStockEntity;
import com.mobcomms.cgvSupport.enums.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StoreCgvStockRepository extends JpaRepository<StoreCgvStockEntity, String> {
    List<StoreCgvStockEntity> findAllByStateAndRegDateBetween(
            StateEnum state, LocalDateTime startDate, LocalDateTime endDate);
}
