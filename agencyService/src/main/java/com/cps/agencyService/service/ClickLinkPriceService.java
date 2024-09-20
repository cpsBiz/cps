package com.cps.agencyService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.agencyService.dto.ClickDto;
import com.cps.agencyService.entity.ClickEntity;
import com.cps.agencyService.packet.ClickPacket;
import com.cps.agencyService.repository.ClickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClickLinkPriceService {

	private final ClickRepository clickRepository;

	/**
	 * 클릭정보 저장
	 * @date 2024-09-04
	 */
	public GenericBaseResponse<ClickDto> campaignClick(ClickPacket.ClickInfo.ClickRequest request) throws Exception {
		ClickPacket.ClickInfo.Response response = new ClickPacket.ClickInfo.Response();
		ClickEntity clickEntity = new ClickEntity();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();

		try {
			clickEntity.setCampaignNum(request.getCampaignNum());
			clickEntity.setRegDay(Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
			clickEntity.setRegYm(Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"))));
			clickEntity.setRegHour(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH")));
			clickEntity.setCampaignNum(request.getCampaignNum());
			clickEntity.setAffliateId(request.getAffliateId());
			clickEntity.setZoneId(request.getZoneId());
			clickEntity.setAgencyId(request.getAgencyId());
			clickEntity.setMemberId(request.getMemberId());
			clickEntity.setType(request.getType());
			clickEntity.setSite(request.getSite());
			clickEntity.setOs(request.getOs());
			clickEntity.setUserId(request.getUserId());
			clickEntity.setRewardYn("N");
			clickEntity.setCode("9999");
			clickEntity.setIpAddress(ipAddress);

			if (null == clickRepository.save(clickEntity)) {
				response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
				return response;
			} else {
				ClickDto clickDto = new ClickDto();
				clickDto.setClickNum(clickEntity.getClickNum());
				clickEntity.setCode("200");
				clickEntity.setMessage("성공");
				clickRepository.save(clickEntity);

				response.setSuccess();
				response.setData(clickDto);
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberSignIn  {}", e);
		}

		return response;
	}
}
