package com.mobcomms.hanapay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mobcomms.common.utils.DateTime;
import com.mobcomms.hanapay.dto.packet.BannerPacket;
import com.mobcomms.hanapay.entity.PointEntity;
import com.mobcomms.hanapay.entity.PointSettingEntity;
import com.mobcomms.hanapay.repository.PointRepository;
import com.mobcomms.hanapay.repository.PointSettingRepository;
import com.mobcomms.hanapay.service.CoupangHttpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequiredArgsConstructor
public class BannerController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CoupangHttpService cupangHttpService;
    private final PointSettingRepository pointSettingRepository;
    private final PointRepository pointRepository;

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

    private String serverType = System.getProperty("spring.profiles.active");

    /**
     * 기본 index.jsp 호출
     * @date 2024-08-19
     */
    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

        return "index";
    }


    /**
     * 광고 API 호출
     * @date 2024-08-19
     */
    @RequestMapping(value = "/api/v1/banner", method = {RequestMethod.GET})
    public String banner(@ModelAttribute BannerPacket.Banner.IndexRequest request) throws Exception{
        List<PointSettingEntity> pointSettingEntityList = pointSettingRepository.findAllByUseYN("Y") ;
        String useYn[] = new String[2];

        for (PointSettingEntity pointSettingEntity : pointSettingEntityList) {
            String point_type = String.valueOf(pointSettingEntity.getType());
            String point_value = String.valueOf(pointSettingEntity.getPoint());

            if ("1".equals(point_type)) {
                //일반 광고
                useYn[0] = String.valueOf(pointSettingEntity.getUseYN());
                request.setMobcommsPoint(point_value);
                request.setMobcommsUseYn(String.valueOf(pointSettingEntity.getUseYN()));
            } else if ("2".equals(point_type)) {
                //쿠팡 광고
                useYn[1] =String.valueOf(pointSettingEntity.getUseYN());
                request.setCoupangUseYn(String.valueOf(pointSettingEntity.getUseYN()));
                request.setCoupangPoint(point_value);
                request.setSubIdIos(subIdIos);
                request.setSubIdAos(subIdAos);
                request.setHouseIos("houseIos");
                request.setHouseAos("houseAos");
            }
        }

        String searchType = searchType(useYn);

        if(request.getUserKey() != null && !"".equals(request.getUserKey())){
            String regDateNum = DateTime.getCurrDate();
            // 사용자 포인트 적립 내역 조회
            List<PointEntity> pointEntitiesList = pointRepository.findAllByUserIdAndRegDateNumAndCodeAndType(request.getUserKey(), regDateNum, "1", searchType);
            request.setResPointCnt(pointEntitiesList.size());
        } else {
            request.setResPointCnt(0);
        }

        request.setServerType(serverType);

        return "index";
    }

    /**
     * 쿠팡파트너스 상품 리스트 조회
     * @date 2024-08-19
     */
    @GetMapping(value = "/view/coupang")
    public String coupang(BannerPacket.Banner.CoupangRequest coupangRequest, Model model) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode newJsonArray = objectMapper.createArrayNode();

        String subId = subIdIos;
        if ("aos".equals(coupangRequest.getOs())) {
            subId = subIdAos;
        }

        //adid 가 있을 시 쿠팡 API 호출
        if (coupangRequest.getAdId() != null && coupangRequest.getOs() != null) {
            try {
                coupangRequest.setEndPoint(String.format(reco_url, coupangRequest.getAdId(), subId, "300x250"));

                coupangRequest.setAuthorization(generate("GET", coupangRequest.getEndPoint(), secret_key, access_key));
                BannerPacket.Banner.Response  response = cupangHttpService.SendCoupang(coupangRequest);

                if(response.getData().size() == 0){
                    newJsonArray.add(objectMapper.createObjectNode()
                            .put("productImage", "/hana/hanapay/img/250x250.png")
                            .put("productUrl", "https://www.mobon.net/bridge"));
                    model.addAttribute("mobon", "Y");
                    model.addAttribute("coupang_point", coupangRequest.getCoupangPoint());
                    log.error("쿠팡 AJAX res_data null: " + response);
                }else{
                    response.getData().forEach(jsonObject -> {
                        newJsonArray.add(objectMapper.createObjectNode()
                                .put("productImage", jsonObject.getProductImage())
                                .put("productUrl", jsonObject.getProductUrl()));
                    });

                }
            } catch (Exception e) {
                log.error("쿠팡 AJAX Exception : " + e);
            }
        } else {
            newJsonArray.add(objectMapper.createObjectNode()
                    .put("productImage", "/hana/hanapay/img/250x250.png")
                    .put("productUrl", "https://www.mobon.net/bridge"));
            model.addAttribute("coupang_point", coupangRequest.getCoupangPoint());
            model.addAttribute("mobon", "Y");
        }

        model.addAttribute("coupang_data", objectMapper.writeValueAsString(newJsonArray));

        return "coupang";
    }

    @PostMapping(value = "/api/v1/coupang_error_log")
    public String coupangErrorLog(@RequestBody String data) throws Exception{
        log.error("쿠팡 AJAX : " + data);
        return data.toString();
    }

    public String searchType(String useYn[]){
        String searchType = "1";
        if (useYn[0].equals("N") && useYn[1].equals("Y")) {
            searchType = "2";
        }
        return searchType;
    }

    /**
     * 공통 쿠팡 파트너스 HMAC 서명 생성 :: 쿠팡에 직접 호출
     * @date 2024-08-19
     */
    private String generate(String method, String url, String secret_key, String access_key) {
        String[] parts = url.split("\\?");
        if (parts.length > 2) {
            throw new RuntimeException("incorrect uri format");
        } else {
            String path = parts[0];
            String query = "";
            if (parts.length == 2) {
                query = parts[1];
            }

            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyMMdd'T'HHmmss'Z'");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
            String datetime = dateFormatGmt.format(new Date());
            String message = datetime + method + path + query;

            String signature;
            try {
                SecretKeySpec signingKey = new SecretKeySpec(secret_key.getBytes(Charset.forName("UTF-8")), "HmacSHA256");
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(signingKey);
                byte[] rawHmac = mac.doFinal(message.getBytes(Charset.forName("UTF-8")));
                signature = Hex.encodeHexString(rawHmac);
            } catch (GeneralSecurityException e) {
                throw new IllegalArgumentException("Unexpected error while creating hash: " + e.getMessage(), e);
            }

            return String.format("CEA algorithm=%s, access-key=%s, signed-date=%s, signature=%s", "HmacSHA256", access_key, datetime, signature);
        }
    }

}
