package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsGiftDto;
import com.cps.cpsService.entity.CpsCampaignFavoritesEntity;
import com.cps.cpsService.entity.CpsGiftBrandEntity;
import com.cps.cpsService.entity.CpsGiftProductEntity;
import com.cps.cpsService.packet.CpsGiftPacket;
import com.cps.cpsService.repository.CpsGiftBrandRepository;
import com.cps.cpsService.repository.CpsGiftProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsGiftService {
	private final CpsGiftBrandRepository cpsGiftBrandRepository;
	private final CpsGiftProductRepository cpsGiftProductRepository;

	/**
	 * 캠페인 카테고리 등록, 수정
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsGiftDto.GiftBrandResponse> giftBrand(CpsGiftPacket.GiftInfo.GiftBrandRequest request) throws Exception {
		CpsGiftPacket.GiftInfo.GiftBrandResponse response = new CpsGiftPacket.GiftInfo.GiftBrandResponse();

		CpsGiftBrandEntity cpsGiftBrandEntity = new CpsGiftBrandEntity();
		cpsGiftBrandEntity.setBrandId(request.getBrandId());
		cpsGiftBrandEntity.setMemberId(request.getMemberId());
		cpsGiftBrandEntity.setBrandName(request.getBrandName());
		cpsGiftBrandEntity.setMinCnt(request.getMinCnt());
		cpsGiftBrandEntity.setBrandYn(request.getBrandYn());

		CpsGiftDto.GiftBrandResponse giftBrandDto = new CpsGiftDto.GiftBrandResponse();
		giftBrandDto.setBrandId(request.getBrandId());
		giftBrandDto.setMemberId(request.getMemberId());
		giftBrandDto.setBrandName(request.getBrandName());
		giftBrandDto.setMinCnt(request.getMinCnt());
		giftBrandDto.setBrandYn(request.getBrandYn());

		try {
			if (request.getApiType().equals("D")) {
				cpsGiftBrandRepository.delete(cpsGiftBrandEntity);
			} else {
				cpsGiftBrandRepository.save(cpsGiftBrandEntity);
			}
			response.setSuccess();
			response.setData(giftBrandDto);
		} catch (Exception e) {
			if (request.getApiType().equals("D")) {
				response.setApiMessage(Constants.GIFT_BRAND_DELETE_EXCEPTION.getCode(), Constants.GIFT_BRAND_DELETE_EXCEPTION.getValue());
			} else if (request.getApiType().equals("U")) {
				response.setApiMessage(Constants.GIFT_BRAND_UPDATE_EXCEPTION.getCode(), Constants.GIFT_BRAND_UPDATE_EXCEPTION.getValue());
			} else {
				response.setApiMessage(Constants.GIFT_BRAND_EXCEPTION.getCode(), Constants.GIFT_BRAND_EXCEPTION.getValue());
			}
			log.error(Constant.EXCEPTION_MESSAGE + " giftBrand api request : {}, exception : {}", request, e);
		}

		return response;
	}

	/**
	 * 캠페인 카테고리 등록, 수정
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsGiftDto.GiftProductResponse> giftProduct(CpsGiftPacket.GiftInfo.GiftProductRequest request) throws Exception {
		CpsGiftPacket.GiftInfo.GiftProductResponse response = new CpsGiftPacket.GiftInfo.GiftProductResponse();

		CpsGiftProductEntity cpsGiftProductEntity = new CpsGiftProductEntity();
		cpsGiftProductEntity.setBrandId(request.getBrandId());
		cpsGiftProductEntity.setProductId(request.getProductId());
		cpsGiftProductEntity.setMemberId(request.getMemberId());
		cpsGiftProductEntity.setProductName(request.getProductName());
		cpsGiftProductEntity.setProductYn(request.getProductYn());

		CpsGiftDto.GiftProductResponse giftProductDto = new CpsGiftDto.GiftProductResponse();
		giftProductDto.setBrandId(request.getBrandId());
		giftProductDto.setProductId(request.getProductId());
		giftProductDto.setMemberId(request.getMemberId());
		giftProductDto.setProductName(request.getProductName());
		giftProductDto.setProductId(request.getProductId());

		try {
			if (request.getApiType().equals("D")) {
				cpsGiftProductRepository.delete(cpsGiftProductEntity);
			} else {
				cpsGiftProductRepository.save(cpsGiftProductEntity);
			}
			response.setSuccess();
			response.setData(giftProductDto);
		} catch (Exception e) {
			if (request.getApiType().equals("D")) {
				response.setApiMessage(Constants.GIFT_PRODUCT_DELETE_EXCEPTION.getCode(), Constants.GIFT_PRODUCT_DELETE_EXCEPTION.getValue());
			} else if (request.getApiType().equals("U")) {
				response.setApiMessage(Constants.GIFT_PRODUCT_UPDATE_EXCEPTION.getCode(), Constants.GIFT_PRODUCT_UPDATE_EXCEPTION.getValue());
			} else {
				response.setApiMessage(Constants.GIFT_PRODUCT_EXCEPTION.getCode(), Constants.GIFT_PRODUCT_EXCEPTION.getValue());
			}
			log.error(Constant.EXCEPTION_MESSAGE + " giftProduct api request : {}, exception : {}", request, e);
		}

		return response;
	}
	/**
	 * 기프트 브랜드 리스트
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsGiftDto.GiftBrandResponse> giftBrandList(CpsGiftPacket.GiftInfo.GiftBrandRequest request) throws Exception {
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
