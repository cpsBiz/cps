package com.cps.rewardLinkPrice.service;

import com.cps.cpsService.dto.CommissionDto;
import com.cps.cpsService.dto.RewardDto;
import com.cps.cpsService.entity.CpsRewardEntity;
import com.cps.rewardLinkPrice.packet.CpsRewardLinkPricePacket;
import com.cps.cpsService.repository.CpsClickRepository;
import com.cps.cpsService.repository.CpsRewardRepository;
import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsRewardLinkPriceService {

	private final CpsClickRepository cpsClickRepository;

	private final CpsRewardRepository cpsRewardRepository;

	/**
	 * 리워드 정보 실시간 저장
	 * @date 2024-09-25
	 */
	public GenericBaseResponse<RewardDto> realTime(CpsRewardLinkPricePacket.RewardInfo.RealTimeRequest request) throws Exception {
		CpsRewardLinkPricePacket.RewardInfo.RewardResponse response = new CpsRewardLinkPricePacket.RewardInfo.RewardResponse();
		CpsRewardEntity cpsRewardEntity = new CpsRewardEntity();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		
		List<Integer> resultList = Collections.singletonList(Integer.parseInt(request.getAffiliate_user_id()));

		try {
			CommissionDto commissionDto = cpsClickRepository.findClickCommission(Integer.parseInt(request.getAffiliate_user_id()));
			if (commissionDto != null) {
				cpsRewardEntity.setClickNum(Integer.parseInt(request.getAffiliate_user_id()));
				cpsRewardEntity.setOrderNo(request.getOrder_code());
				cpsRewardEntity.setProductCode(request.getProduct_code());

				if (null != request.getDay()) {
					cpsRewardEntity.setRegDay(Integer.parseInt(request.getDay()));
					cpsRewardEntity.setRegYm(Integer.parseInt(request.getDay().substring(0,6)));
					cpsRewardEntity.setRegHour(request.getTime().substring(0,2));
				} else {
					cpsRewardEntity.setRegDay(commissionDto.getCpsClickEntity().getRegDay());
					cpsRewardEntity.setRegYm(commissionDto.getCpsClickEntity().getRegYm());
					cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
				}

				cpsRewardEntity.setMerchantId(commissionDto.getCpsClickEntity().getMerchantId());
				cpsRewardEntity.setAgencyId(commissionDto.getCpsClickEntity().getAgencyId());
				cpsRewardEntity.setCampaignNum(commissionDto.getCpsClickEntity().getCampaignNum());
				cpsRewardEntity.setAffliateId(commissionDto.getCpsClickEntity().getAffliateId());
				cpsRewardEntity.setZoneId(commissionDto.getCpsClickEntity().getZoneId());
				cpsRewardEntity.setSite(commissionDto.getCpsClickEntity().getSite());
				cpsRewardEntity.setOs(commissionDto.getCpsClickEntity().getOs());
				cpsRewardEntity.setType(commissionDto.getCpsClickEntity().getType());
				cpsRewardEntity.setUserId(commissionDto.getCpsClickEntity().getUserId());
				cpsRewardEntity.setAdId(commissionDto.getCpsClickEntity().getAdId());
				cpsRewardEntity.setStatus(100);

				cpsRewardEntity.setMemberName(commissionDto.getMemberName());
				cpsRewardEntity.setProductName(request.getProduct_name());
				cpsRewardEntity.setProductCnt(request.getItem_count());
				cpsRewardEntity.setProductPrice(request.getPrice());

				int commission = request.getCommision();
				double memberCommissionShareDouble = (double) commissionDto.getMemberCommissionShare() / 100;
				double userCommissionShareDouble = (double) commissionDto.getUserCommissionShare() / 100;

				//커미션 매출
				cpsRewardEntity.setCommission(commission);
				//커미션 순이익 (커미션 매출 - 매체 커미션)
				cpsRewardEntity.setCommissionProfit(commission - (int) (commission * memberCommissionShareDouble));
				//매체 커미션 (커미션 매출 * 매체쉐어)
				cpsRewardEntity.setAffliateCommission((int) (commission * memberCommissionShareDouble));
				//유저 커미션 (매체 커미션 * 유저쉐어)
				cpsRewardEntity.setUserCommission((int) ((commission * memberCommissionShareDouble) * userCommissionShareDouble));

				cpsRewardEntity.setCommissionRate(request.getBase_commission());
				cpsRewardEntity.setBaseCommission(request.getBase_commission());
				cpsRewardEntity.setIncentiveCommission(request.getIncentive_commission());
				cpsRewardEntity.setIpAddress(ipAddress);

				if (null == cpsRewardRepository.save(cpsRewardEntity)) {
					response.setApiMessage(Constants.DOTPITCH_EXCEPTION.getCode(), Constants.DOTPITCH_EXCEPTION.getValue());
					return response;
				} else {					
					//클릭 테이블 리워드 상태로 업데이트
					cpsClickRepository.updateRewardYnByClickNumList("R", resultList);
					RewardDto rewardDto = new RewardDto();
					response.setSuccess();
					response.setData(rewardDto);
				}
			} else {
				response.setApiMessage(Constants.LINKPRICE_BLANK.getCode(), Constants.LINKPRICE_BLANK.getValue());
				return response;
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.LINKPRICE_EXCEPTION.getCode(), Constants.LINKPRICE_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " linkPrice realTime request : {}, exception : {}", request, e);
		}

		return response;
	}
}
