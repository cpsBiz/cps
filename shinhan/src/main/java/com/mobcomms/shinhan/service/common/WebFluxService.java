package com.mobcomms.shinhan.service.common;


import io.netty.channel.ChannelOption;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@Service
public class WebFluxService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String AUTHORIZATION = "Authorization";
	private static final String ALGORITHM = "HmacSHA256";
	private static final Charset STANDARD_CHARSET = Charset.forName("UTF-8");
	private static final String REQUEST_METHOD = "GET";
	private static final Marker MARKER_SHINHAN = MarkerFactory.getMarker("SHINHAN");

	/**
	 * 공통 쿠팡 파트너스 API 외부 연동 :: 쿠팡에 직접 호출
	 * @date 2024-06-19
	 */
	@SuppressWarnings("unchecked")
	public JSONObject coupangApi(String domain, String uri, String access_key, String secret_key) throws IOException {

		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10*1024*1024))
				.build();

		WebClient client = WebClient.builder()
				.baseUrl(domain)
				.exchangeStrategies(exchangeStrategies)
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();

		//HMAC 서명 생성
		String authorization = generate(REQUEST_METHOD, uri, secret_key, access_key);

		String result = client.get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.header(AUTHORIZATION, authorization)
				.retrieve()
				.bodyToMono(String.class).block();

		JSONObject jObject = new JSONObject();
		try {
			JSONObject response = new JSONObject(result.toString());
			JSONArray dataArray = response.getJSONArray("data");
			JSONArray filteredArray = new JSONArray();

			for (int i = 0; i < dataArray.length(); i++) {
				JSONObject originalObject = dataArray.getJSONObject(i);
				JSONObject filteredObject = new JSONObject();

				filteredObject.put("productImage", originalObject.getString("productImage"));
				filteredObject.put("productUrl", originalObject.getString("productUrl"));

				filteredArray.put(filteredObject);
			}

			jObject.put("data", filteredArray);
		}  catch (Exception e) {
			logger.warn(MARKER_SHINHAN, "쿠팡 파트너스 API Url : " + uri);
			logger.warn(MARKER_SHINHAN, "쿠팡 파트너스 API Result : " + result);
			e.printStackTrace();
		}

		return jObject;
	}

	/**
	 * 공통 쿠팡 파트너스 HMAC 서명 생성 :: 쿠팡에 직접 호출
	 * @date 2024-06-19
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

	public String commonAdApi(String domain, String uri) throws Exception{
		logger.info("commonAdApi API 시작");

		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10*1024*1024))
				.build();

		WebClient client = WebClient.builder()
				.baseUrl(domain)
				.exchangeStrategies(exchangeStrategies)
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();

		String result = client.get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(String.class).block();

		String str = "";
		try {
			str = result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}
}
