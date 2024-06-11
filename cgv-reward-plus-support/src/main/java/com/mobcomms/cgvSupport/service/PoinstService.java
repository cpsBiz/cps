package com.mobcomms.cgvSupport.service;

import com.mobcomms.cgvSupport.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional("transactionManager")
public class PoinstService {
    private final PointRepository pointRepository;

    public List<Map<String, Object>> readAccumulationUserCount(String startDate, String endDate){
        return pointRepository.findPointUserCount(startDate, endDate);
    }
}
