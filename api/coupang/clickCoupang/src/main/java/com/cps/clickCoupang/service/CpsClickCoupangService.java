package com.cps.clickCoupang.service;

import com.cps.common.dto.ClickDto;
import com.cps.common.entity.CpsClickEntity;
import com.cps.common.packet.CpsClickPacket;
import com.cps.clickCoupang.packet.CpsCoupangClickPacket;
import com.cps.common.repository.CpsClickRepository;
import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsClickCoupangService {

	private final CpsClickRepository cpsClickRepository;

	@Value("${coupang.click.domain.url}") String coupangClickDomain;
	@Value("${coupang.subId}") String coupangSubId;
	@Value("${coupang.lptag}") String coupangLpTag;

	/**
	 * 클릭정보 저장
	 * @date 2024-10-08
	 */
	public GenericBaseResponse<ClickDto> campaignClick(CpsClickPacket.ClickInfo.ClickRequest request) throws Exception {
		CpsClickPacket.ClickInfo.Response response = new CpsClickPacket.ClickInfo.Response();
		CpsClickEntity cpsClickEntity = new CpsClickEntity();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();

		try {
			cpsClickEntity.setCampaignNum(request.getCampaignNum());
			cpsClickEntity.setRegDay(Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
			cpsClickEntity.setRegYm(Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"))));
			cpsClickEntity.setRegHour(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH")));
			cpsClickEntity.setCampaignNum(request.getCampaignNum());
			cpsClickEntity.setAffliateId(request.getAffliateId());
			cpsClickEntity.setZoneId(request.getZoneId());
			cpsClickEntity.setAgencyId(request.getAgencyId());
			cpsClickEntity.setMerchantId(request.getMerchantId());
			cpsClickEntity.setType(request.getType());
			cpsClickEntity.setSite(request.getSite());
			cpsClickEntity.setClickUrl(request.getClickUrl());
			cpsClickEntity.setOs(request.getOs());
			cpsClickEntity.setUserId(request.getUserId());
			cpsClickEntity.setAdId(request.getAdId());
			cpsClickEntity.setRewardYn("N");
			cpsClickEntity.setIpAddress(ipAddress);

			if (!"".equals(request.getClickUrl())) {
				if (null == cpsClickRepository.save(cpsClickEntity)) {
					response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
					return response;
				} else {
						CpsCoupangClickPacket.CoupangClickInfo.CoupangClickRequest clickRequest = new CpsCoupangClickPacket.CoupangClickInfo.CoupangClickRequest();
						clickRequest.setSubparam(String.valueOf(cpsClickEntity.getClickNum()));
						clickRequest.setSubid(coupangSubId);
						clickRequest.setLpttag(coupangLpTag);
						String outputUrl = String.format("?lptag=%s&subid=%s&subparam=%s", clickRequest.getLpttag(), clickRequest.getSubid(), clickRequest.getSubparam());

						ClickDto clickDto = new ClickDto();
						clickDto.setClickNum(cpsClickEntity.getClickNum());
						clickDto.setClickUrl(request.getClickUrl() + outputUrl);

						response.setSuccess();
						response.setData(clickDto);
				}
			} else {
				response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
				return response;
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " campaignClick request : {}, exception : {}", request, e);
			throw (e);
		}

		return response;
	}
}
