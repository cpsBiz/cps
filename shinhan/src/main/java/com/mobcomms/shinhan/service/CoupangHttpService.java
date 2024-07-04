package com.mobcomms.shinhan.service;

import com.mobcomms.common.servcies.BaseHttpService;
import com.mobcomms.shinhan.dto.packet.CoupangPacket;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;

/*
 * Created by enliple
 * Create Date : 2024-06-28
 * Class 설명, method
 * UpdateDate : 2024-06-28, 업데이트 내용
 */
@Service
public class CoupangHttpService extends BaseHttpService {
    public static final String DOMAIN = "https://api-gateway.coupang.com";
    public static final String GET_AD_ENDPOINT="/v2/providers/affiliate_open_api/apis/openapi/v1/products/reco";
    private static final Charset STANDARD_CHARSET = Charset.forName("UTF-8");
    private static final String AUTHORIZATION = "Authorization";
    private static final String ALGORITHM = "HmacSHA256";
    private static final String REQUEST_METHOD = "GET";

    private static final String ACCESS_KEY = "37a19127-d99d-4415-b059-937ecaad7a85";
    private static final String SECRET_KEY = "eac2c8e2ccc7962f736b31b5f8a3f1c009e5212c";

    public CoupangHttpService() {
        super(DOMAIN);
    }

    public CoupangHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public CoupangHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    public CoupangPacket.GetCoupangAdInfo.Response getCoupangAdInfo(CoupangPacket.GetCoupangAdInfo.Request request){
        try{
            var generateUri = GET_AD_ENDPOINT+ String.format("?deviceId=%s&subId=%s&imageSize=%s",request.getDeviceId(),request.getSubId(),request.getImageSize());
            var authorization = generate(REQUEST_METHOD,generateUri,SECRET_KEY,ACCESS_KEY);

            var result = this.GetAsync(generateUri,null,CoupangPacket.GetCoupangAdInfo.Response.class,httpHeaders -> {
                httpHeaders.add(AUTHORIZATION,authorization);
            });
            return result.block();
        } catch (Exception ex){
            var error = new CoupangPacket.GetCoupangAdInfo.Response();
            error.setRCode("-9999");
            error.setRMessage(ex.getMessage());
            return error;
        }
    }


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
                SecretKeySpec signingKey = new SecretKeySpec(secret_key.getBytes(STANDARD_CHARSET), ALGORITHM);
                Mac mac = Mac.getInstance(ALGORITHM);
                mac.init(signingKey);
                byte[] rawHmac = mac.doFinal(message.getBytes(STANDARD_CHARSET));
                signature = Hex.encodeHexString(rawHmac);
            } catch (GeneralSecurityException e) {
                throw new IllegalArgumentException("Unexpected error while creating hash: " + e.getMessage(), e);
            }

            return String.format("CEA algorithm=%s, access-key=%s, signed-date=%s, signature=%s", "HmacSHA256", access_key, datetime, signature);
        }
    }

}
