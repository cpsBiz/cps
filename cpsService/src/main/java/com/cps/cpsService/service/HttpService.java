package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.servcies.BaseHttpService;
import com.cps.common.utils.HmacGenerator;
import com.cps.cpsService.packet.CpsGiftPacket;
import com.cps.cpsService.packet.CpsMemberPacket;
import com.cps.cpsService.packet.CpsRewardPacket;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 링크프라이스 광고주 API
 * @date 2024-09-03
 */

@Slf4j
@Service
public class HttpService extends BaseHttpService {

    public static final String ACCESS_KEY = "6566d8b3-141d-4bde-8de9-606126385670";
    public static final String SECRET_KEY = "dcfcadfdd0e4cf4610fd08b886859f975fde5a8f";

    //도트피치 익일CPS, 매출 취소 내역 호출
    public CpsRewardPacket.RewardInfo.DotPitchListResponse SendDotPitchReward(String domain, CpsRewardPacket.RewardInfo.DotPitchRequest request) {
        CpsRewardPacket.RewardInfo.DotPitchListResponse responseObj = new CpsRewardPacket.RewardInfo.DotPitchListResponse();
        try{
            var result = this.GetAsync(domain, request, String.class);
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
            List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> responseObj = new ObjectMapper().readValue(result, new TypeReference<List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse>>() {});
            return responseObj;
        } catch (Exception ex) {
            log.error(Constant.EXCEPTION_MESSAGE + " SendUserLinkPriceMerchant request : {}, exception : {}", request, ex);
            List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> errorResult = new ArrayList<>();
            return errorResult;
        }
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

        try{
            String authorization = HmacGenerator.generate("GET", domain, SECRET_KEY, ACCESS_KEY);
            var result = this.GetAsync(url+domain, String.class, httpHeaders -> {
                httpHeaders.add("Authorization", authorization);
            });

            // order_list 유무 체크
            Map<String, Object> responseMap = new ObjectMapper().readValue(result.block(), new TypeReference<Map<String, Object>>() {});
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

    public String linkPriceDetail(String Domain) {
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

    public CpsGiftPacket.GiftInfo.ShowBizListResponse SendGiftiShowBiz(String url, MultiValueMap<String, String> request) {
        CpsGiftPacket.GiftInfo.ShowBizListResponse responseObj = new CpsGiftPacket.GiftInfo.ShowBizListResponse();

        try{
            var result = this.PostAsync(url, request, String.class, httpHeaders -> {
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            });
            Map<String, Object> responseMap = new ObjectMapper().readValue(result.block(), new TypeReference<Map<String, Object>>() {});
            Map<String, Object> resultMap = (Map<String, Object>) responseMap.get("result");

            if (resultMap.containsKey("goodsList")) {
                Object listData = resultMap.get("goodsList");
                if (listData instanceof List && !((List<?>) listData).isEmpty()) {
                    List<CpsGiftPacket.GiftInfo.ShowBizData> bizDataList = new ObjectMapper().convertValue(listData, new TypeReference<List<CpsGiftPacket.GiftInfo.ShowBizData>>() {});
                    if (responseObj.getResult() == null) {
                        responseObj.setResult(new CpsGiftPacket.GiftInfo.Result());
                    }
                    responseObj.getResult().setGoodsList(bizDataList);
                    responseObj.getResult().setListNum((int) resultMap.get("listNum"));
                    responseObj.setCode((String) responseMap.get("code"));
                }
            }
            return responseObj;
        } catch (Exception ex) {
            log.error(Constant.EXCEPTION_MESSAGE + " SendCoupangReward request : {}, exception : {}", request, ex);
            CpsGiftPacket.GiftInfo.ShowBizListResponse errorResult = new CpsGiftPacket.GiftInfo.ShowBizListResponse();
            return errorResult;
        }
    }

    public CpsGiftPacket.GiftInfo.ShowBizListCouponResponse SendGiftiShowBizCoupon(String url, MultiValueMap<String, String> request) {
        try{
            var result = this.PostAsync(url, request, CpsGiftPacket.GiftInfo.ShowBizListCouponResponse.class, httpHeaders -> {
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            });

            return result.block();
        } catch (Exception ex) {
            log.error(Constant.EXCEPTION_MESSAGE + " SendGiftiShowBizCoupon request : {}, exception : {}", request, ex);
            CpsGiftPacket.GiftInfo.ShowBizListCouponResponse errorResult = new CpsGiftPacket.GiftInfo.ShowBizListCouponResponse();
            errorResult.setCode("9999");
            return errorResult;
        }
    }
}
