package com.cps.agencyService.service;

import com.cps.agencyService.dto.RewardDotPitchDto;
import com.cps.agencyService.entity.CpsClickEntity;
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
	public GenericBaseResponse<RewardDotPitchDto> realTime(CpsRewardDotPitchPacket.RewardInfo.RealTimeRequest request) throws Exception {
		CpsRewardDotPitchPacket.RewardInfo.RewardResponse response = new CpsRewardDotPitchPacket.RewardInfo.RewardResponse();
		CpsRewardEntity cpsRewardEntity = new CpsRewardEntity();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();

		try {
			CpsClickEntity cpsClickEntity = cpsClickRepository.findByClickNum(Integer.parseInt(request.getR_Keyid()));
			if (cpsClickEntity != null) {
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
					cpsRewardEntity.setRegDay(cpsClickEntity.getRegDay());
					cpsRewardEntity.setRegYm(cpsClickEntity.getRegYm());
					cpsRewardEntity.setRegHour(cpsClickEntity.getRegHour());
				}

				cpsRewardEntity.setMemberId("dot_"+cpsClickEntity.getMemberId());
				cpsRewardEntity.setAgencyId(cpsClickEntity.getAgencyId());
				cpsRewardEntity.setCampaignNum(cpsClickEntity.getCampaignNum());
				cpsRewardEntity.setAffliateId(cpsClickEntity.getAffliateId());
				cpsRewardEntity.setZoneId(cpsClickEntity.getZoneId());
				cpsRewardEntity.setSite(cpsClickEntity.getSite());
				cpsRewardEntity.setOs(cpsClickEntity.getOs());
				cpsRewardEntity.setType(cpsClickEntity.getType());
				cpsRewardEntity.setUserId(cpsClickEntity.getUserId());
				cpsRewardEntity.setAdId(cpsClickEntity.getAdId());
				cpsRewardEntity.setStatus(100);
				if (!request.getR_Gubun().equals("GMV")) {
					cpsRewardEntity.setStatus(300);
				}
				cpsRewardEntity.setMemberName(request.getR_Mid());
				cpsRewardEntity.setProductName(request.getR_ProdNm());
				cpsRewardEntity.setProductCnt(request.getR_Quantity());
				cpsRewardEntity.setProductPrice(request.getR_OrdPrice());
				cpsRewardEntity.setCommission(request.getR_CommRate());
				cpsRewardEntity.setIpAddress(ipAddress);

				if (null == cpsRewardRepository.save(cpsRewardEntity)) {
					response.setApiMessage(Constants.DOTPITCH_EXCEPTION.getCode(), Constants.DOTPITCH_EXCEPTION.getValue());
					return response;
				} else {
					RewardDotPitchDto rewardDotPitchDto = new RewardDotPitchDto();
					response.setSuccess();
					response.setData(rewardDotPitchDto);
				}
			} else {
				response.setApiMessage(Constants.DOTPITCH_BLANK.getCode(), Constants.DOTPITCH_BLANK.getValue());
				return response;
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.DOTPITCH_EXCEPTION.getCode(), Constants.DOTPITCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " dotPitch request : {}, exception : {}", request, e);
		}

		return response;
	}
}
