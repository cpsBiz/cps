package com.cps.giftCoupang.service;

import com.cps.cpsService.dto.CpsGiftDto;
import com.cps.cpsService.dto.UnitDto;
import com.cps.cpsService.dto.UnitListDto;
import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.entity.CpsGiftEntity;
import com.cps.cpsService.entity.CpsGiftHistoryEntity;
import com.cps.cpsService.packet.CpsGiftPacket;
import com.cps.cpsService.repository.CpsGiftHistoryRepository;
import com.cps.cpsService.repository.CpsGiftRepository;
import com.cps.cpsService.repository.CpsRewardUnitRepository;
import com.cps.giftCoupang.packet.CpsCoupangStickPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsGiftCoupangService {

	private final CpsRewardUnitRepository cpsRewardUnitRepository;

	private final CpsGiftRepository cpsGiftRepository;

	private final CpsGiftHistoryRepository cpsGiftHistoryRepository;

	private final com.cps.cpsService.service.CpsGiftService cpsGiftService;

	/**
	 * 쿠팡 막대사탕 개수 조회
	 * @date 2024-10-15
	 */
	public GenericBaseResponse<UnitDto> coupangStick(CpsCoupangStickPacket.CoupangStickInfo.CoupangStickRequest request) throws Exception {
		CpsCoupangStickPacket.CoupangStickInfo.CoupangStickResponse response = new CpsCoupangStickPacket.CoupangStickInfo.CoupangStickResponse();

		try {
			UnitDto unitDto = cpsRewardUnitRepository.findByUserIdAndAffliateId(request.getUserId(), request.getMerchantId(), request.getAffliateId());
			if (null != unitDto) {
				response.setData(unitDto);
				response.setSuccess();
			} else {
				response.setApiMessage(Constants.COUPANG_STICK_BLANK.getCode(), Constants.COUPANG_STICK_BLANK.getValue());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.COUPANG_STICK_SEARCH_EXCEPTION.getCode(), Constants.COUPANG_STICK_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " coupangStick request : {}, exception : {}", request, e);
		}

		return response;
	}

	/**
	 * 쿠팡 막대사탕 리스트 조회
	 * @date 2024-10-15
	 */
	public GenericBaseResponse<UnitListDto> coupangStickList(CpsCoupangStickPacket.CoupangStickInfo.CoupangStickListRequest request) throws Exception {
		CpsCoupangStickPacket.CoupangStickInfo.CoupangStickListResponse response = new CpsCoupangStickPacket.CoupangStickInfo.CoupangStickListResponse();

		try {
			List<UnitListDto> unitListDtoList = cpsRewardUnitRepository.findByUserIdAndStatus(request.getUserId(), request.getMerchantId(), request.getAffliateId(), request.getRegYm(), request.getStatus());
			if (unitListDtoList.size() > 0) {
				response.setDatas(unitListDtoList);
				response.setSuccess();
			} else {
				response.setApiMessage(Constants.COUPANG_STICK_BLANK.getCode(), Constants.COUPANG_STICK_BLANK.getValue());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.COUPANG_STICK_SEARCH_EXCEPTION.getCode(), Constants.COUPANG_STICK_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " coupangStickList request : {}, exception : {}", request, e);
		}

		return response;
	}

	/**
	 * 쿠팡 막대사탕 사용
	 * @date 2024-10-15
	 */
	public GenericBaseResponse<CpsGiftDto.GiftProductResponse> coupangGift(CpsCoupangStickPacket.CoupangStickInfo.CoupangStickGiftRequest request) throws Exception {
		CpsCoupangStickPacket.CoupangStickInfo.CoupangGiftResponse response = new CpsCoupangStickPacket.CoupangStickInfo.CoupangGiftResponse();
		CpsGiftPacket.GiftInfo.GiftRequest giftRequest = new CpsGiftPacket.GiftInfo.GiftRequest();


		giftRequest.setBrandId(request.getBrandId());
		giftRequest.setAffliateId(request.getAffliateId());
		giftRequest.setMerchantId(request.getMerchantId());

		//상품추첨
		CpsGiftDto.GiftProductResponse giftResponse = cpsGiftService.giftProbability(giftRequest);

		CpsGiftHistoryEntity cpsGiftHistoryEntity = new CpsGiftHistoryEntity();
		cpsGiftHistoryEntity.setUserId(request.getUserId());
		cpsGiftHistoryEntity.setAffliateId(request.getAffliateId());
		cpsGiftHistoryEntity.setMerchantId(request.getMerchantId());
		cpsGiftHistoryEntity.setBrandId(request.getBrandId());
		cpsGiftHistoryEntity.setProductId(giftResponse.getProductId());

		CpsGiftEntity cpsGiftEntity = cpsGiftRepository.findByBrandIdAndProductId(request.getBrandId(), giftResponse.getProductId());
		cpsGiftHistoryEntity.setValidDay(cpsGiftEntity.getValidDay());
		cpsGiftHistoryEntity.setCnt(request.getCnt());
		cpsGiftHistoryEntity.setAwardDay(Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
		cpsGiftHistoryEntity.setUseDay(0);
		cpsGiftHistoryEntity.setGiftYn("N");

		try {
			UnitDto unitDto = cpsRewardUnitRepository.findByUserIdAndAffliateId(request.getUserId(), request.getMerchantId(), request.getAffliateId());

			if (null != unitDto) {
				long stickCnt =  unitDto.getCnt() - unitDto.getStockCnt();
				AtomicLong cnt = new AtomicLong((long) request.getCnt());

				//보유 개수와 상품 구매 개수 비교
				if (stickCnt >= cnt.get()) {
					List<UnitListDto> unitListDtoList = cpsRewardUnitRepository.findByUserIdAndStatusOrderByRegDate(request.getUserId(), request.getMerchantId(), request.getAffliateId());

					//사용 가능한 막대사탕 리스트 가져오기
					if (unitListDtoList.size() > 0) {

						//기프티콘 API 통신 성공 시시
						cpsGiftHistoryEntity.setBarcode("");

						//giftHistory (상품지급 로직)
						cpsGiftHistoryRepository.save(cpsGiftHistoryEntity);

						unitListDtoList.forEach(stick ->{
							if (cnt.get() > 0) {
								long stickListCnt = (long) stick.getCnt() - (long) stick.getStockCnt();

								//구매개수가 더 큰 경우 기존 데이터 stockCnt cnt 값으로 업데이트
								if (cnt.get() > stickListCnt) {
									cnt.set(cnt.get() - stickListCnt);
									cpsRewardUnitRepository.updateByRewardUnitNum210(stick.getRewardUnitNum());
								} else if (stickListCnt > cnt.get()) {
									//잔여 개수가 더 큰 경우 stickListCnt - cnt 잔여개수를 stock_cnt로 업데이트
									cpsRewardUnitRepository.updateByRewardUnitNum200(stick.getRewardUnitNum(), cnt.get());
									cnt.set(0);
								} else {
									//잔여 개수와 구매개수가 같은 경우 stock_cnt = cnt로 업데이트
									cpsRewardUnitRepository.updateByRewardUnitNum210(stick.getRewardUnitNum());
									cnt.set(0);
								}
							}
						});

						response.setData(giftResponse);
						response.setSuccess();
					} else {
						response.setApiMessage(Constants.COUPANG_STICK_BLANK.getCode(), Constants.COUPANG_STICK_BLANK.getValue());
						return response;
					}
				} else {
					response.setApiMessage(Constants.COUPANG_STICK_COUNT.getCode(), Constants.COUPANG_STICK_COUNT.getValue());
					return response;
				}
			} else {
				response.setApiMessage(Constants.COUPANG_STICK_BLANK.getCode(), Constants.COUPANG_STICK_BLANK.getValue());
				return response;
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.COUPANG_STICK_SEARCH_EXCEPTION.getCode(), Constants.COUPANG_STICK_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " coupangGift request : {}, exception : {}", request, e);
		}

		return response;
	}
}
