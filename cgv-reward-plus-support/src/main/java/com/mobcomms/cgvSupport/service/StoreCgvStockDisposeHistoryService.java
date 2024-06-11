package com.mobcomms.cgvSupport.service;

import com.mobcomms.cgvSupport.entity.StoreCgvStockDisposeHistoryEntity;
import com.mobcomms.cgvSupport.entity.StoreCgvStockEntity;
import com.mobcomms.cgvSupport.repository.StoreCgvStockDisposeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional("transactionManager")
public class StoreCgvStockDisposeHistoryService {

    private final StoreCgvStockDisposeHistoryRepository storeCgvStockDisposeHistoryRepository;

    protected List<StoreCgvStockDisposeHistoryEntity> createStoreCgvStockDisposeHistoryEntity(
            List<StoreCgvStockEntity> entities
    ) {
        List<StoreCgvStockDisposeHistoryEntity> storeCgvStockDisposeHistoryEntities =
                entities.stream().map(entity -> {
                        StoreCgvStockDisposeHistoryEntity storeCgvStockDisposeHistoryEntity =
                                new StoreCgvStockDisposeHistoryEntity();
                        storeCgvStockDisposeHistoryEntity.setCouponNo(entity.getCouponNo());
                        storeCgvStockDisposeHistoryEntity.setPreState(entity.getState());
                        return storeCgvStockDisposeHistoryEntity;
            }).collect(Collectors.toList());

        return storeCgvStockDisposeHistoryRepository.saveAllAndFlush(storeCgvStockDisposeHistoryEntities);
    }

}
