package com.mobcomms.adPanel.service;

import com.mobcomms.adPanel.dto.AdPanelDto;
import com.mobcomms.adPanel.entity.AdPanelEntity;
import com.mobcomms.adPanel.repository.AdPanelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional("transactionManager")
@RequiredArgsConstructor
public class AdPanelService {
    private final AdPanelRepository adPanelRepository;

    public List<AdPanelDto> adPanelList(){
        return adPanelRepository.findAll().stream().map(entity -> AdPanelDto.adPanelList(entity)).collect(Collectors.toList());
    }

    public void adPanelInsert(AdPanelEntity adPanelEntity){
        List<AdPanelEntity> adPanelSize = adPanelRepository.findAllByClientCodeAndProductCodeAndZoneIdAndOsType(adPanelEntity.getClientCode(), adPanelEntity.getProductCode(), adPanelEntity.getZoneId(), adPanelEntity.getOsType());
        if (adPanelSize.size() == 0) {
            adPanelEntity.setRegDttm(LocalDateTime.now());
        } else {
            adPanelEntity.setModDttm(LocalDateTime.now());
        }
        adPanelRepository.save(adPanelEntity);
    }

    public void adPanelDelete(AdPanelEntity adPanelEntity){
        adPanelRepository.delete(adPanelEntity);
    }

}
