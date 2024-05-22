package com.mobcomms.adPanel.controller;

import com.mobcomms.adPanel.dto.AdPanelDto;
import com.mobcomms.adPanel.service.AdPanelService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewSampleCotroller {

    private final AdPanelService adPanelService;

    @GetMapping("/index")
    public String greeting(@RequestParam(name="val", required=false, defaultValue="Sample") String val, Model model) {
        model.addAttribute("val", val);
        return "index";
    }


    @RequestMapping("/adPanel")
    public String adPanel(@RequestParam(name="val", required=false, defaultValue="Sample") String val, Model model) {
        List<AdPanelDto> adPanelList = adPanelService.adPanelList();

        model.addAttribute("adPanelList", adPanelList);
        model.addAttribute("listSize", adPanelList.size());
        return "adPanel";
    }
}
