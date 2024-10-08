package com.cps.api.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.common.utils.HmacGenerator;
import com.cps.cpsService.dto.CommissionDto;
import com.cps.cpsService.dto.CpsRewardUnitDto;
import com.cps.cpsService.dto.CpsViewDto;
import com.cps.cpsService.dto.DotPitchRewardDto;
import com.cps.cpsService.entity.CpsRewardEntity;
import com.cps.cpsService.entity.CpsRewardUnitEntity;
import com.cps.cpsService.packet.CpsRewardPacket;
import com.cps.cpsService.repository.CpsClickRepository;
import com.cps.cpsService.repository.CpsRewardRepository;
import com.cps.cpsService.repository.CpsRewardUnitRepository;
import com.cps.cpsService.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsRewardService {
	@Value("${dotPitch.domain.url}") String dotPitchDomain;

	@Value("${dotPitch.reco.cps.url}") String dotPitchCpsEndPoint;

	@Value("${dotPitch.reco.order.url}") String dotPitchOrderEndPoint;

	@Value("${dotPitch.id}") String dotPitchId;

	@Value("${dotPitch.pw}") String dotPtichPw;

	@Value("${linkPrice.domain.url}") String linkPriceDomain;

	@Value("${linkPrice.domain.reward.url}") String linkPriceReward;

	@Value("${linkPrice.authKey}") String linkPriceAuthKey;

	@Value("${linkPrice.site.code}") String linkPriceSiteCode;

	@Value("${coupang.domain.url}") String coupangDomain;

	@Value("${coupang.domain.order.url}") String coupangOrderDomain;

	@Value("${coupang.domain.cancel.url}") String coupangCancelDomain;

	@Value("${coupang.subId}") String coupangSubId;

	private final HttpService httpService;

	private final CpsClickRepository cpsClickRepository;

	private final CpsRewardRepository cpsRewardRepository;

	private final CpsRewardUnitRepository cpsRewardUnitRepository;

	/**
	 * 도트피치 익일, 취소내역 호출
	 * @date 2024-09-23
	 */
	public GenericBaseResponse<DotPitchRewardDto> dotPitchReward(CpsRewardPacket.RewardInfo.DotPitchRequest request) throws Exception {
		CpsRewardPacket.RewardInfo.RewardResponse response = new CpsRewardPacket.RewardInfo.RewardResponse();
		List<CpsRewardEntity> cpsRewardEntityList = new ArrayList<>();

		String endPoint = dotPitchCpsEndPoint;
		if(null != request.getSearch_type()){endPoint = dotPitchOrderEndPoint;}
		String domain = dotPitchDomain + endPoint;

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		AtomicReference<String> rewardYn = new AtomicReference<>("R"); //구매리워드 단계

		request.setLog_id(dotPitchId);
		request.setLog_pw(dotPtichPw);

		try {
			CpsRewardPacket.RewardInfo.DotPitchListResponse dotPitchResponseList = httpService.SendDotPitchReward(domain, request);

			if (dotPitchResponseList.getListData() != null) {
				dotPitchResponseList.getListData().forEach(dot ->{
					CommissionDto commissionDto = cpsClickRepository.findClickCommission(Integer.parseInt(dot.getA_info()));
					if (null != commissionDto) {
						CpsRewardEntity cpsRewardEntity = new CpsRewardEntity();
						cpsRewardEntity.setClickNum(Integer.parseInt(dot.getA_info()));
						cpsRewardEntity.setOrderNo(dot.getOrderid());
						cpsRewardEntity.setProductCode(dot.getOrderid());

						//11번가 주문번호로 구매일자 넣기, 구매시간은 CLICK 기준, 옥션은 구매일자를 주지 않아 클릭 기준으로 날짜 넣기
						if (dot.getM_name().equals("11번가_PC") || dot.getM_name().equals("11번가_모바일")) {
							cpsRewardEntity.setRegDay(Integer.parseInt(dot.getOrderid().substring(0, 8)));
							cpsRewardEntity.setRegYm(Integer.parseInt(dot.getOrderid().substring(0, 6)));
							cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
						} else if (dot.getM_name().equals("옥션_PC") || dot.getM_name().equals("옥션_모바일")) {
							cpsRewardEntity.setRegDay(commissionDto.getCpsClickEntity().getRegDay());
							cpsRewardEntity.setRegYm(commissionDto.getCpsClickEntity().getRegYm());
							cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
						}

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

						if (null != dot.getOrder_flag()) {
							if ("확정".equals(dot.getOrder_flag())) {
								cpsRewardEntity.setStatus(210);
							} else {
								cpsRewardEntity.setStatus(310);
							}
							rewardYn.set("Y"); //구매 확정 취소 여부 확인 완료
						}

						cpsRewardEntity.setMemberName(commissionDto.getMemberName());

						//지마켓 경우 인코딩
						if (dot.getM_name().equals("G마켓_PC") || dot.getM_name().equals("G마켓_모바일")) {
							cpsRewardEntity.setProductName(URLDecoder.decode(dot.getP_name(), StandardCharsets.UTF_8));
						} else {
							cpsRewardEntity.setProductName(dot.getP_name());
						}
						cpsRewardEntity.setProductCnt(Integer.parseInt(dot.getQuantity()));
						cpsRewardEntity.setProductPrice(Integer.parseInt(dot.getPrice()));

						int commission = dot.getComm();
						double memberCommissionShareDouble = (double) commissionDto.getMemberCommissionShare() / 10;
						double userCommissionShareDouble = (double) commissionDto.getUserCommissionShare() / 10;
						BigDecimal pointRateBigDecimal = commissionDto.getPointRate();
						double pointRate = pointRateBigDecimal.doubleValue();

						//커미션 매출
						cpsRewardEntity.setCommission(commission);
						//커미션 순이익 (커미션 매출 - 매체 커미션)
						cpsRewardEntity.setCommissionProfit(commission - (int) (commission * memberCommissionShareDouble));
						//매체 커미션 (커미션 매출 * 매체쉐어)
						cpsRewardEntity.setAffliateCommission((int) (commission * memberCommissionShareDouble));
						//유저 커미션 (매체 커미션 * 유저쉐어)
						cpsRewardEntity.setUserCommission((int) (((commission * memberCommissionShareDouble) * userCommissionShareDouble) * pointRate));
						cpsRewardEntity.setCommissionRate(dot.getCommission_rate());

						cpsRewardEntity.setBaseCommission("0");
						cpsRewardEntity.setIncentiveCommission("0");
						cpsRewardEntity.setIpAddress(ipAddress);
						cpsRewardEntityList.add(cpsRewardEntity);

						if (cpsRewardEntityList.size() == 1000) {
							//도트피치 리워드 저장
							cpsRewardRepository.saveAll(cpsRewardEntityList);

							//도트피치 CLICK REWARD 업데이트
							cpsClickRepository.updateRewardYnByClickNumList(rewardYn.get(), cpsRewardEntityList.stream().map(CpsRewardEntity::getClickNum).collect(Collectors.toList()));
							response.setSuccess();

							cpsRewardEntityList.clear();
						}
					}
				});

				if (cpsRewardEntityList.size() > 0) {
					//도트피치 리워드 저장
					cpsRewardRepository.saveAll(cpsRewardEntityList);
					//도트피치 CLICK 업데이트
					cpsClickRepository.updateRewardYnByClickNumList(rewardYn.get(), cpsRewardEntityList.stream().map(CpsRewardEntity::getClickNum).collect(Collectors.toList()));
					response.setSuccess();
				}

			} else {
				response.setApiMessage(Constants.DOTPITCH_BLANK.getCode(), Constants.DOTPITCH_BLANK.getValue());
				return response;
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.DOTPITCH_EXCEPTION.getCode(), Constants.DOTPITCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " dotPitchReward api request : {}, entity : {}, exception : {}", request, cpsRewardEntityList, e);
		}

		return response;
	}

	/**
	 * 링크프라이스 실적조회 호출
	 * @date 2024-09-25
	 */
	public GenericBaseResponse<DotPitchRewardDto> linkPriceReward(CpsRewardPacket.RewardInfo.LinkPriceRequest request, String rewardYn) throws Exception {
		CpsRewardPacket.RewardInfo.RewardResponse response = new CpsRewardPacket.RewardInfo.RewardResponse();
		List<CpsRewardEntity> cpsRewardEntityList = new ArrayList<>();
		String domain = linkPriceDomain + linkPriceReward;

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		request.setAuth_key(linkPriceAuthKey);
		request.setA_id(linkPriceSiteCode);
		try {
			for (int i = 1; i <= 20; i++) {
				request.setPage(i);
				CpsRewardPacket.RewardInfo.LinkPriceListResponse dotPitchResponseList = httpService.SendLinkPriceReward(domain, request);
				if (dotPitchResponseList.getOrder_list() != null) {

					//조회 성공 응답 코드가 아닌 경우
					if (!dotPitchResponseList.getResult().equals("0")) {
						//정상 페이지 호출이 아닌 경우
						if (dotPitchResponseList.getResult().equals("101")) {
							break;
						} else if (dotPitchResponseList.getResult().equals("100")) {
							log.error("linkPriceReward 100 : {}, result : {}", i, "A코드 (사이트코드)(a_id) 없음");
						} else if (dotPitchResponseList.getResult().equals("200")) {
							log.error("linkPriceReward 100 : {}, result : {}", i, "조회일자(yyyymmdd) 없음");
						} else if (dotPitchResponseList.getResult().equals("210")) {
							log.error("linkPriceReward 100 : {}, result : {}", i, "조회일자(yyyymmdd) 길이 오류");
						} else if (dotPitchResponseList.getResult().equals("300")) {
							log.error("linkPriceReward 100 : {}, result : {}", i, "인증키(auth_key)가 맞지 않음");
						} else if (dotPitchResponseList.getResult().equals("400")) {
							log.error("linkPriceReward 100 : {}, result : {}", i, "통화단위(currency)를 지원하지 않음");
						}
					}

					dotPitchResponseList.getOrder_list().forEach(link -> {
						CommissionDto commissionDto = cpsClickRepository.findClickCommission(link.getUser_id());
						if (null != commissionDto) {
							CpsRewardEntity cpsRewardEntity = new CpsRewardEntity();
							cpsRewardEntity.setClickNum(link.getUser_id());
							cpsRewardEntity.setOrderNo(link.getO_cd());
							cpsRewardEntity.setProductCode(link.getP_cd());

							if (null != link.getYyyymmdd()) {
								cpsRewardEntity.setRegDay(Integer.parseInt(link.getYyyymmdd()));
								cpsRewardEntity.setRegYm(Integer.parseInt(link.getYyyymmdd().substring(0, 6)));
								cpsRewardEntity.setRegHour(link.getHhmiss().substring(0, 2));
							} else {
								cpsRewardEntity.setRegDay(commissionDto.getCpsClickEntity().getRegDay());
								cpsRewardEntity.setRegYm(commissionDto.getCpsClickEntity().getRegYm());
								cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
							}

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
							cpsRewardEntity.setStatus(link.getStatus());

							cpsRewardEntity.setMemberName(commissionDto.getMemberName());
							cpsRewardEntity.setProductName(link.getP_nm());
							cpsRewardEntity.setProductCnt(Integer.parseInt(link.getIt_cnt()));
							cpsRewardEntity.setProductPrice(Integer.parseInt(link.getSales()));

							int commission = link.getCommission();
							double memberCommissionShareDouble = (double) commissionDto.getMemberCommissionShare() / 10;
							double userCommissionShareDouble = (double) commissionDto.getUserCommissionShare() / 10;
							BigDecimal pointRateBigDecimal = commissionDto.getPointRate();
							double pointRate = pointRateBigDecimal.doubleValue();

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
							cpsRewardEntity.setIpAddress(ipAddress);
							cpsRewardEntityList.add(cpsRewardEntity);

							if (cpsRewardEntityList.size() == 1000) {
								//링크프라이스 리워드 저장
								cpsRewardRepository.saveAll(cpsRewardEntityList);

								//링크프라이스 CLICK REWARD 업데이트
								cpsClickRepository.updateRewardYnByClickNumList(rewardYn, cpsRewardEntityList.stream().map(CpsRewardEntity::getClickNum).collect(Collectors.toList()));
								response.setSuccess();

								cpsRewardEntityList.clear();
							}
						}
					});

					if (cpsRewardEntityList.size() > 0) {
						//링크프라이스 리워드 저장
						cpsRewardRepository.saveAll(cpsRewardEntityList);
						//링크프라이스 CLICK 업데이트
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

	/**
	 * 쿠팡 실적조회 호출
	 * @date 2024-10-07
	 */
	public GenericBaseResponse<DotPitchRewardDto> coupangReward(CpsRewardPacket.RewardInfo.CoupangRequest request, String cancel) throws Exception {
		CpsRewardPacket.RewardInfo.RewardResponse response = new CpsRewardPacket.RewardInfo.RewardResponse();
		List<CpsRewardEntity> cpsRewardEntityList = new ArrayList<>();

		String domain = coupangOrderDomain;
		if ("Y".equals(cancel)) {
			domain = coupangCancelDomain;
		}

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		final List<Integer> clickNumList = new ArrayList<>();

		request.setSubId(coupangSubId);

		try {
			for (int i = 0; i <= 1; i++) {
				request.setPage(i);
				CpsRewardPacket.RewardInfo.CoupangListResponse coupangResponseList = httpService.SendCoupangReward(coupangDomain, domain, request);
				if (coupangResponseList.getDataList() != null) {

					//if(i>0){coupangResponseList.setRCode("1");}

					//조회 성공 응답 코드가 아닌 경우
					if (!coupangResponseList.getRCode().equals("0")) {
						break;
					}

					coupangResponseList.getDataList().forEach(coupang -> {
						if (null != coupang.getSubParam()) {
							int clickNum = Integer.parseInt(coupang.getSubParam());
							clickNumList.add(clickNum);

							CommissionDto commissionDto = cpsClickRepository.findClickCommission(Integer.parseInt(coupang.getSubParam()));

							if (null != commissionDto) {
								CpsRewardEntity cpsRewardEntity = new CpsRewardEntity();

								if (null != coupang.getDate() && "N".equals(cancel)) {
									cpsRewardEntity.setRegDay(Integer.parseInt(coupang.getDate()));
									cpsRewardEntity.setRegYm(Integer.parseInt(coupang.getDate().substring(0, 6)));
								} else {
									cpsRewardEntity = cpsRewardRepository.findByClickNumAndOrderNoAndProductCode(Integer.parseInt(coupang.getSubParam()), coupang.getOrderId(), coupang.getProductId());
									if (null != cpsRewardEntity) {
										cpsRewardEntity.setRegDay(cpsRewardEntity.getRegDay());
										cpsRewardEntity.setRegYm(cpsRewardEntity.getRegYm());
									} else {
										cpsRewardEntity = new CpsRewardEntity();
										cpsRewardEntity.setRegDay(commissionDto.getCpsClickEntity().getRegDay());
										cpsRewardEntity.setRegYm(commissionDto.getCpsClickEntity().getRegYm());
									}
								}

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
								cpsRewardEntity.setStatus(210);

								int commission = coupang.getCommission();
								double memberCommissionShareDouble = (double) commissionDto.getMemberCommissionShare() / 10;

								if ("Y".equals(cancel)) {
									//취소 건 중 기존 수량과 다른 경우 수량, 가격 계산 후 set (부분취소)
									if(cpsRewardEntity.getProductCnt() > coupang.getQuantity()) {
										cpsRewardEntity.setProductCnt(cpsRewardEntity.getProductCnt() - coupang.getQuantity());
										cpsRewardEntity.setProductPrice(cpsRewardEntity.getProductPrice() - coupang.getGmv());
										commission = cpsRewardEntity.getCommission() - commission;
									}else{
										cpsRewardEntity.setStatus(310);
									}
								} else {
									cpsRewardEntity.setProductCnt(coupang.getQuantity());
									cpsRewardEntity.setProductPrice(coupang.getGmv());
								}

								//커미션 매출
								cpsRewardEntity.setCommission(commission);
								//커미션 순이익 (커미션 매출 - 매체 커미션)
								cpsRewardEntity.setCommissionProfit(commission - (int) (commission * memberCommissionShareDouble));
								//매체 커미션 (커미션 매출 * 매체쉐어)
								cpsRewardEntity.setAffliateCommission((int) (commission * memberCommissionShareDouble));
								//유저 커미션 (매체 커미션 * 유저쉐어)
								cpsRewardEntity.setUserCommission(0);
								cpsRewardEntity.setCommissionRate(String.valueOf(coupang.getCommissionRate()));

								cpsRewardEntity.setBaseCommission("0");
								cpsRewardEntity.setIncentiveCommission("0");
								cpsRewardEntity.setIpAddress(ipAddress);
								cpsRewardEntityList.add(cpsRewardEntity);
							}

							if (cpsRewardEntityList.size() == 1000) {
								//쿠팡 리워드 저장
								cpsRewardRepository.saveAll(cpsRewardEntityList);
								//쿠팡 CLICK 업데이트
								cpsClickRepository.updateRewardYnByClickNumList("Y", cpsRewardEntityList.stream().map(CpsRewardEntity::getClickNum).collect(Collectors.toList()));
								response.setSuccess();

								cpsRewardEntityList.clear();
							}
						}
					});

					if (cpsRewardEntityList.size() > 0) {
						//쿠팡 리워드 저장
						cpsRewardRepository.saveAll(cpsRewardEntityList);
						//쿠팡 CLICK 업데이트
						cpsClickRepository.updateRewardYnByClickNumList("Y", cpsRewardEntityList.stream().map(CpsRewardEntity::getClickNum).collect(Collectors.toList()));
						response.setSuccess();
					}
				} else {
					response.setApiMessage(Constants.COUPANG_BLANK.getCode(), Constants.COUPANG_BLANK.getValue());
					return response;
				}
			}

			List<Integer> rewordUnitList = new ArrayList<>();
			List<CpsRewardUnitEntity> cpsRewardUnitList = new ArrayList<>();

			if (clickNumList.size() > 0) {
				rewordUnitList = clickNumList.stream().distinct().collect(Collectors.toList());
				rewordUnitList.forEach(clickNum -> {
					CpsRewardUnitDto cpsRewardUnitDto = cpsRewardRepository.findByRewardClickNum(clickNum);
					CpsRewardUnitEntity cpsRewardUnitEntity = new CpsRewardUnitEntity();
					cpsRewardUnitEntity.setClickNum(clickNum);
					cpsRewardUnitEntity.setRewardCnt(cpsRewardUnitDto.getRewardCnt());
					cpsRewardUnitEntity.setRegDay(cpsRewardUnitDto.getRegDay());
					cpsRewardUnitEntity.setRegYm(cpsRewardUnitDto.getRegYm());
					cpsRewardUnitEntity.setUserId(cpsRewardUnitDto.getUserId());
					cpsRewardUnitEntity.setMerchantId(cpsRewardUnitDto.getMerchantId());
					cpsRewardUnitEntity.setAffliateId(cpsRewardUnitDto.getAffliateId());
					cpsRewardUnitEntity.setProductName(cpsRewardUnitDto.getProductName());
					cpsRewardUnitEntity.setRewardCnt(cpsRewardUnitDto.getRewardCnt());
					cpsRewardUnitEntity.setCnt(cpsRewardUnitDto.getCnt());
					cpsRewardUnitEntity.setTotalPrice(cpsRewardUnitDto.getTotalPrice());
					cpsRewardUnitEntity.setStockCnt(0);
					cpsRewardUnitEntity.setStatus(100);
					cpsRewardUnitEntity.setIpAddress(ipAddress);
					cpsRewardUnitList.add(cpsRewardUnitEntity);

					if (cpsRewardUnitList.size() == 1000) {
						//쿠팡 막대사탕 저장
						cpsRewardUnitRepository.saveAll(cpsRewardUnitList);
						cpsRewardEntityList.clear();
					}
				});

				if (cpsRewardUnitList.size() > 0) {
					//쿠팡 막대사탕 저장
					cpsRewardUnitRepository.saveAll(cpsRewardUnitList);
					cpsRewardEntityList.clear();
				}
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.COUPANG_EXCEPTION.getCode(), Constants.COUPANG_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " coupangReward api request : {}, entity : {}, exception : {}", request, cpsRewardEntityList, e);
		}
		return response;
	}
}
