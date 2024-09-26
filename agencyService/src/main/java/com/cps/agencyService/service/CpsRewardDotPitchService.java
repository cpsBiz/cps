package com.cps.agencyService.service;

import com.cps.agencyService.dto.CommissionDto;
import com.cps.agencyService.dto.RewardDto;
import com.cps.agencyService.entity.CpsRewardEntity;
import com.cps.agencyService.packet.CpsRewardDotPitchPacket;
import com.cps.agencyService.repository.CpsClickRepository;
import com.cps.agencyService.repository.CpsRewardRepository;
import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsRewardDotPitchService {

	private final CpsClickRepository cpsClickRepository;

	private final CpsRewardRepository cpsRewardRepository;

	/**
	 * 클릭정보 실시간 저장
	 * @date 2024-09-23
	 */
	public GenericBaseResponse<RewardDto> realTime(CpsRewardDotPitchPacket.RewardInfo.RealTimeRequest request) throws Exception {
		CpsRewardDotPitchPacket.RewardInfo.RewardResponse response = new CpsRewardDotPitchPacket.RewardInfo.RewardResponse();
		CpsRewardEntity cpsRewardEntity = new CpsRewardEntity();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		
		List<Integer> resultList = Collections.singletonList(Integer.parseInt(request.getR_Keyid()));
		try {
			CommissionDto commissionDto = cpsClickRepository.findClickCommission(Integer.parseInt(request.getR_Keyid()));
			if (commissionDto != null) {
				cpsRewardEntity.setClickNum(Integer.parseInt(request.getR_Keyid()));
				cpsRewardEntity.setOrderNo(request.getR_Ordid());
				cpsRewardEntity.setProductCode(request.getR_Ordid());

				if (null != request.getR_Date()) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime dateTime = LocalDateTime.parse(request.getR_Date(), formatter);
					int regDay = dateTime.getYear() * 10000 + dateTime.getMonthValue() * 100 + dateTime.getDayOfMonth();
					cpsRewardEntity.setRegDay(regDay);
					int regYm = dateTime.getYear() * 100 + dateTime.getMonthValue();
					cpsRewardEntity.setRegYm(regYm);
					String regHour = String.format("%02d", dateTime.getHour());  // 두 자리로 맞춰주기
					cpsRewardEntity.setRegHour(regHour);
				} else {
					cpsRewardEntity.setRegDay(commissionDto.getCpsClickEntity().getRegDay());
					cpsRewardEntity.setRegYm(commissionDto.getCpsClickEntity().getRegYm());
					cpsRewardEntity.setRegHour(commissionDto.getCpsClickEntity().getRegHour());
				}

				cpsRewardEntity.setMemberId(commissionDto.getCpsClickEntity().getMemberId());
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
				if (!request.getR_Gubun().equals("GMV")) {
					cpsRewardEntity.setStatus(300);
				}
				cpsRewardEntity.setMemberName(request.getR_Mid());
				cpsRewardEntity.setProductName(request.getR_ProdNm());
				cpsRewardEntity.setProductCnt(request.getR_Quantity());
				cpsRewardEntity.setProductPrice(request.getR_OrdPrice());

				int commission = request.getR_CommPrice();
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

				cpsRewardEntity.setCommissionRate(String.valueOf(request.getR_CommRate()));
				cpsRewardEntity.setBaseCommission("0");
				cpsRewardEntity.setIncentiveCommission("0");
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
				response.setApiMessage(Constants.DOTPITCH_BLANK.getCode(), Constants.DOTPITCH_BLANK.getValue());
				return response;
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.DOTPITCH_EXCEPTION.getCode(), Constants.DOTPITCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " dotPitch realTime request : {}, exception : {}", request, e);
		}

		return response;
	}
}
