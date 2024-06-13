package com.mobcomms.cgvSupport.service;

import com.mobcomms.cgvSupport.entity.StoreCgvStockEntity;
import com.mobcomms.cgvSupport.enums.StateEnum;
import com.mobcomms.cgvSupport.repository.StoreCgvStockRepository;
import com.mobcomms.common.utils.MobDateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional("transactionManager")
public class StoreCgvStockService {

    private final StoreCgvStockDisposeHistoryService storeCgvStockDisposeHistoryService;

    private final StoreCgvStockRepository storeCgvStockRepository;

    public List<StoreCgvStockEntity> readStoreCgvStock(StateEnum state, String startDate, String endDate) {
        return storeCgvStockRepository.findAllByStateAndRegDateBetween(
                state
                , MobDateUtils.toLocateDateTimeFromYyyymmdd(startDate)
                , MobDateUtils.toLocateDateTimeFromYyyymmdd(endDate));
    }

    public List<StoreCgvStockEntity> updateCouponState(String startDate, String endDate) {
        List<StoreCgvStockEntity> storeCgvStockEntities = readStoreCgvStock(StateEnum.AVAILABLE, startDate, endDate);
        storeCgvStockDisposeHistoryService.createStoreCgvStockDisposeHistoryEntity(storeCgvStockEntities);
        storeCgvStockEntities.forEach(entity -> entity.setState(StateEnum.DISCARDED));
        return storeCgvStockRepository.saveAll(storeCgvStockEntities);
    }
}
