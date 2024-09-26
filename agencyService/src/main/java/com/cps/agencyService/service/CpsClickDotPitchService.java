package com.cps.agencyService.service;

import com.cps.agencyService.dto.ClickDto;
import com.cps.agencyService.entity.CpsClickEntity;
import com.cps.agencyService.packet.CpsClickPacket;
import com.cps.agencyService.packet.CpsDotPitchClickPacket;
import com.cps.agencyService.repository.CpsClickRepository;
import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsClickDotPitchService {

	private final CpsClickRepository cpsClickRepository;

	@Value("${dotpitch.click.domain.url}") String dotpitchClickDomain;

	/**
	 * 클릭정보 저장
	 * @date 2024-09-23
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
			cpsClickEntity.setClickUrl(request.getClickUrl());
			cpsClickEntity.setOs(request.getOs());
			cpsClickEntity.setUserId(request.getUserId());
			cpsClickEntity.setAdId(request.getAdId());
			cpsClickEntity.setRewardYn("N");
			cpsClickEntity.setIpAddress(ipAddress);

			if (!"".equals(pfCode(request.getClickUrl()))) {
				if (null == cpsClickRepository.save(cpsClickEntity)) {
					response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
					return response;
				} else {
						CpsDotPitchClickPacket.DotPitchClickInfo.DotPitchClickRequest clickRequest = new CpsDotPitchClickPacket.DotPitchClickInfo.DotPitchClickRequest();
						clickRequest.setPf_code(pfCode(request.getClickUrl()));
						clickRequest.setKeyid(String.valueOf(cpsClickEntity.getClickNum()));
						//clickRequest.setTurl(site("https://www.11st.co.kr/products/2744617875?trTypeCd=03&trCtgrNo=2151825", request.getMemberId()));
						String outputUrl = String.format("?pf_code=%s&keyid=%s", clickRequest.getPf_code(), clickRequest.getKeyid());

						ClickDto clickDto = new ClickDto();
						clickDto.setClickNum(cpsClickEntity.getClickNum());
						clickDto.setClickUrl(dotpitchClickDomain + outputUrl);

						response.setSuccess();
						response.setData(clickDto);
				}
			} else {
				response.setApiMessage(Constants.CLICK_PFCODE_EXCEPTION.getCode(), Constants.CLICK_PFCODE_EXCEPTION.getValue());
				return response;
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.CLICK_EXCEPTION.getCode(), Constants.CLICK_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " campaignClick request : {}, exception : {}", request, e);
		}

		return response;
	}

	public String site(String site, String memberId) throws Exception {
		if (memberId.equals("dot_ssg") || memberId.equals("dot_emart") || memberId.equals("공영홈쇼핑")) {
			site = URLEncoder.encode(site, "EUC-KR");
		} else if (memberId.equals("dot_gmarket") || memberId.equals("wemake")) {
			site = URLEncoder.encode(site, StandardCharsets.UTF_8.toString());
		}
		return site;
	}

	public String pfCode(String site) throws Exception {
		try {
			URI uri = new URI(site);
			String query = uri.getQuery();
			return Arrays.stream(query.split("&"))
					.filter(param -> param.startsWith("pf_code="))
					.map(param -> param.split("=")[1])
					.findFirst()
					.orElse("");
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " pfCode : {}, exception : {}", site, e);
			return "";
		}
	}
}
