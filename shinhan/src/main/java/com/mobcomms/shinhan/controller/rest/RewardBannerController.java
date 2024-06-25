package com.mobcomms.shinhan.controller.rest;

import com.mobcomms.shinhan.common.util.TextUtil;
import com.mobcomms.shinhan.service.common.WebFluxService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RewardBannerController {

    /*private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebFluxService webFluxService;

    @Value("${coupang.domain.url}")
    private String domain_url;
    @Value("${coupang.reco.url}")
    private String reco_url;
    @Value("${coupang.access.key}")
    private String access_key;
    @Value("${coupang.secret.key}")
    private String secret_key;
    @Value("${coupang.subId.aos}")
    private String subIdAos;
    @Value("${coupang.subId.ios}")
    private String subIdIos;
    @Value("${mobwith.domain.url}")
    private String mobwithDomain;
    @Value("${mobwith.param.url}")
    private String mobwithParam;
    @Value("${dev.zone.point.aos}")
    private String zonePointAos;
    @Value("${dev.zone.point.ios}")
    private String zonePointIos;

    @GetMapping("/banner/coupang")
    public Map<String, Object> coupang(
            @RequestParam(value = "adid", required = false) String adid,
            @RequestParam(value = "os", required = false) String os,
            HttpServletRequest request) throws Exception {

        Map<String, Object> resultJson = new HashMap<>();

        if (adid != null && os != null) {
            String subId = "aos".equals(os) ? subIdAos : subIdIos;
            String uri = String.format(reco_url, adid, subId, "300x100");

            try {
                JSONObject responseJsonObj = webFluxService.coupangApi(domain_url, uri, access_key, secret_key);
                JSONArray dataJsonArray = responseJsonObj.getJSONArray("data");

                List<Map<String, Object>> resultList = new ArrayList<>();
                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject jsonObject = dataJsonArray.getJSONObject(i);
                    resultList.add(jsonObject.toMap());
                }

                resultJson.put("data", resultList.isEmpty() ? null : resultList);

            } catch (Exception e) {
                resultJson.put("error", "Failed Coupang API: " + e.getMessage());
            }
        }
        return resultJson;
    }

    @GetMapping(value = "/banner/mobwith")
    public Map<String, Object> mobwith(
            @RequestParam(value = "zone", required = false) String zone,
            @RequestParam(value = "adid", required = false) String adid,
            @RequestParam(value = "os", required = false) String os,
            HttpServletRequest request) throws Exception {

        logger.info("Call Rest Api: [" + request.getMethod() + "] " + request.getRequestURL() + "?" + request.getQueryString());

        Map<String, Object> resultJson = new HashMap<>();

        if (zone != null) {
            if (TextUtil.isNull(adid)) {
                adid = "7e17b2d4-3a16-413f-ae12-124010abf9c7";
            }
            String uri = String.format(mobwithParam, zone, adid);
            String resultStr = webFluxService.commonAdApi(mobwithDomain, uri);

            JSONObject jsonObject = new JSONObject(resultStr);
            resultJson = jsonObject.toMap();

            if ("1204".equals(jsonObject.getString("code"))
                    && jsonObject.isNull("data")
                    && "NO AD".equals(jsonObject.getString("message"))) {
                String coupangUri = String.format(reco_url, adid, os != null && os.equals("aos") ? subIdAos : subIdIos, "300x100");
                JSONObject coupangResponseJsonObj = webFluxService.coupangApi(domain_url, coupangUri, access_key, secret_key);
                resultJson.put("coupang", coupangResponseJsonObj.toMap());
            }

        } else {
            resultJson.put("message", "zone is null.");
        }

        return resultJson;
    }*/
}
