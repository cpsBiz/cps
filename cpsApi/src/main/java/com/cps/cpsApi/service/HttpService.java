package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.servcies.BaseHttpService;
import com.cps.cpsApi.packet.CpsRewardPacket;
import com.cps.cpsApi.packet.CpsUserPacket;
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
                    "      \"m_name\": \"11번가\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"10000\",\n" +
                    "      \"comm\": 1000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"51\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924002\",\n" +
                    "      \"m_name\": \"11번가\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"20000\",\n" +
                    "      \"comm\": 2000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"54\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924003\",\n" +
                    "      \"m_name\": \"11번가\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"30000\",\n" +
                    "      \"comm\": 3000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"55\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924004\",\n" +
                    "      \"m_name\": \"11번가\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"40000\",\n" +
                    "      \"comm\": 4000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"59\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"orderid\": \"20240924005\",\n" +
                    "      \"m_name\": \"11번가\",\n" +
                    "      \"aff_id\": \"test1\",\n" +
                    "      \"p_name\": \"테스트상품1\",\n" +
                    "      \"quantity\": \"7\",\n" +
                    "      \"price\": \"50000\",\n" +
                    "      \"comm\": 5000,\n" +
                    "      \"commission_rate\": \"80\",\n" +
                    "      \"a_info\": \"60\",\n" +
                    "      \"order_flag\": \"확정\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            // listData 유무 체크
            Map<String, Object> responseMap = new ObjectMapper().readValue(json, new TypeReference<Map<String, Object>>() {});
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

    public List<CpsUserPacket.UserInfo.LinkPriceAgencyResponse> SendUserLinkPriceMerchant(CpsUserPacket.UserInfo.Domain request) {
        try{
            var result = linkPriceDetail(request.getDomain());
            //var result = this.PostAsync(request.getDomain(), request, String.class);
            List<CpsUserPacket.UserInfo.LinkPriceAgencyResponse> responseObj = new ObjectMapper().readValue(result, new TypeReference<List<CpsUserPacket.UserInfo.LinkPriceAgencyResponse>>() {});
            return responseObj;
        } catch (Exception ex) {
            log.error(Constant.EXCEPTION_MESSAGE + " SendUserLinkPriceMerchant request : {}, exception : {}", request, ex);
            List<CpsUserPacket.UserInfo.LinkPriceAgencyResponse> errorResult = new ArrayList<>();
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
            var result = this.PostAsync(domain, request, String.class);
            String json = "{"
                    + "\"result\": \"0\","
                    + "\"list_count\": 2,"
                    + "\"order_list\": ["
                    + "    {"
                    + "        \"trlog_id\": \"18000406750585\","
                    + "        \"m_id\": \"clickbuy\","
                    + "        \"o_cd\": \"recover_231019_182249\","
                    + "        \"p_cd\": \"recover_231019_182249\","
                    + "        \"p_nm\": \"pn_recover_5\","
                    + "        \"it_cnt\": \"1\","
                    + "        \"user_id\": \"u_id_recover_5\","
                    + "        \"status\": \"100\","
                    + "        \"c_cd\": \"test_c_cd\","
                    + "        \"create_time_stamp\": \"20231020\","
                    + "        \"applied_pgm_id\": \"0038\","
                    + "        \"yyyymmdd\": \"20231017\","
                    + "        \"hhmiss\": \"000000\","
                    + "        \"trans_comment\": \"\","
                    + "        \"sales\": 39900,"
                    + "        \"commission\": 1,"
                    + "        \"pgm_name\": \"tt_mo\","
                    + "        \"is_pc\": \"mobile\","
                    + "        \"pur_rate\": \"1원\""
                    + "    },"
                    + "    {"
                    + "        \"trlog_id\": \"18000406750586\","
                    + "        \"m_id\": \"clickbuy\","
                    + "        \"o_cd\": \"recover_231019_182243\","
                    + "        \"p_cd\": \"recover_231019_182243\","
                    + "        \"p_nm\": \"pn_recover_4\","
                    + "        \"it_cnt\": \"1\","
                    + "        \"user_id\": \"u_id_recover_4\","
                    + "        \"status\": \"100\","
                    + "        \"c_cd\": \"test_c_cd\","
                    + "        \"create_time_stamp\": \"20231020\","
                    + "        \"applied_pgm_id\": \"0038\","
                    + "        \"yyyymmdd\": \"20231017\","
                    + "        \"hhmiss\": \"000000\","
                    + "        \"trans_comment\": \"\","
                    + "        \"sales\": 39900,"
                    + "        \"commission\": 1,"
                    + "        \"pgm_name\": \"tt_mo\","
                    + "        \"is_pc\": \"mobile\","
                    + "        \"pur_rate\": \"1원\""
                    + "    }"
                    + "]"
                    + "}";

            // order_list 유무 체크
            Map<String, Object> responseMap = new ObjectMapper().readValue(json, new TypeReference<Map<String, Object>>() {});
            if (responseMap.containsKey("order_list")) {
                Object orderList = responseMap.get("order_list");
                if (orderList instanceof List && !((List<?>) orderList).isEmpty()) {
                    // order_list LinkData 리스트로 변환
                    List<CpsRewardPacket.RewardInfo.LinkData> linkDataList = new ObjectMapper().convertValue(orderList, new TypeReference<List<CpsRewardPacket.RewardInfo.LinkData>>() {});
                    // LinkPriceListResponse 객체 생성
                    responseObj = new CpsRewardPacket.RewardInfo.LinkPriceListResponse();
                    responseObj.setList_count((String) responseMap.get("list_count"));
                    responseObj.setResult((String) responseMap.get("result"));
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
}
