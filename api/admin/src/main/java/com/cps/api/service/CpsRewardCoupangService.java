package com.cps.api.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.common.utils.CommonUtil;
import com.cps.cpsService.dto.CommissionDto;
import com.cps.cpsService.dto.CpsRewardUnitDto;
import com.cps.cpsService.dto.DotPitchRewardDto;
import com.cps.cpsService.entity.CpsRewardEntity;
import com.cps.cpsService.entity.CpsRewardFirstEntity;
import com.cps.cpsService.entity.CpsRewardTempEntity;
import com.cps.cpsService.entity.CpsRewardUnitEntity;
import com.cps.cpsService.packet.CpsRewardPacket;
import com.cps.cpsService.repository.*;
import com.cps.cpsService.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsRewardCoupangService {

	@Value("${coupang.domain.url}") String coupangDomain;

	@Value("${coupang.domain.order.url}") String coupangOrderDomain;

	@Value("${coupang.domain.cancel.url}") String coupangCancelDomain;

	@Value("${coupang.subId}") String coupangSubId;

	private final HttpService httpService;

	private final CpsClickRepository cpsClickRepository;

	private final CpsRewardRepository cpsRewardRepository;

	private final CpsRewardUnitRepository cpsRewardUnitRepository;

	private final CpsRewardService cpsRewardService;

	private final CpsRewardTempRepository cpsRewardTempRepository;

	/**
	 * 쿠팡 실적조회 호출
	 * @date 2024-10-07
	 */
	public GenericBaseResponse<DotPitchRewardDto> coupangReward(CpsRewardPacket.RewardInfo.CoupangRequest request, String rewardYn) throws Exception {
		CpsRewardPacket.RewardInfo.RewardResponse response = new CpsRewardPacket.RewardInfo.RewardResponse();
		List<CpsRewardEntity> cpsRewardEntityList = new ArrayList<>();
		List<CpsRewardTempEntity> cpsRewardTempEntityList = new ArrayList<>();
		List<CpsRewardFirstEntity> cpsRewardFirstEntityList = new ArrayList<>();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		final List<Integer> clickNumList = new ArrayList<>();

		request.setSubId(coupangSubId);

		try {
			for (int i = 0; i <= 20; i++) {
				String domain = coupangOrderDomain;
				if (rewardYn.equals("Y")) {domain = coupangCancelDomain;}
				request.setPage(i);
				domain = domain + "?" + CommonUtil.objectToQueryString(request);
				CpsRewardPacket.RewardInfo.CoupangListResponse coupangResponseList = httpService.SendCoupangReward(coupangDomain, domain, request);
				if (coupangResponseList.getDataList() != null) {
					//조회 성공 응답 코드가 아닌 경우
					if (!coupangResponseList.getRCode().equals("0")) {
						break;
					}

					coupangResponseList.getDataList().forEach(coupang -> {
						if (null != coupang.getSubParam()) {
							int productCnt = 0;
							int clickNum = Integer.parseInt(coupang.getSubParam());
							clickNumList.add(clickNum);

							//클릭 정보
							CommissionDto commissionDto = cpsClickRepository.findClickCommission(Integer.parseInt(coupang.getSubParam()));
							CpsRewardEntity cpsRewardEntity = cpsRewardRepository.findByClickNumAndOrderNoAndProductCodeExcludingStatus(Integer.parseInt(coupang.getSubParam()), coupang.getOrderId(), coupang.getProductId());
							CpsRewardTempEntity cpsRewardTempEntity = new CpsRewardTempEntity();

							if (null != commissionDto) {
								if (rewardYn.equals("N")) {
									cpsRewardEntity = coupangRewardEntity(cpsRewardEntity, coupang, commissionDto, "reward", 0);
								} else {
									if (null != cpsRewardEntity) {
										productCnt = cpsRewardEntity.getProductCnt();
										if (cpsRewardEntity.getProductCnt() > coupang.getQuantity()) {
											cpsRewardEntity = coupangRewardEntity(cpsRewardEntity, coupang, commissionDto, "cancelReward", productCnt);
										}
										cpsRewardEntity = coupangRewardEntity(cpsRewardEntity, coupang, commissionDto, "cancel", productCnt);
									}
								}

								if (null != cpsRewardEntity) {
									cpsRewardEntity.setIpAddress(ipAddress);

									//리워드 TEMP 저장
									cpsRewardTempEntity = cpsRewardService.cpsRewardTempEntity(cpsRewardEntity);
									cpsRewardTempEntityList.add(cpsRewardTempEntity);

									//리워드 저장
									cpsRewardEntityList.add(cpsRewardEntity);

									//첫 데이터 등록 시
									if (productCnt == 0) {
										cpsRewardFirstEntityList.add(cpsRewardService.cpsRewardEntityList(cpsRewardEntity));
									}

									//쿠팡 리워드 TEMP 저장
									if (cpsRewardTempEntityList.size() == 1000) {
										cpsRewardTempRepository.saveAll(cpsRewardTempEntityList);
										cpsRewardTempEntityList.clear();
									}
								}
							}
						}
					});

					if (cpsRewardTempEntityList.size() > 0) {
						//쿠팡 리워드 TEMP 저장
						cpsRewardTempRepository.saveAll(cpsRewardTempEntityList);
					}

					//저장 완료 되면 CpsRewardTempEntityList 데이터 cpsRewardEntityList로 옮긴 후 1000개씩 저장 공통단
					//첫 리워드도 1000개씩 저장 공통단
					if (cpsRewardEntityList.size() > 0) {
						cpsRewardService.cpsRewardEntityListSave(cpsRewardEntityList, cpsRewardFirstEntityList);
					}

					//CLICK 테이블 상태 업데이트
					cpsClickRepository.updateRewardYnByClickNumList(rewardYn, cpsRewardEntityList.stream().map(CpsRewardEntity::getClickNum).collect(Collectors.toList()));
					response.setSuccess();

					//막대사탕 적립
					coupangStick(clickNumList, rewardYn);
				} else {
					response.setApiMessage(Constants.COUPANG_BLANK.getCode(), Constants.COUPANG_BLANK.getValue());
					return response;
				}
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.COUPANG_EXCEPTION.getCode(), Constants.COUPANG_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " coupangReward api request : {}, entity : {}, exception : {}", request, cpsRewardEntityList, e);
		}
		return response;
	}

	public void coupangStick(List<Integer> clickNumList, String rewardYn) throws UnknownHostException {
		List<CpsRewardUnitEntity> cpsRewardUnitList = new ArrayList<>();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();

		if (clickNumList.size() > 0) {
			List<Integer> rewordUnitList = clickNumList.stream().distinct().collect(Collectors.toList());
			rewordUnitList.forEach(clickNum -> {
				try {
					CpsRewardUnitDto cpsRewardUnitDto = cpsRewardRepository.findByRewardClickNum(clickNum);

					if (cpsRewardUnitDto != null) {
						CpsRewardUnitEntity cpsRewardUnitEntity = new CpsRewardUnitEntity();
						cpsRewardUnitEntity.setClickNum(clickNum);
						cpsRewardUnitEntity.setRewardCnt(cpsRewardUnitDto.getRewardCnt());
						cpsRewardUnitEntity.setRegDay(cpsRewardUnitDto.getRegDay());
						cpsRewardUnitEntity.setRegYm(cpsRewardUnitDto.getRegYm());
						cpsRewardUnitEntity.setUserId(cpsRewardUnitDto.getUserId());
						cpsRewardUnitEntity.setMerchantId(cpsRewardUnitDto.getMerchantId());
						cpsRewardUnitEntity.setAffliateId(cpsRewardUnitDto.getAffliateId());
						cpsRewardUnitEntity.setProductName(cpsRewardUnitDto.getProductName());
						cpsRewardUnitEntity.setCnt(cpsRewardUnitDto.getCnt());
						cpsRewardUnitEntity.setTotalPrice(cpsRewardUnitDto.getTotalPrice());
						cpsRewardUnitEntity.setStockCnt(0);
						cpsRewardUnitEntity.setStatus(100);
						cpsRewardUnitEntity.setIpAddress(ipAddress);

						//reward 테이블에 건수가 있고 기존 데이터와 요청 데이터 건수가 다른 경우 insert 안함
						if (cpsRewardUnitDto.getCnt() > 0 && (cpsRewardUnitDto.getUnitCnt() != cpsRewardUnitDto.getCnt())) {
							cpsRewardUnitList.add(cpsRewardUnitEntity);
						}

						//기존 적립개수와 적립 할 개수가 맞지 않은 경우
						if (cpsRewardUnitDto.getUnitCnt() > cpsRewardUnitDto.getCnt()) {
							cpsRewardUnitEntity = new CpsRewardUnitEntity();
							cpsRewardUnitEntity.setRewardUnitNum(cpsRewardUnitDto.getRewardUnitNum());
							cpsRewardUnitEntity.setClickNum(clickNum);
							cpsRewardUnitEntity.setRegDay(cpsRewardUnitDto.getRegDay());
							cpsRewardUnitEntity.setRegYm(cpsRewardUnitDto.getRegYm());
							cpsRewardUnitEntity.setUserId(cpsRewardUnitDto.getUserId());
							cpsRewardUnitEntity.setMerchantId(cpsRewardUnitDto.getMerchantId());
							cpsRewardUnitEntity.setAffliateId(cpsRewardUnitDto.getAffliateId());
							cpsRewardUnitEntity.setProductName(cpsRewardUnitDto.getProductName());
							cpsRewardUnitEntity.setRewardCnt(cpsRewardUnitDto.getRewardCnt());
							cpsRewardUnitEntity.setIpAddress(ipAddress);

							//reward 테이블에 건수가 있는 경우 기존 건수, 금액 계산 후 310 데이터로 insert
							//건수가 없는 경우 기존 데이터로 310 업데이트
							if (cpsRewardUnitDto.getCnt() > 0) {
								cpsRewardUnitEntity.setCnt(cpsRewardUnitDto.getUnitCnt() - cpsRewardUnitDto.getCnt());
								cpsRewardUnitEntity.setTotalPrice(cpsRewardUnitDto.getUnitPrice() - cpsRewardUnitDto.getTotalPrice());
							} else {
								cpsRewardUnitEntity.setCnt(cpsRewardUnitDto.getUnitCnt());
								cpsRewardUnitEntity.setTotalPrice(cpsRewardUnitDto.getUnitPrice());
							}
							cpsRewardUnitEntity.setStatus(310);
							cpsRewardUnitList.add(cpsRewardUnitEntity);
						}
					}

					if (cpsRewardUnitList.size() > 1000) {
						//쿠팡 막대사탕 저장
						cpsRewardUnitRepository.saveAll(cpsRewardUnitList);
					}
				} catch (Exception e) {
					log.error(Constant.EXCEPTION_MESSAGE + " coupangStick for cpsRewardUnitList : {}, exception  : {} ", cpsRewardUnitList, e);
				}
			});

			if (cpsRewardUnitList.size() > 0) {
				//쿠팡 막대사탕 저장
				cpsRewardUnitRepository.saveAll(cpsRewardUnitList);
			}
		}
	}

	public void coupangStickSchedule(CpsRewardPacket.RewardInfo.CoupangStickRequest request) throws UnknownHostException {
		try {
			cpsRewardUnitRepository.updateByRegDay(request.getRegDay());;
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " coupangStickSchedule api request : {}, exception  : {} ", request, e);
		}
	}

	public CpsRewardEntity coupangRewardEntity(CpsRewardEntity cpsRewardEntity, CpsRewardPacket.RewardInfo.CoupangData coupang, CommissionDto commissionDto, String reward, int productCnt) {
		if(null == cpsRewardEntity) cpsRewardEntity = new CpsRewardEntity();
		int commission = coupang.getCommission();
		double memberCommissionShareDouble = (double) commissionDto.getMemberCommissionShare() / 100;
		int productPrice = cpsRewardEntity.getProductPrice();

		//기존 수량과 현재 수량이 다르고 취소데이터인 경우 insert 로직 변경 그 외 업데이트
		if (reward.equals("cancel") && productCnt > coupang.getQuantity()) {
			cpsRewardEntity = new CpsRewardEntity();
		}
		cpsRewardEntity.setRegDay(commissionDto.getCpsClickEntity().getRegDay());
		cpsRewardEntity.setRegYm(commissionDto.getCpsClickEntity().getRegYm());
		cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
		cpsRewardEntity.setClickNum(Integer.parseInt(coupang.getSubParam()));
		cpsRewardEntity.setOrderNo(coupang.getOrderId());
		cpsRewardEntity.setProductCode(coupang.getProductId());
		cpsRewardEntity.setCampaignNum(commissionDto.getCpsClickEntity().getCampaignNum());
		cpsRewardEntity.setMerchantId(commissionDto.getCpsClickEntity().getMerchantId());
		cpsRewardEntity.setAgencyId(commissionDto.getCpsClickEntity().getAgencyId());
		cpsRewardEntity.setAffliateId(commissionDto.getCpsClickEntity().getAffliateId());
		cpsRewardEntity.setZoneId(commissionDto.getCpsClickEntity().getZoneId());
		cpsRewardEntity.setSite(commissionDto.getCpsClickEntity().getSite());
		cpsRewardEntity.setOs(commissionDto.getCpsClickEntity().getOs());
		cpsRewardEntity.setType(commissionDto.getCpsClickEntity().getType());
		cpsRewardEntity.setUserId(commissionDto.getCpsClickEntity().getUserId());
		cpsRewardEntity.setAdId(commissionDto.getCpsClickEntity().getAdId());
		cpsRewardEntity.setMemberName(commissionDto.getMemberName());
		cpsRewardEntity.setProductName(coupang.getProductName());
		cpsRewardEntity.setStatus(100);
		cpsRewardEntity.setProductCnt(coupang.getQuantity());
		cpsRewardEntity.setProductPrice(coupang.getGmv());

		 if(reward.equals("cancelReward")){
			//부분 취소 (기존 100번 데이터 업데이트)
			if (productCnt > coupang.getQuantity()) {
				cpsRewardEntity.setProductCnt(productCnt - coupang.getQuantity());
				cpsRewardEntity.setProductPrice(productPrice - coupang.getGmv());
				commission = cpsRewardEntity.getCommission() - coupang.getCommission();
			} else {
				//취소 수량과 현재 수량이 같은 경우 취소처리
				cpsRewardEntity.setStatus(310);
				cpsRewardEntity.setProductCnt(coupang.getQuantity());
				cpsRewardEntity.setProductPrice(coupang.getGmv());
			}
		 } else if (reward.equals("cancel")) {
			 cpsRewardEntity.setStatus(310);
		 }
		
		//커미션 매출
		cpsRewardEntity.setCommission(commission);
		//커미션 순이익 (커미션 매출 - 매체 커미션)
		cpsRewardEntity.setCommissionProfit(commission - (int) (commission * memberCommissionShareDouble));
		//매체 커미션 (커미션 매출 * 매체쉐어)
		cpsRewardEntity.setAffliateCommission((int) (commission * memberCommissionShareDouble));
		//유저 커미션 (매체 커미션 * 유저쉐어), 쿠팡은 커미션이 아닌 막대사탕으로 지급
		cpsRewardEntity.setUserCommission(0);
		cpsRewardEntity.setCommissionRate(String.valueOf(coupang.getCommissionRate()));

		cpsRewardEntity.setBaseCommission("0");
		cpsRewardEntity.setIncentiveCommission("0");
		return cpsRewardEntity;
	}

	public CpsRewardUnitEntity cpsRewardUnitEntity(CpsRewardUnitEntity rewardUnitEntity){
		CpsRewardUnitEntity cpsRewardUnitEntity = new CpsRewardUnitEntity();

		return cpsRewardUnitEntity;
	}
}
