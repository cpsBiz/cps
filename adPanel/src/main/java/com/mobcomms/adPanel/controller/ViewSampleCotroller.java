package com.mobcomms.adPanel.controller;

import com.mobcomms.adPanel.dto.AdPanelDto;
import com.mobcomms.adPanel.model.AdPanelModel;
import com.mobcomms.adPanel.service.AdPanelService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ViewSampleCotroller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdPanelService adPanelService;

    private static final Marker MARKER_PANNEL = MarkerFactory.getMarker("PANEL");

    @GetMapping("/index")
    public String greeting(@RequestParam(name="val", required=false, defaultValue="Sample") String val, Model model) {
        model.addAttribute("val", val);
        return "index";
    }

    @GetMapping("/ad-panel")
    public String adPanel(@ModelAttribute AdPanelModel adPanelModel) throws Exception {
        logger.warn(MARKER_PANNEL, "Start");
        List<AdPanelDto> adPanelList = adPanelService.adPanelList();
        String returnUrl = "adPanel";

        try {
            for(AdPanelDto adPanelDto : adPanelList.stream()
                                                   .filter(panel-> panel.osType().equals(adPanelModel.getOs()) && panel.useYn().equals("Y"))
                                                   .sorted(Comparator.comparingInt(panel -> Integer.parseInt(panel.viewIndex())))
                                                   .collect(Collectors.toList())) {

                String zoneId = adPanelDto.zoneId();
                String width = adPanelDto.width();
                String height = adPanelDto.height();
                String uri = "/api/banner/app/mobicomms/v1/hanamoney?zone="+zoneId+"&count=1&w="+width+"&h="+height+"&adid="+adPanelModel.getAdId();

                Mono<String> adPanelNoad = adPanelService.panelData(uri);

                String src = adPanelNoad.block().toString();

                if(src.isEmpty() == false){
                    src = src.replaceAll(" ","");
                    if(src.toLowerCase().contains("value=\"noad\"")) {
                        logger.warn(MARKER_PANNEL, "ZoonID : " + zoneId + " Is No AD!!!");
                    } else {
                        adPanelModel.setZoneId(zoneId);
                        adPanelModel.setWidth(width);
                        adPanelModel.setHeight(height);
                        returnUrl = urlChk(width);
                        break;
                    }
                } else {
                    logger.warn(MARKER_PANNEL, "mobiwith response data is null!!!");
                }
            }
        } catch (Exception e) {
            logger.warn(MARKER_PANNEL, "adPanel Exception : " + e.toString());
        }

        return returnUrl;
    }

    public String urlChk(String width){
        String returnUrl = "adPanel";
        if (width.equals("250")) {
            returnUrl = "adPanel250_250";
        }
        return returnUrl;
    }
}
