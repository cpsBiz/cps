package com.cps.agencyService.service;

import com.cps.agencyService.packet.CpsLinkPriceClickPacket;
import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.agencyService.dto.ClickDto;
import com.cps.agencyService.entity.CpsClickEntity;
import com.cps.agencyService.packet.CpsClickPacket;
import com.cps.agencyService.repository.CpsClickRepository;
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
public class CpsClickLinkPriceService {

	private final CpsClickRepository cpsClickRepository;

	@Value("${linkPrice.click.domain.url}") String linkPriceClickDomain;

	@Value("${linkPrice.click.reco.url}") String linkPriceClickEndPoint;

	/**
	 * 클릭정보 저장
	 * @date 2024-09-04
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
			cpsClickEntity.setMemberId(request.getMemberId());
			cpsClickEntity.setType(request.getType());
			cpsClickEntity.setSite(request.getSite());
			cpsClickEntity.setOs(request.getOs());
			cpsClickEntity.setUserId(request.getUserId());
			cpsClickEntity.setRewardYn("N");
			cpsClickEntity.setIpAddress(ipAddress);

			if (null == cpsClickRepository.save(cpsClickEntity)) {
				response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
				return response;
			} else {
				ClickDto clickDto = new ClickDto();
				clickDto.setClickNum(cpsClickEntity.getClickNum());

				String domain = "https://click.linkprice.com/click.php";
				CpsLinkPriceClickPacket.CpsLinkPriceClickInfo.CpsLinkPriceClickRequest clickRequest = new CpsLinkPriceClickPacket.CpsLinkPriceClickInfo.CpsLinkPriceClickRequest();
				clickRequest.setU_id(String.valueOf(cpsClickEntity.getClickNum()));
				clickRequest.setM(cpsClickEntity.getMemberId().replace("link_",""));
				clickRequest.setA(cpsClickEntity.getAffliateId());//확인
				clickRequest.setL(cpsClickEntity.getAffliateId());//확인

				response.setSuccess();
				response.setData(clickDto);
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberSignIn request : {}, exception : {}", request, e);
		}

		return response;
	}
}
