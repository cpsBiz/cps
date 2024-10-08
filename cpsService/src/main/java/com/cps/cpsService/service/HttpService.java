package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.servcies.BaseHttpService;
import com.cps.common.utils.HmacGenerator;
import com.cps.cpsService.packet.CpsMemberPacket;
import com.cps.cpsService.packet.CpsRewardPacket;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 링크프라이스 광고주 API
 * @date 2024-09-03
 */

@Slf4j
@Service
public class HttpService extends BaseHttpService {
    public static final String DOMAIN = "";

    public static final String ACCESS_KEY = "6566d8b3-141d-4bde-8de9-606126385670";
    public static final String SECRET_KEY = "dcfcadfdd0e4cf4610fd08b886859f975fde5a8f";

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

    //도트피치 익일CPS, 매출 취소 내역 호출
    public CpsRewardPacket.RewardInfo.DotPitchListResponse SendDotPitchReward(String domain, CpsRewardPacket.RewardInfo.DotPitchRequest request) {
        CpsRewardPacket.RewardInfo.DotPitchListResponse responseObj = new CpsRewardPacket.RewardInfo.DotPitchListResponse();
        try{
            var result = this.GetAsync(domain, request, String.class);

            String json = "{\n" +
                    "  \"searchDate\": \"20240101\",\n" +
                    "  \"result\": \"OK\",\n" +
                    "  \"listData\": [\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924001\",\n" +
                    "      \"m_name\": \"11번가_PC\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"10000\",\n" +
                    "      \"comm\": 1000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"91\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924002\",\n" +
                    "      \"m_name\": \"11번가_PC\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"20000\",\n" +
                    "      \"comm\": 2000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"91\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924003\",\n" +
                    "      \"m_name\": \"11번가_PC\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"30000\",\n" +
                    "      \"comm\": 3000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"91\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924004\",\n" +
                    "      \"m_name\": \"11번가_PC\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"40000\",\n" +
                    "      \"comm\": 4000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"91\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924005\",\n" +
                    "      \"m_name\": \"11번가_PC\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"50000\",\n" +
                    "      \"comm\": 5000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"91\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            // listData 유무 체크
            Map<String, Object> responseMap = new ObjectMapper().readValue(result.block(), new TypeReference<Map<String, Object>>() {});
            if (responseMap.containsKey("listData")) {
                Object listData = responseMap.get("listData");
                if (listData instanceof List && !((List<?>) listData).isEmpty()) {
                    // listData를 DotData 리스트로 변환
                    List<CpsRewardPacket.RewardInfo.DotData> dotDataList = new ObjectMapper().convertValue(listData, new TypeReference<List<CpsRewardPacket.RewardInfo.DotData>>() {});
                   // DotPitchListResponse 객체 생성
                    responseObj = new CpsRewardPacket.RewardInfo.DotPitchListResponse();
                    responseObj.setSearchDate((String) responseMap.get("searchDate"));
                    responseObj.setResult((String) responseMap.get("result"));
                    responseObj.setListData(dotDataList);
                }
            }
            return responseObj;
        } catch (Exception ex) {
            log.error(Constant.EXCEPTION_MESSAGE + " SendDotPitchReward request : {}, exception : {}", request, ex);
            CpsRewardPacket.RewardInfo.DotPitchListResponse errorResult = new CpsRewardPacket.RewardInfo.DotPitchListResponse();
            errorResult.setResult("9999");
            errorResult.setListData(null);
            return errorResult;
        }
    }

    public List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> SendUserLinkPriceMerchant(CpsMemberPacket.MemberInfo.Domain request) {
        try{
            var result = linkPriceDetail(request.getDomain());
            //var result = this.PostAsync(request.getDomain(), request, String.class);
            List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> responseObj = new ObjectMapper().readValue(result, new TypeReference<List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse>>() {});
            return responseObj;
        } catch (Exception ex) {
            log.error(Constant.EXCEPTION_MESSAGE + " SendUserLinkPriceMerchant request : {}, exception : {}", request, ex);
            List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> errorResult = new ArrayList<>();
            return errorResult;
        }
    }

    public  String linkPriceDetail(String Domain) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;
        String responseLine;

        try {
            URL url = new URL(Domain);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response.toString();
    }

    //링크프라이스 실적조회
    public CpsRewardPacket.RewardInfo.LinkPriceListResponse SendLinkPriceReward(String domain, CpsRewardPacket.RewardInfo.LinkPriceRequest request) {
        CpsRewardPacket.RewardInfo.LinkPriceListResponse responseObj = new CpsRewardPacket.RewardInfo.LinkPriceListResponse();
        try{
            var result = this.GetAsync(domain, request, String.class);
            // order_list 유무 체크
            Map<String, Object> responseMap = new ObjectMapper().readValue(result.block(), new TypeReference<Map<String, Object>>() {});
            if (responseMap.containsKey("order_list")) {
                Object orderList = responseMap.get("order_list");
                if (orderList instanceof List && !((List<?>) orderList).isEmpty()) {
                    // order_list LinkData 리스트로 변환
                    List<CpsRewardPacket.RewardInfo.LinkData> linkDataList = new ObjectMapper().convertValue(orderList, new TypeReference<List<CpsRewardPacket.RewardInfo.LinkData>>() {});
                    // LinkPriceListResponse 객체 생성
                    responseObj = new CpsRewardPacket.RewardInfo.LinkPriceListResponse();
                    responseObj.setList_count(String.valueOf(responseMap.get("list_count")));
                    responseObj.setResult(String.valueOf(responseMap.get("result")));
                    responseObj.setOrder_list(linkDataList);
                }
            }
            return responseObj;
        } catch (Exception ex) {
            log.error(Constant.EXCEPTION_MESSAGE + " SendLinkPriceReward request : {}, exception : {}", request, ex);
            CpsRewardPacket.RewardInfo.LinkPriceListResponse errorResult = new CpsRewardPacket.RewardInfo.LinkPriceListResponse();
            errorResult.setResult("9999");
            errorResult.setOrder_list(null);
            return errorResult;
        }
    }

    //쿠팡 실적조회
    public CpsRewardPacket.RewardInfo.CoupangListResponse SendCoupangReward(String url, String domain, CpsRewardPacket.RewardInfo.CoupangRequest request) {
        CpsRewardPacket.RewardInfo.CoupangListResponse responseObj = new CpsRewardPacket.RewardInfo.CoupangListResponse();
        String jsonString = "{\n" +
                "  \"rCode\": \"0\",\n" +
                "  \"rMessage\": \"\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"date\": \"20190307\",\n" +
                "      \"trackingCode\": \"AF1234567\",\n" +
                "      \"subId\": \"A1234567890\",\n" +
                "      \"addtag\": \"400\",\n" +
                "      \"ctag\": \"Home\",\n" +
                "      \"orderId\": 12345678901234,\n" +
                "      \"productId\": 1234567,\n" +
                "      \"productName\": \"상품명\",\n" +
                "      \"quantity\": 1,\n" +
                "      \"gmv\": 4450,\n" +
                "      \"commissionRate\": 3,\n" +
                "      \"commission\": 267,\n" +
                "      \"subParam\": 91\n" +
                "    },\n" +
                "    {\n" +
                "      \"date\": \"20190307\",\n" +
                "      \"trackingCode\": \"AF1234567\",\n" +
                "      \"subId\": \"A1234567890\",\n" +
                "      \"addtag\": \"400\",\n" +
                "      \"ctag\": \"Home\",\n" +
                "      \"orderId\": 12345678901234,\n" +
                "      \"productId\": 12345671,\n" +
                "      \"productName\": \"상품명1\",\n" +
                "      \"quantity\": 4,\n" +
                "      \"gmv\": 8900,\n" +
                "      \"commissionRate\": 3,\n" +
                "      \"commission\": 267,\n" +
                "      \"subParam\": 91\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        try{
            String authorization = HmacGenerator.generate("GET", domain, SECRET_KEY, ACCESS_KEY);
            var result = this.GetAsync(url, request, String.class, httpHeaders -> {
                httpHeaders.add("Authorization", authorization);
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            });

            // order_list 유무 체크
            Map<String, Object> responseMap = new ObjectMapper().readValue(jsonString, new TypeReference<Map<String, Object>>() {});
            if (responseMap.containsKey("data")) {
                Object data = responseMap.get("data");
                if (data instanceof List && !((List<?>) data).isEmpty()) {
                    // dataList coupangDataList 리스트로 변환
                    List<CpsRewardPacket.RewardInfo.CoupangData> coupangDataList = new ObjectMapper().convertValue(data, new TypeReference<List<CpsRewardPacket.RewardInfo.CoupangData>>() {});
                    // CoupangListResponse 객체 생성
                    responseObj = new CpsRewardPacket.RewardInfo.CoupangListResponse();
                    responseObj.setRMessage(String.valueOf(responseMap.get("rMessage")));
                    responseObj.setRCode(String.valueOf(responseMap.get("rCode")));
                    responseObj.setDataList(coupangDataList);
                }
            }

            return responseObj;
        } catch (Exception ex) {
            log.error(Constant.EXCEPTION_MESSAGE + " SendCoupangReward request : {}, exception : {}", request, ex);
            CpsRewardPacket.RewardInfo.CoupangListResponse errorResult = new CpsRewardPacket.RewardInfo.CoupangListResponse();
            errorResult.setResult("9999");
            errorResult.setDataList(null);
            return errorResult;
        }
    }
}
