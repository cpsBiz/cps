package com.cps.api.service;

import com.cps.common.entity.CpsGiftEntity;
import com.cps.common.packet.CpsGiftPacket;
import com.cps.common.repository.CpsGiftHistoryRepository;
import com.cps.common.repository.CpsGiftRepository;
import com.cps.common.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GiftiShowService {
	@Value("${giftishow.domain.url}") String giftishowDomain;

	@Value("${giftishow.auth.code}") String giftishowAuthCode;

	@Value("${giftishow.auth.token}") String giftishowAuthToken;

	@Value("${giftishow.user.id}") String giftishowUserId;

	@Value("${giftishow.api.code}") String giftishowApiCode;

	private final HttpService httpService;

	private final CpsGiftRepository cpsGiftRepository;

	private final CpsGiftHistoryRepository cpsGiftHistoryRepository;


	public void giftiShowBizProduct() {
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("api_code", giftishowApiCode);
		requestParams.add("custom_auth_code", giftishowAuthCode);
		requestParams.add("custom_auth_token",giftishowAuthToken);
		requestParams.add("dev_yn", "N");
		requestParams.add("start", "1");
		requestParams.add("size", "2000");

		try{
			CpsGiftPacket.GiftInfo.ShowBizListResponse showBizListResponse =  httpService.SendGiftiShowBiz(giftishowDomain, requestParams);
			List<CpsGiftEntity> CpsGiftEntityList = new ArrayList<>();

			if (null != showBizListResponse) {
				if(showBizListResponse.getResult().getListNum() > 0 ) {
					if ("0000".equals(showBizListResponse.getCode())) {
						showBizListResponse.getResult().getGoodsList().forEach(product->{
							CpsGiftEntity cpsGiftEntity = new CpsGiftEntity();
							cpsGiftEntity.setBrandId(product.getBrandCode());
							cpsGiftEntity.setProductId(product.getGoodsCode());
							cpsGiftEntity.setBrandName(product.getBrandName());
							cpsGiftEntity.setProductName(product.getGoodsName());
							cpsGiftEntity.setProductImageS(product.getGoodsImgS());
							cpsGiftEntity.setProductImageL(product.getGoodsImgB());
							cpsGiftEntity.setBrandIcon(product.getBrandIconImg());
							cpsGiftEntity.setRealPrice(product.getRealPrice());
							cpsGiftEntity.setSalePrice(product.getSalePrice());
							cpsGiftEntity.setDiscountRate(product.getDiscountRate());
							cpsGiftEntity.setDiscountPrice(product.getDiscountPrice());
							cpsGiftEntity.setLimitDay(product.getLimitDay());
							cpsGiftEntity.setValidDay(product.getValidPrdDay());
							cpsGiftEntity.setContent(product.getContent());
							cpsGiftEntity.setEndDay( Integer.parseInt(
									ZonedDateTime.parse(product.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))
											.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
							));
							CpsGiftEntityList.add(cpsGiftEntity);
						});
						cpsGiftRepository.saveAll(CpsGiftEntityList);
					}
				}
			}
		} catch (Exception e){
			log.error("SendGiftiShowBiz Exception :" + e);
		}
	}

	public void giftiConEnd(int validDay){
		System.out.println("validDay : " + validDay);
		cpsGiftHistoryRepository.giftiConEnd(validDay);
	}
}
