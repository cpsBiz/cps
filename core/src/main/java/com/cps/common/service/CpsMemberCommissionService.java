package com.cps.common.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.dto.*;
import com.cps.common.model.GenericBaseResponse;
import com.cps.common.packet.CoupangStickPacket;
import com.cps.common.packet.CpsGiftHistoryPacket;
import com.cps.common.packet.CpsMemberCommissionPacket;
import com.cps.common.repository.CpsGiftHistoryRepository;
import com.cps.common.repository.CpsMemberCommissionRepository;
import com.cps.common.repository.CpsRewardUnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsMemberCommissionService {

	private final CpsMemberCommissionRepository cpsMemberCommissionRepository;

	private final CpsRewardUnitRepository cpsRewardUnitRepository;

	private final CpsGiftHistoryRepository cpsGiftHistoryRepository;

	/**
	 * 회원 적립금 조회
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsMemberCommissionDto> memberCommission(CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionRequest request) throws Exception {
		CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionResponse response = new CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionResponse();

		try {
			CpsMemberCommissionDto result = cpsMemberCommissionRepository.findRewardsByUserId(request.getUserId(), request.getAffliateId());

			if (null != result) {
				response.setSuccess();
				response.setData(result);
			}else{
				response.setApiMessage(Constants.MEMBER_COMMISSION_BLANK.getCode(), Constants.MEMBER_COMMISSION_BLANK.getValue());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.MEMBER_COMMISSION_SEARCH_EXCEPTION.getCode(), Constants.MEMBER_COMMISSION_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberCommission api request : {}, exception :  {}", request, e);
		}
		return response;
	}

	/**
	 * 회원 적립금 조회
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsMemberCommissionListDto> memberCommissionList(CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListRequest request) throws Exception {
		CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListResponse response = new CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListResponse();
		List<CpsMemberCommissionListDto> cpsMemberCommissionList = new ArrayList<>();
		List<Integer> statusList = new ArrayList<>();

		try {
			
			if (request.getStatus() == 0) { //전체
				statusList.add(100);
				statusList.add(200);
				statusList.add(210);
				statusList.add(300);
				statusList.add(310);
			} else if (request.getStatus() == 310) { //취소
				statusList.add(310);
			} else if (request.getStatus() == 100) { //예정
				statusList.add(100);
				statusList.add(200);
				statusList.add(300);
			} else { //확정
				statusList.add(210);
			}

			List<CpsMemberCommissionListDto> CpsMemberCommissionListDtoList = cpsMemberCommissionRepository.findRewardsByUserIdAndRegYm(request.getUserId(), request.getRegYm(), request.getAffliateId(), statusList);

			if (CpsMemberCommissionListDtoList.size() > 0) {
				response.setSuccess();
				response.setDatas(CpsMemberCommissionListDtoList);
			}else{
				response.setApiMessage(Constants.MEMBER_COMMISSION_BLANK.getCode(), Constants.MEMBER_COMMISSION_BLANK.getValue());
			}
		}catch (Exception e){
			response.setApiMessage(Constants.MEMBER_COMMISSION_SEARCH_EXCEPTION.getCode(), Constants.MEMBER_COMMISSION_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberCommissionList api request : {}, exception :  {}", request, e);
		}
		return response;
	}

	/**
	 * 쿠팡 막대사탕 개수 조회
	 * @date 2024-10-15
	 */
	public GenericBaseResponse<UnitDto> coupangStick(CoupangStickPacket.CoupangStickInfo.CoupangStickRequest request) throws Exception {
		CoupangStickPacket.CoupangStickInfo.CoupangStickResponse response = new CoupangStickPacket.CoupangStickInfo.CoupangStickResponse();

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
	public GenericBaseResponse<UnitListDto> coupangStickList(CoupangStickPacket.CoupangStickInfo.CoupangStickListRequest request) throws Exception {
		CoupangStickPacket.CoupangStickInfo.CoupangStickListResponse response = new CoupangStickPacket.CoupangStickInfo.CoupangStickListResponse();
		List<Integer> statusList = new ArrayList<>();

		if (request.getStatus() != 200 && request.getStatus() != 100) { //사용/취소
			statusList.add(210); //사용
			statusList.add(310); //취소
		} else { //확정, 예정
			statusList.add(request.getStatus());
		}

		try {
			List<UnitListDto> unitListDtoList = cpsRewardUnitRepository.findByUserIdAndStatus(request.getUserId(), request.getMerchantId(), request.getAffliateId(), request.getRegYm(), statusList);
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
	 * 기프티콘 리스트 조회
	 * @date 2024-10-17
	 */
	public GenericBaseResponse<CpsGiftHistoryDto> gifticonList(CpsGiftHistoryPacket.GiftHistoryInfo.GiftHistoryRequest request) throws Exception {
		CpsGiftHistoryPacket.GiftHistoryInfo.GiftHistoryResponse response = new CpsGiftHistoryPacket.GiftHistoryInfo.GiftHistoryResponse();
		List<String> giftList = new ArrayList<>();

		if (request.getGiftYn().equals("Y")) { //사용완료
			giftList.add("Y"); //사용
			giftList.add("V"); //만료
		} else { //사용가능
			giftList.add(request.getGiftYn());
		}

		try {
			List<CpsGiftHistoryDto> cpsGiftHistoryDtoList = cpsGiftHistoryRepository.findGiftInfo(request.getUserId(), request.getMerchantId(), request.getAffliateId(), request.getAwardYm(), giftList);
			if (cpsGiftHistoryDtoList.size() > 0) {
				response.setDatas(cpsGiftHistoryDtoList);
				response.setSuccess();
			} else {
				response.setApiMessage(Constants.COUPANG_STICK_BLANK.getCode(), Constants.COUPANG_STICK_BLANK.getValue());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.COUPANG_STICK_SEARCH_EXCEPTION.getCode(), Constants.COUPANG_STICK_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " gifticonList request : {}, exception : {}", request, e);
		}

		return response;
	}
}
