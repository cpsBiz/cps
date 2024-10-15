package com.cps.giftCoupang.service;

import com.cps.cpsService.dto.UnitDto;
import com.cps.cpsService.dto.UnitListDto;
import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.repository.CpsRewardUnitRepository;
import com.cps.giftCoupang.packet.CpsCoupangStickPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsGiftCoupangService {

	private final CpsRewardUnitRepository cpsRewardUnitRepository;

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
	public GenericBaseResponse<UnitListDto> coupangGift(CpsCoupangStickPacket.CoupangStickInfo.CoupangStickRequest request) throws Exception {
		CpsCoupangStickPacket.CoupangStickInfo.CoupangStickListResponse response = new CpsCoupangStickPacket.CoupangStickInfo.CoupangStickListResponse();

		try {
			UnitDto unitDto = cpsRewardUnitRepository.findByUserIdAndAffliateId(request.getUserId(), request.getMerchantId(), request.getAffliateId());
			if (null != unitDto) {
				Long stickCnt =  unitDto.getCnt() - unitDto.getStockCnt();

			} else {
				response.setApiMessage(Constants.COUPANG_STICK_BLANK.getCode(), Constants.COUPANG_STICK_BLANK.getValue());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.COUPANG_STICK_SEARCH_EXCEPTION.getCode(), Constants.COUPANG_STICK_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " coupangStickList request : {}, exception : {}", request, e);
		}

		return response;
	}
}
