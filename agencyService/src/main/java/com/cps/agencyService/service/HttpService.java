package com.cps.agencyService.service;

import com.cps.agencyService.packet.CpsDotPitchClickPacket;
import com.cps.common.servcies.BaseHttpService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 링크프라이스 광고주 API
 * @date 2024-09-23
 */

@Slf4j
@Service
public class HttpService extends BaseHttpService {
    public static final String DOMAIN = "";

    public HttpService() {
        super(DOMAIN);
    }

    public HttpService(String domain) {
        super(domain);
    }

    public HttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public HttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    //도트피치 클릭 url 호출
    public List<CpsDotPitchClickPacket.DotPitchClickInfo.DotPitchClickResponse> SendDotPitchClick(String domain, CpsDotPitchClickPacket.DotPitchClickInfo.DotPitchClickRequest request) {
        try {
            var result = this.GetAsync(domain, request, String.class).block();
            List<CpsDotPitchClickPacket.DotPitchClickInfo.DotPitchClickResponse> responseObj = new ObjectMapper().readValue(result, new TypeReference<List<CpsDotPitchClickPacket.DotPitchClickInfo.DotPitchClickResponse>>() {});
            return responseObj;
        } catch (Exception ex) {
            log.error("SendLinkPriceMerchant : {} ", ex);
            List<CpsDotPitchClickPacket.DotPitchClickInfo.DotPitchClickResponse> errorResult = new ArrayList<>();
            return errorResult;
        }
    }
}
