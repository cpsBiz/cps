package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsGiftBrandDto;
import com.cps.cpsService.dto.CpsGiftDto;
import com.cps.cpsService.dto.CpsGiftProductDto;
import com.cps.cpsService.entity.CpsGiftBrandEntity;
import com.cps.cpsService.entity.CpsGiftEntity;
import com.cps.cpsService.entity.CpsGiftProductEntity;
import com.cps.cpsService.packet.CpsGiftBrandPacket;
import com.cps.cpsService.packet.CpsGiftPacket;
import com.cps.cpsService.packet.CpsGiftProductPacket;
import com.cps.cpsService.repository.CpsGiftBrandRepository;
import com.cps.cpsService.repository.CpsGiftProductRepository;
import com.cps.cpsService.repository.CpsGiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsGiftService {

	private final CpsGiftRepository cpsGiftRepository;

	private final CpsGiftBrandRepository cpsGiftBrandRepository;

	private final CpsGiftProductRepository cpsGiftProductRepository;

	/**
	 * 기프트 브랜드 등록, 수정, 삭제
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsGiftDto.GiftBrandResponse> giftBrand(CpsGiftPacket.GiftInfo.GiftBrandRequest request) throws Exception {
		CpsGiftPacket.GiftInfo.GiftBrandResponse response = new CpsGiftPacket.GiftInfo.GiftBrandResponse();

		CpsGiftBrandEntity cpsGiftBrandEntity = new CpsGiftBrandEntity();
		cpsGiftBrandEntity.setBrandId(request.getBrandId());
		cpsGiftBrandEntity.setAffliateId(request.getAffliateId());
		cpsGiftBrandEntity.setBrandType(request.getBrandType());
		cpsGiftBrandEntity.setBrandName(request.getBrandName());
		cpsGiftBrandEntity.setBrandLogo(request.getBrandLogo());
		cpsGiftBrandEntity.setMinCnt(request.getMinCnt());
		cpsGiftBrandEntity.setBrandYn(request.getBrandYn());

		CpsGiftDto.GiftBrandResponse giftBrandDto = new CpsGiftDto.GiftBrandResponse();
		giftBrandDto.setBrandId(request.getBrandId());
		giftBrandDto.setAffliateId(request.getAffliateId());
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
	 * 상품 등록, 수정, 삭제
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsGiftDto.GiftProductResponse> giftProduct(CpsGiftPacket.GiftInfo.GiftProductRequest request) throws Exception {
		CpsGiftPacket.GiftInfo.GiftProductResponse response = new CpsGiftPacket.GiftInfo.GiftProductResponse();

		CpsGiftProductEntity cpsGiftProductEntity = new CpsGiftProductEntity();
		cpsGiftProductEntity.setBrandId(request.getBrandId());
		cpsGiftProductEntity.setProductId(request.getProductId());
		cpsGiftProductEntity.setAffliateId(request.getAffliateId());
		cpsGiftProductEntity.setProbabilities(request.getProbabilities());

		CpsGiftDto.GiftProductResponse giftProductDto = new CpsGiftDto.GiftProductResponse();
		giftProductDto.setBrandId(request.getBrandId());
		giftProductDto.setProductId(request.getProductId());
		giftProductDto.setAffliateId(request.getAffliateId());

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
	 * 기프트 상품 등록
	 * @date 2024-10-16
	 */
	public GenericBaseResponse<CpsGiftDto.GiftProductResponse> product(CpsGiftPacket.GiftInfo.CpsGiftRequest request) throws Exception {
		CpsGiftPacket.GiftInfo.GiftProductResponse response = new CpsGiftPacket.GiftInfo.GiftProductResponse();

		CpsGiftEntity cpsGiftEntity = new CpsGiftEntity();
		cpsGiftEntity.setBrandId(request.getBrandId());
		cpsGiftEntity.setProductId(request.getProductId());
		cpsGiftEntity.setBrandName(request.getBrandName());
		cpsGiftEntity.setProductName(request.getProductName());
		cpsGiftEntity.setProductImageS(request.getProductImageS());
		cpsGiftEntity.setProductImageL(request.getProductImageL());
		cpsGiftEntity.setBrandIcon(request.getBrandIcon());
		cpsGiftEntity.setRealPrice(request.getRealPrice());
		cpsGiftEntity.setSalePrice(request.getSalePrice());
		cpsGiftEntity.setDiscountRate(request.getDiscountRate());
		cpsGiftEntity.setDiscountPrice(request.getDiscountPrice());
		cpsGiftEntity.setLimitDay(request.getLimitDay());
		cpsGiftEntity.setValidDay(request.getValidDay());
		cpsGiftEntity.setContent(request.getContent());
		cpsGiftEntity.setEndDay(request.getEndDay());

		try {
			if (request.getApiType().equals("D")) {
				cpsGiftRepository.delete(cpsGiftEntity);
			} else {
				cpsGiftRepository.save(cpsGiftEntity);
			}
			CpsGiftDto.GiftProductResponse giftProductDto = new CpsGiftDto.GiftProductResponse();
			giftProductDto.setBrandId(request.getBrandId());
			giftProductDto.setProductId(request.getProductId());

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
			log.error(Constant.EXCEPTION_MESSAGE + " product api request : {}, exception : {}", request, e);
		}

		return response;
	}

	/**
	 * 기프트 상품 추첨
	 * @date 2024-10-16
	 */
	public CpsGiftDto.GiftProductResponse giftProbability(CpsGiftPacket.GiftInfo.GiftRequest request) throws Exception {
		CpsGiftDto.GiftProductResponse giftProductDto = new CpsGiftDto.GiftProductResponse();
		CpsGiftProductEntity cpsGiftProductEntity = new CpsGiftProductEntity();

		try {
			List<CpsGiftProductEntity> cpsGiftProductEntityList =  cpsGiftProductRepository.findByBrandIdAndAffliateIdAndMerchantId(request.getBrandId(), request.getAffliateId(), request.getMerchantId());

			if (cpsGiftProductEntityList.size() > 0) {
				int totalProbabilities = cpsGiftProductEntityList.stream().mapToInt(entity -> Integer.parseInt(entity.getProbabilities())).sum();

				Random random = new Random();
				int randomValue = random.nextInt(totalProbabilities);
				int cumulativeProbability = 0;
				for (CpsGiftProductEntity product : cpsGiftProductEntityList) {
					cumulativeProbability += Integer.parseInt(product.getProbabilities());
					if (randomValue < cumulativeProbability) {
						cpsGiftProductEntity =  product;
						break;
					}
				}

				giftProductDto.setBrandId(request.getBrandId());
				giftProductDto.setProductId(cpsGiftProductEntity.getProductId());
				giftProductDto.setAffliateId(request.getAffliateId());
			}
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " giftProbability api request : {}, exception : {}", request, e);
		}

		return giftProductDto;
	}

	public GenericBaseResponse<CpsGiftBrandDto> giftBrandList(CpsGiftBrandPacket.BrandInfo.BradnRequest request) throws Exception {
		CpsGiftBrandPacket.BrandInfo.GiftBrandResponse response = new CpsGiftBrandPacket.BrandInfo.GiftBrandResponse();
		List<CpsGiftBrandDto> cpsGiftBrandDtoList = new ArrayList<>();
		try {
			List<CpsGiftBrandEntity> cpsGiftBrandEntityList = cpsGiftBrandRepository.findByAffliateIdAndMerchantIdAndBrandTypeAndBrandYn(request.getAffliateId(), request.getMerchantId(), request.getBrandType(), "Y");
			if (cpsGiftBrandEntityList.size() > 0) {
				cpsGiftBrandEntityList.forEach(brand -> {
					CpsGiftBrandDto cpsGiftBrandDto = new CpsGiftBrandDto();
					cpsGiftBrandDto.setBrandId(brand.getBrandId());
					cpsGiftBrandDto.setBrandName(brand.getBrandName());
					cpsGiftBrandDto.setBrandLogo(brand.getBrandLogo());
					cpsGiftBrandDto.setMinCnt(brand.getMinCnt());
					cpsGiftBrandDtoList.add(cpsGiftBrandDto);
				});
				response.setSuccess();
				response.setDatas(cpsGiftBrandDtoList);
			} else {
				response.setApiMessage(Constants.GIFT_BRAND_BLANK.getCode(), Constants.GIFT_BRAND_BLANK.getValue());
				return response;
			}
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " giftBrandList api request : {}, exception : {}", request, e);
			response.setApiMessage(Constants.GIFT_BRAND_SEARCH_EXCEPTION.getCode(), Constants.GIFT_BRAND_SEARCH_EXCEPTION.getValue());
			return response;
		}

		return response;
	}

	/**
	 * 기프트 상품 리스트
	 * @date 2024-10-17
	 */
	public GenericBaseResponse<CpsGiftProductDto> giftProductList(CpsGiftProductPacket.GiftProductInfo.GiftProductRequest request) throws Exception {
		CpsGiftProductPacket.GiftProductInfo.GiftProductResponse response = new CpsGiftProductPacket.GiftProductInfo.GiftProductResponse();
		try {
			List<CpsGiftProductDto> cpsGiftProductDtoList = cpsGiftRepository.findByBrandIdAndProductId(request.getBrandId(), request.getAffliateId(), request.getMerchantId());

			if (cpsGiftProductDtoList.size() > 0) {
				response.setSuccess();
				response.setDatas(cpsGiftProductDtoList);
			} else {
				response.setApiMessage(Constants.GIFT_PRODUCT_BLANK.getCode(), Constants.GIFT_PRODUCT_BLANK.getValue());
				return response;
			}
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " giftProductList api request : {}, exception : {}", request, e);
			response.setApiMessage(Constants.GIFT_PRODUCT_SEARCH_EXCEPTION.getCode(), Constants.GIFT_PRODUCT_SEARCH_EXCEPTION.getValue());
			return response;
		}

		return response;
	}
}
