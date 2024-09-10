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

import java.time.LocalDate;
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

		try {
			clickEntity.setCampaignId(request.getCampaignId());
			clickEntity.setAffliateId(request.getAffliateId());
			clickEntity.setZoneId(request.getZoneId());
			clickEntity.setUserId(request.getUserId());
			clickEntity.setDateNum(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

			if (null == clickRepository.save(clickEntity)) {
				response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
				return response;
			} else {
				ClickDto clickDto = new ClickDto();
				clickDto.setClickId(String.valueOf(clickEntity.getClickNum()));
				clickEntity.setCode("200");
				clickEntity.setMessage("성공");
				response.setSuccess();
				response.setData(clickDto);
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberSignIn  {}", e);
		} finally {
			if (null != clickEntity) clickRepository.save(clickEntity);
		}

		return response;
	}
}
