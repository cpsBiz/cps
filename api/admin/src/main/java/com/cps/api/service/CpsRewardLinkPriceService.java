package com.cps.api.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CommissionDto;
import com.cps.cpsService.dto.DotPitchRewardDto;
import com.cps.cpsService.entity.CpsRewardEntity;
import com.cps.cpsService.entity.CpsRewardFirstEntity;
import com.cps.cpsService.entity.CpsRewardTempEntity;
import com.cps.cpsService.packet.CpsRewardPacket;
import com.cps.cpsService.repository.*;
import com.cps.cpsService.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsRewardLinkPriceService {
	@Value("${linkPrice.domain.url}") String linkPriceDomain;

	@Value("${linkPrice.domain.reward.url}") String linkPriceReward;

	@Value("${linkPrice.authKey}") String linkPriceAuthKey;

	@Value("${linkPrice.site.code}") String linkPriceSiteCode;

	private final HttpService httpService;

	private final CpsRewardService cpsRewardService;

	private final CpsClickRepository cpsClickRepository;

	private final CpsRewardRepository cpsRewardRepository;

	private final CpsRewardTempRepository cpsRewardTempRepository;


	/**
	 * 링크프라이스 실적조회 호출
	 * @date 2024-09-25
	 */
	public GenericBaseResponse<DotPitchRewardDto> linkPriceReward(CpsRewardPacket.RewardInfo.LinkPriceRequest request, String rewardYn) throws Exception {
		CpsRewardPacket.RewardInfo.RewardResponse response = new CpsRewardPacket.RewardInfo.RewardResponse();

		List<CpsRewardEntity> cpsRewardEntityList = new ArrayList<>();
		List<CpsRewardTempEntity> cpsRewardTempEntityList = new ArrayList<>();
		List<CpsRewardFirstEntity> cpsRewardFirstEntityList = new ArrayList<>();

		String domain = linkPriceDomain + linkPriceReward;

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		request.setAuth_key(linkPriceAuthKey);
		request.setA_id(linkPriceSiteCode);

		try {
			//추후 수정
			for (int i = 1; i <= 20; i++) {
				request.setPage(i);
				CpsRewardPacket.RewardInfo.LinkPriceListResponse dotPitchResponseList = httpService.SendLinkPriceReward(domain, request);
				if (dotPitchResponseList.getOrder_list() != null) {
				//추후 수정
					if (i > 1) {
						dotPitchResponseList.setResult("123");
					}

					//조회 성공 응답 코드가 아닌 경우
					if (!dotPitchResponseList.getResult().equals("0")) {
						//정상 페이지 호출이 아닌 경우
						if (dotPitchResponseList.getResult().equals("100")) {
							log.error("linkPriceReward 100 : {}, result : {}", i, "A코드 (사이트코드)(a_id) 없음");
						} else if (dotPitchResponseList.getResult().equals("200")) {
							log.error("linkPriceReward 200 : {}, result : {}", i, "조회일자(yyyymmdd) 없음");
						} else if (dotPitchResponseList.getResult().equals("210")) {
							log.error("linkPriceReward 210 : {}, result : {}", i, "조회일자(yyyymmdd) 길이 오류");
						} else if (dotPitchResponseList.getResult().equals("300")) {
							log.error("linkPriceReward 300 : {}, result : {}", i, "인증키(auth_key)가 맞지 않음");
						} else if (dotPitchResponseList.getResult().equals("400")) {
							log.error("linkPriceReward 400 : {}, result : {}", i, "통화단위(currency)를 지원하지 않음");
						}
						break;
					} else {
						dotPitchResponseList.getOrder_list().forEach(link -> {
							CommissionDto commissionDto = cpsClickRepository.findClickCommission(link.getUser_id());
							CpsRewardEntity cpsRewardEntity = cpsRewardRepository.findByClickNumAndOrderNoAndProductCodeExcludingStatus(link.getUser_id(), link.getO_cd(), link.getP_cd());
							CpsRewardTempEntity cpsRewardTempEntity = new CpsRewardTempEntity();

							if (null != commissionDto) {
								int productCnt = 0;
								int productPrice = 0;
								int commission = 0;

								if (null != cpsRewardEntity) {
									productCnt = cpsRewardEntity.getProductCnt();
									productPrice = cpsRewardEntity.getProductPrice();
									commission = cpsRewardEntity.getCommission();

									if (productCnt > Integer.parseInt(link.getIt_cnt())) {
										cpsRewardEntityList.add(linkPriceRewardEntity(cpsRewardEntity, link, commissionDto, "cancelReward", request.getCancel_flag(), rewardYn));
										cpsRewardEntity.setProductCnt(productCnt);
										cpsRewardEntity.setProductPrice(productPrice);
										cpsRewardEntity.setCommission(commission);
										cpsRewardEntity = linkPriceRewardEntity(cpsRewardEntity, link, commissionDto, "reward", request.getCancel_flag(), rewardYn);
									} else if (Integer.parseInt(link.getIt_cnt()) >= productCnt) {
										cpsRewardEntityList.add(linkPriceRewardEntity(cpsRewardEntity, link, commissionDto, "cancel", request.getCancel_flag(), rewardYn));
									}
								} else {
									cpsRewardEntity = linkPriceRewardEntity(cpsRewardEntity, link, commissionDto, "reward", request.getCancel_flag(), rewardYn);
								}
								cpsRewardEntity.setIpAddress(ipAddress);

								//리워드 TEMP 저장
								cpsRewardTempEntity = cpsRewardService.cpsRewardTempEntity(cpsRewardEntity);
								cpsRewardTempEntityList.add(cpsRewardTempEntity);

								//리워드 저장
								cpsRewardEntityList.add(cpsRewardEntity);

								//첫 데이터 등록 시
								if (productCnt == 0) {
									cpsRewardFirstEntityList.add(cpsRewardEntityList(cpsRewardEntity));
								}

								//링크프라이스 리워드 TEMP 저장
								if (cpsRewardTempEntityList.size() == 1000) {
									cpsRewardTempRepository.saveAll(cpsRewardTempEntityList);
									cpsRewardTempEntityList.clear();
								}
							}
						});

						if (cpsRewardTempEntityList.size() > 0) {
							//링크프라이스 리워드 TEMP 저장
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
					}
				} else {
					response.setApiMessage(Constants.LINKPRICE_BLANK.getCode(), Constants.LINKPRICE_BLANK.getValue());
					return response;
				}
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.LINKPRICE_EXCEPTION.getCode(), Constants.LINKPRICE_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " linkPriceReward api request : {}, entity : {}, exception : {}", request, cpsRewardEntityList, e);
		}
		return response;
	}

	public CpsRewardEntity linkPriceRewardEntity(CpsRewardEntity cpsRewardEntity, CpsRewardPacket.RewardInfo.LinkData link, CommissionDto commissionDto, String reward, String cancelFlag, String rewardYn) {
		if(null == cpsRewardEntity) cpsRewardEntity = new CpsRewardEntity();

		int commission = link.getCommission();
		double memberCommissionShareDouble = (double) commissionDto.getMemberCommissionShare() / 10;
		double userCommissionShareDouble = (double) commissionDto.getUserCommissionShare() / 10;
		BigDecimal pointRateBigDecimal = commissionDto.getPointRate();
		double pointRate = pointRateBigDecimal.doubleValue();

		//기존 수량과 현재 수량이 다른 경우 insert 로직 변경 그 외 업데이트
		if (reward.equals("cancelReward") && cpsRewardEntity.getProductCnt() > Integer.parseInt(link.getIt_cnt())) {
			cpsRewardEntity = new CpsRewardEntity();
		}

		if (null != link.getYyyymmdd()) {
			cpsRewardEntity.setRegDay(Integer.parseInt(link.getYyyymmdd()));
			cpsRewardEntity.setRegYm(Integer.parseInt(link.getYyyymmdd().substring(0, 6)));
			cpsRewardEntity.setRegHour(link.getHhmiss().substring(0, 2));
		} else {
			cpsRewardEntity.setRegDay(commissionDto.getCpsClickEntity().getRegDay());
			cpsRewardEntity.setRegYm(commissionDto.getCpsClickEntity().getRegYm());
			cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
		}

		cpsRewardEntity.setClickNum(link.getUser_id());
		cpsRewardEntity.setOrderNo(link.getO_cd());
		cpsRewardEntity.setProductCode(link.getP_cd());
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
		cpsRewardEntity.setStatus(100);
		cpsRewardEntity.setMemberName(commissionDto.getMemberName());
		cpsRewardEntity.setProductName(link.getP_nm());

		if (reward.equals("cancelReward")) {
			if(cancelFlag.equals("Y")) {
				//취소 호출 시 링크프라이스에서 가져온 수량, 금액으로 취소 처리
				cpsRewardEntity.setStatus(310);
			}else {
				//주문 호출 시 링크프라이스에서 가져온 수량, 금액으로 주문 처리
				cpsRewardEntity.setStatus(100);
			}
			cpsRewardEntity.setProductCnt(Integer.parseInt(link.getIt_cnt()));
			cpsRewardEntity.setProductPrice(Integer.parseInt(link.getSales()));
		} else if (reward.equals("reward")) {
			if (cpsRewardEntity.getProductCnt() > Integer.parseInt(link.getIt_cnt())) {
				cpsRewardEntity.setProductCnt(cpsRewardEntity.getProductCnt() - Integer.parseInt(link.getIt_cnt()));
				cpsRewardEntity.setProductPrice(cpsRewardEntity.getProductPrice() - Integer.parseInt(link.getSales()));
				commission = cpsRewardEntity.getCommission() - link.getCommission();

				if(cancelFlag.equals("N")) {
					//주문 호출 시 기존 일반 데이터는 취소 처리
					cpsRewardEntity.setStatus(310);
				}
			} else {
				cpsRewardEntity.setStatus(100);
				cpsRewardEntity.setProductCnt(Integer.parseInt(link.getIt_cnt()));
				cpsRewardEntity.setProductPrice(Integer.parseInt(link.getSales()));
				commission = link.getCommission();
			}
		} else if (reward.equals("cancel")) {
			if(cancelFlag.equals("Y")) {
				//취소 호출 시 기존 데이터는 취소 처리
				cpsRewardEntity.setStatus(310);
			} else {
				//주문 호출 시 기존 데이터가 있는 경우 최신 데이터로 업데이트
				cpsRewardEntity.setProductCnt(Integer.parseInt(link.getIt_cnt()));
				cpsRewardEntity.setProductPrice(Integer.parseInt(link.getSales()));
			}
		}

		if (rewardYn.equals("Y")) {
			if (cpsRewardEntity.getStatus() == 100) {
				cpsRewardEntity.setStatus(210);
			}
		}

		//커미션 매출
		cpsRewardEntity.setCommission(commission);
		//커미션 순이익 (커미션 매출 - 매체 커미션)
		cpsRewardEntity.setCommissionProfit(commission - (int) (commission * memberCommissionShareDouble));
		//매체 커미션 (커미션 매출 * 매체쉐어)
		cpsRewardEntity.setAffliateCommission((int) (commission * memberCommissionShareDouble));
		//유저 커미션 (매체 커미션 * 유저쉐어)
		cpsRewardEntity.setUserCommission((int) (((commission * memberCommissionShareDouble) * userCommissionShareDouble) * pointRate));
		cpsRewardEntity.setCommissionRate("0");

		cpsRewardEntity.setBaseCommission("0");
		cpsRewardEntity.setIncentiveCommission("0");

		return cpsRewardEntity;
	}

	public CpsRewardFirstEntity cpsRewardEntityList(CpsRewardEntity rewardEntity){
		CpsRewardFirstEntity cpsRewardFirstEntity = new CpsRewardFirstEntity();
		cpsRewardFirstEntity.setClickNum(rewardEntity.getClickNum());
		cpsRewardFirstEntity.setRegDay(rewardEntity.getRegDay());
		cpsRewardFirstEntity.setRegYm(rewardEntity.getRegYm());
		cpsRewardFirstEntity.setRegHour(rewardEntity.getRegHour());
		cpsRewardFirstEntity.setOrderNo(rewardEntity.getOrderNo());
		cpsRewardFirstEntity.setProductCode(rewardEntity.getProductCode());
		cpsRewardFirstEntity.setCampaignNum(rewardEntity.getCampaignNum());
		cpsRewardFirstEntity.setMerchantId(rewardEntity.getMerchantId());
		cpsRewardFirstEntity.setAgencyId(rewardEntity.getAgencyId());
		cpsRewardFirstEntity.setAffliateId(rewardEntity.getAffliateId());
		cpsRewardFirstEntity.setZoneId(rewardEntity.getZoneId());
		cpsRewardFirstEntity.setSite(rewardEntity.getSite());
		cpsRewardFirstEntity.setOs(rewardEntity.getOs());
		cpsRewardFirstEntity.setType(rewardEntity.getType());
		cpsRewardFirstEntity.setUserId(rewardEntity.getUserId());
		cpsRewardFirstEntity.setAdId(rewardEntity.getAdId());
		cpsRewardFirstEntity.setMemberName(rewardEntity.getMemberName());
		cpsRewardFirstEntity.setProductName(rewardEntity.getProductName());
		cpsRewardFirstEntity.setProductCnt(rewardEntity.getProductCnt());
		cpsRewardFirstEntity.setProductPrice(rewardEntity.getProductPrice());
		cpsRewardFirstEntity.setCommission(rewardEntity.getCommission());
		cpsRewardFirstEntity.setCommissionProfit(rewardEntity.getCommissionProfit());
		cpsRewardFirstEntity.setAffliateCommission(rewardEntity.getAffliateCommission());
		cpsRewardFirstEntity.setUserCommission(rewardEntity.getUserCommission());
		cpsRewardFirstEntity.setCommissionRate(rewardEntity.getCommissionRate());
		cpsRewardFirstEntity.setStatus(rewardEntity.getStatus());
		cpsRewardFirstEntity.setBaseCommission(rewardEntity.getBaseCommission());
		cpsRewardFirstEntity.setIncentiveCommission(rewardEntity.getIncentiveCommission());
		cpsRewardFirstEntity.setIpAddress(rewardEntity.getIpAddress());
		return cpsRewardFirstEntity;
	}
}
