package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CommissionDto;
import com.cps.cpsApi.dto.DotPitchRewardDto;
import com.cps.cpsApi.entity.CpsClickEntity;
import com.cps.cpsApi.entity.CpsRewardEntity;
import com.cps.cpsApi.packet.CpsRewardPacket;
import com.cps.cpsApi.repository.CpsClickRepository;
import com.cps.cpsApi.repository.CpsRewardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsRewardService {
	@Value("${dotPitch.domain.url}") String dorPriceDomain;

	@Value("${dotPitch.reco.cps.url}") String dorPriceCpsEndPoint;

	@Value("${dotPitch.reco.order.url}") String dorPriceOrderEndPoint;

	@Value("${dotPitch.id}") String dotPitchId;

	@Value("${dotPitch.pw}") String dotPtichPw;

	private final HttpService httpService;

	private final CpsClickRepository cpsClickRepository;

	private final CpsRewardRepository cpsRewardRepository;

	/**
	 * 도트피치 익일, 취소내역 호출
	 * @date 2024-09-23
	 */
	public GenericBaseResponse<DotPitchRewardDto> dotPitchReward(CpsRewardPacket.RewardInfo.DotPitchRequest request) throws Exception {
		CpsRewardPacket.RewardInfo.DotPitchResponse response = new CpsRewardPacket.RewardInfo.DotPitchResponse();
		List<CpsRewardEntity> cpsRewardEntityList = new ArrayList<>();

		String endPoint = dorPriceCpsEndPoint;
		if(null != request.getSearch_type()){endPoint = dorPriceOrderEndPoint;}
		String domain = dorPriceDomain + endPoint;

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
						if (dot.getM_name().equals("11번가")) {
							cpsRewardEntity.setRegDay(Integer.parseInt(dot.getOrderid().substring(0, 8)));
							cpsRewardEntity.setRegYm(Integer.parseInt(dot.getOrderid().substring(0, 6)));
							cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
						} else if (dot.getM_name().equals("옥션")) {
							cpsRewardEntity.setRegDay(commissionDto.getCpsClickEntity().getRegDay());
							cpsRewardEntity.setRegYm(commissionDto.getCpsClickEntity().getRegYm());
							cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
						}
						cpsRewardEntity.setCampaignNum(commissionDto.getCpsClickEntity().getCampaignNum());
						cpsRewardEntity.setMemberId(commissionDto.getCpsClickEntity().getMemberId());
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
						cpsRewardEntity.setMemberName(dot.getM_name());
						cpsRewardEntity.setProductName(dot.getP_name());
						cpsRewardEntity.setProductCnt(Integer.parseInt(dot.getQuantity()));
						cpsRewardEntity.setProductPrice(Integer.parseInt(dot.getPrice()));

						int commission = dot.getComm();
						double memberCommissionShareDouble = (double) commissionDto.getMemberCommissionShare() / 10;
						double userCommissionShareDouble = (double) commissionDto.getUserCommissionShare() / 10;

						//커미션 매출
						cpsRewardEntity.setCommission(commission);
						//커미션 순이익 (커미션 매출 - 매체 커미션)
						cpsRewardEntity.setCommissionProfit(commission - (int) (commission * memberCommissionShareDouble));
						//매체 커미션 (커미션 매출 * 매체쉐어)
						cpsRewardEntity.setAffliateCommission((int) (commission * memberCommissionShareDouble));
						//유저 커미션 (매체 커미션 * 유저쉐어)
						cpsRewardEntity.setUserCommission((int) ((commission * memberCommissionShareDouble) * userCommissionShareDouble));
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
}