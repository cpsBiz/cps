package com.cps.cpsViewApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsViewApi.dto.CpsCampaignFavoritesDto;
import com.cps.cpsViewApi.dto.CpsGiftDto;
import com.cps.cpsViewApi.entity.CpsCampaignFavoritesEntity;
import com.cps.cpsViewApi.packet.CpsCampaignFavoritesPacket;
import com.cps.cpsViewApi.packet.CpsGiftPacket;
import com.cps.cpsViewApi.repository.CpsCampaignFavoritesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsGiftService {

	private final CpsCampaignFavoritesRepository cpsCampaignFavoritesRepository;

	/**
	 * 기프트 브랜드 리스트
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsGiftDto> giftBrandList(CpsGiftPacket.GiftInfo.GiftBrandRequest request) throws Exception {
		CpsGiftPacket.GiftInfo.GiftBrandResponse response = new CpsGiftPacket.GiftInfo.GiftBrandResponse();
		CpsCampaignFavoritesEntity cpsCampaignFavoritesEntity = new CpsCampaignFavoritesEntity();


		try {


		}catch (Exception e){
			response.setApiMessage(Constants.GIFT_BRAND_SEARCH_EXCEPTION.getCode(), Constants.GIFT_BRAND_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " giftBrandList api request : {}, exception :  {}", request, e);
		}
		return response;
	}
}
