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
import com.cps.cpsService.repository.CpsClickRepository;
import com.cps.cpsService.repository.CpsRewardFirstRepository;
import com.cps.cpsService.repository.CpsRewardRepository;
import com.cps.cpsService.repository.CpsRewardUnitRepository;
import com.cps.cpsService.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
public class CpsRewardDotPitchService {
	@Value("${dotPitch.domain.url}") String dotPitchDomain;

	@Value("${dotPitch.reco.cps.url}") String dotPitchCpsEndPoint;

	@Value("${dotPitch.reco.order.url}") String dotPitchOrderEndPoint;

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
						double memberCommissionShareDouble = (double) commissionDto.getMemberCommissionShare() / 100;
						double userCommissionShareDouble = (double) commissionDto.getUserCommissionShare() / 100;
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
}
