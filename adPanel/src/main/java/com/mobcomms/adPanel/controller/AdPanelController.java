package com.mobcomms.adPanel.controller;

import com.mobcomms.adPanel.dto.AdPanelDto;
import com.mobcomms.adPanel.entity.AdPanelEntity;
import com.mobcomms.adPanel.service.AdPanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdPanelController {

    private final AdPanelService adPanelService;

    @GetMapping("/adPanelList")
    public List<AdPanelDto>  adPanelList() {
        List<AdPanelDto> adPanelList = adPanelService.adPanelList();
        return adPanelList;
    }

    @PostMapping("/adPanelInsert")
    public void adPanelInsert(@RequestBody AdPanelEntity adPanelEntity) {
        adPanelService.adPanelInsert(adPanelEntity);
    }

    @PostMapping("/adPanelDelete")
    public void adPanelDelete(@RequestBody AdPanelEntity adPanelEntity) {
        adPanelService.adPanelDelete(adPanelEntity);
    }

}
