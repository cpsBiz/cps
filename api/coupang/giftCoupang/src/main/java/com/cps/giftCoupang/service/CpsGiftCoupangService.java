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
import com.cps.cpsService.service.HttpService;
import com.cps.giftCoupang.packet.CpsCoupangStickPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsGiftCoupangService {

	@Value("${giftishow.coupon.url}") String giftishowCouponDomain;

	@Value("${giftishow.auth.code}") String giftishowAuthCode;

	@Value("${giftishow.auth.token}") String giftishowAuthToken;

	@Value("${giftishow.user.id}") String giftishowUserId;

	@Value("${giftishow.coupon.api.code}") String giftishowCouponApiCode;

	@Value("${giftishow.coupon.cancel.url}") String giftishowCouponCancelDomain;

	@Value("${giftishow.coupon.cancel.api.code}") String giftishowCouponCancelApiCode;

	private final CpsRewardUnitRepository cpsRewardUnitRepository;

	private final CpsGiftRepository cpsGiftRepository;

	private final CpsGiftHistoryRepository cpsGiftHistoryRepository;

	private final com.cps.cpsService.service.CpsGiftService cpsGiftService;

	private final HttpService httpService;

	/**
	 * 쿠팡 막대사탕 사용
	 * @date 2024-10-15
	 */
	public GenericBaseResponse<CpsGiftDto.GiftProductResponse> coupangGift(CpsCoupangStickPacket.CoupangStickInfo.CoupangStickGiftRequest request) throws Exception {
		CpsCoupangStickPacket.CoupangStickInfo.CoupangGiftResponse response = new CpsCoupangStickPacket.CoupangStickInfo.CoupangGiftResponse();
		CpsGiftPacket.GiftInfo.GiftRequest giftRequest = new CpsGiftPacket.GiftInfo.GiftRequest();
		CpsGiftHistoryEntity cpsGiftHistoryEntity = new CpsGiftHistoryEntity();
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		giftRequest.setBrandId(request.getBrandId());
		giftRequest.setAffliateId(request.getAffliateId());
		giftRequest.setMerchantId(request.getMerchantId());

		//상품추첨
		CpsGiftDto.GiftProductResponse giftResponse = cpsGiftService.giftProbability(giftRequest);

		if(null != giftResponse) {
			try {
				UnitDto unitDto = cpsRewardUnitRepository.findByUserIdAndAffliateId(request.getUserId(), request.getMerchantId(), request.getAffliateId());

				if (null != unitDto) {
					long stickCnt = unitDto.getCnt() - unitDto.getStockCnt();
					AtomicLong cnt = new AtomicLong((long) request.getCnt());

					//보유 개수와 상품 구매 개수 비교
					if (stickCnt >= cnt.get()) {
						List<UnitListDto> unitListDtoList = cpsRewardUnitRepository.findByUserIdAndStatusOrderByRegDate(request.getUserId(), request.getMerchantId(), request.getAffliateId());

						//사용 가능한 막대사탕 리스트 가져오기
						if (unitListDtoList.size() > 0) {

							//giftHistory (상품지급 로직)
							cpsGiftHistoryEntity.setUserId(request.getUserId());
							cpsGiftHistoryEntity.setAffliateId(request.getAffliateId());
							cpsGiftHistoryEntity.setMerchantId(request.getMerchantId());
							cpsGiftHistoryEntity.setBrandId(request.getBrandId());
							cpsGiftHistoryEntity.setProductId(giftResponse.getProductId());
							cpsGiftHistoryEntity.setCnt(request.getCnt());
							cpsGiftHistoryEntity.setAwardDay(Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
							cpsGiftHistoryEntity.setAwardYm(Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"))));
							cpsGiftHistoryEntity.setUseDay(0);
							cpsGiftHistoryEntity.setGiftYn("N");
							CpsGiftEntity cpsGiftEntity = cpsGiftRepository.findByBrandIdAndProductId(request.getBrandId(), giftResponse.getProductId());
							cpsGiftHistoryEntity.setValidDay(cpsGiftEntity.getValidDay());
							cpsGiftHistoryRepository.save(cpsGiftHistoryEntity);

							//기프티콘 API 통신 성공 시시
							requestParams.add("api_code", giftishowCouponApiCode);
							requestParams.add("custom_auth_code", giftishowAuthCode);
							requestParams.add("custom_auth_token",giftishowAuthToken);
							requestParams.add("dev_yn", "N");
							requestParams.add("goods_code", giftResponse.getProductId());
							requestParams.add("order_no", cpsGiftHistoryEntity.getHistoryNum() + "_ShowPlusOrder");
							requestParams.add("mms_msg", cpsGiftEntity.getProductName() + " 기프티콘 발송");
							requestParams.add("mms_title", "기프티콘 발송");
							requestParams.add("callback_no", "15880108");
							requestParams.add("phone_no", "01030074759");
							requestParams.add("tr_id", "ShowPlusTr_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_"+ cpsGiftHistoryEntity.getHistoryNum()); //tr_id
							requestParams.add("user_id", giftishowUserId); //user_id
							requestParams.add("rev_info_yn", "N"); //예약
							requestParams.add("gubun", "I"); //핀번호

							cpsGiftHistoryEntity.setTrId("ShowPlusTr_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_"+ cpsGiftHistoryEntity.getHistoryNum());
							CpsGiftPacket.GiftInfo.ShowBizListCouponResponse showBizListResponse =  httpService.SendGiftiShowBizCoupon(giftishowCouponDomain, requestParams);

							if (null != showBizListResponse.getShowBizCouponResult()) {
								if ("0000".equals(showBizListResponse.getShowBizCouponResult().getCode())) {
									cpsGiftHistoryEntity.setBarcode(showBizListResponse.getShowBizCouponResult().getResult().getCouponImgUrl());
									cpsGiftHistoryEntity.setOrderNo(showBizListResponse.getShowBizCouponResult().getResult().getOrderNo());
									cpsGiftHistoryEntity.setPinNo(showBizListResponse.getShowBizCouponResult().getResult().getPinNo());

									cpsGiftHistoryRepository.save(cpsGiftHistoryEntity);

									unitListDtoList.forEach(stick -> {
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
									couponCancel(cpsGiftHistoryEntity);
									response.setData(giftResponse);
									response.setSuccess();
								} else {
									//쿠폰발급 오류
									couponCancel(cpsGiftHistoryEntity);
									response.setApiMessage(showBizListResponse.getCode(), couponErrorMsg(showBizListResponse.getCode()));
									return response;
								}
							} else {
								//쿠폰 정보 없음
								couponCancel(cpsGiftHistoryEntity);
								response.setApiMessage(Constants.GIFTISHOW_BLANK.getCode(), Constants.GIFTISHOW_BLANK.getValue());
								return response;
							}
						} else {
							//막대사탕 정보 없음
							response.setApiMessage(Constants.COUPANG_STICK_BLANK.getCode(), Constants.COUPANG_STICK_BLANK.getValue());
							return response;
						}
					} else {
						//막대사탕 개수 부족
						response.setApiMessage(Constants.COUPANG_STICK_COUNT.getCode(), Constants.COUPANG_STICK_COUNT.getValue());
						return response;
					}
				} else {
					//막대사탕 정보 없음
					response.setApiMessage(Constants.COUPANG_STICK_BLANK.getCode(), Constants.COUPANG_STICK_BLANK.getValue());
					return response;
				}
			} catch (Exception e) {
				couponCancel(cpsGiftHistoryEntity);
				response.setApiMessage(Constants.COUPANG_STICK_SEARCH_EXCEPTION.getCode(), Constants.COUPANG_STICK_SEARCH_EXCEPTION.getValue());
				log.error(Constant.EXCEPTION_MESSAGE + " coupangGift request : {}, exception : {}", request, e);
			}
		} else {
			response.setApiMessage(Constants.GIFT_PRODUCT_BLANK.getCode(), Constants.GIFT_PRODUCT_BLANK.getValue());
			return response;
		}

		return response;
	}

	// 쿠폰 로직 이상 시 쿠폰 발급 취소
	public void couponCancel(CpsGiftHistoryEntity cpsGiftHistoryEntity){
		if (cpsGiftHistoryEntity.getHistoryNum() > 0 ) {
			//추후 수정 E로 수정
			cpsGiftHistoryEntity.setGiftYn("N");
			cpsGiftHistoryRepository.save(cpsGiftHistoryEntity);
		}
		MultiValueMap<String, String>  requestParams = new LinkedMultiValueMap<>();
		requestParams.add("api_code", giftishowCouponCancelApiCode);
		requestParams.add("custom_auth_code", giftishowAuthCode);
		requestParams.add("custom_auth_token",giftishowAuthToken);
		requestParams.add("dev_yn", "N");
		requestParams.add("tr_id", cpsGiftHistoryEntity.getTrId());
		requestParams.add("user_id", giftishowUserId);

		CpsGiftPacket.GiftInfo.ShowBizListCouponResponse showBizListResponse =  httpService.SendGiftiShowBizCoupon(giftishowCouponCancelDomain, requestParams);
		
	}

	public String couponErrorMsg(String code) {
		Map<String, String> errorMessages = new HashMap<>();
		errorMessages.put("E0002", "API 코드가 존재하지 않습니다.");
		errorMessages.put("E0007", "API코드가 일치하지 않습니다.");
		errorMessages.put("E0008", "유효한 인증 키가 아닙니다.");
		errorMessages.put("E0009", "유효한 인증 토큰이 아닙니다.");
		errorMessages.put("E0010", "비즈머니 잔액이 부족합니다.");
		errorMessages.put("E0011", "인증키가 없습니다.");
		errorMessages.put("E0012", "토큰키가 없습니다.");
		errorMessages.put("E0013", "테스트 YN 값이 없습니다.");
		errorMessages.put("E9999", "오류가 발생했습니다.");
		errorMessages.put("ERR0000", "알 수 없는 에러입니다.");
		errorMessages.put("ERR0005", "DBMS 에러입니다.");
		errorMessages.put("ERR0008", "SQL 에러입니다.");
		errorMessages.put("ERR0100", "현재 DEV 서비스를 이용할 수 없습니다.");
		errorMessages.put("ERR0201", "필수 파라미터가 누락되었습니다.");
		errorMessages.put("ERR0208", "상품 주문 관련 오류.");
		errorMessages.put("ERR0209", "상품 주문 메시지 관련 오류.");
		errorMessages.put("ERR0212", "MMS 재발송 대상 미조회.");
		errorMessages.put("ERR0213", "MMS 재발송 대상 미조회.");
		errorMessages.put("ERR0217", "MMS 번호 번호 변경 불가.");
		errorMessages.put("ERR0214", "TR ID가 없습니다.");
		errorMessages.put("ERR0215", "TR ID가 중복되었습니다.");
		errorMessages.put("ERR0300", "회원정보 조회 실패.");
		errorMessages.put("ERR0301", "API 가입정보 없음.");
		errorMessages.put("ERR0401", "요청한 제품이 없습니다.");
		errorMessages.put("ERR0800", "비즈포인트 조회 오류.");
		errorMessages.put("ERR0803", "비즈포인트 차감 오류.");
		errorMessages.put("ERR0804", "비즈포인트 적립 오류.");
		errorMessages.put("ERR0805", "쿠폰 취소 실패.");
		errorMessages.put("ERR0806", "제목이 20자를 초과했습니다.");
		errorMessages.put("ERR0807", "TR_ID가 25byte를 초과했습니다.");
		errorMessages.put("ERR0808", "이미 취소된 쿠폰입니다.");
		errorMessages.put("ERR0817", "수신 전화번호 오류.");
		errorMessages.put("ERR0999", "쿠폰 발송 오류.");
		errorMessages.put("PAG0001", "페이징 관련 오류.");
		errorMessages.put("PAG0002", "페이징 관련 오류.");
		return errorMessages.getOrDefault(code, "중복된 거래아이디(tr_id)로 호출하였습니다.");
	}
}
