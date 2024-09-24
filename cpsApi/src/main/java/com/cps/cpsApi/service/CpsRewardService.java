package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.DotPitchRewardDto;
import com.cps.cpsApi.entity.CpsClickEntity;
import com.cps.cpsApi.entity.CpsRewardEntity;
import com.cps.cpsApi.packet.CpsRewardPacket;
import com.cps.cpsApi.repository.CpsClickRepository;
import com.cps.cpsApi.repository.CpsRewardRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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

		request.setLog_id(dotPitchId);
		request.setLog_pw(dotPtichPw);

		try {
			CpsRewardPacket.RewardInfo.DotPitchListResponse dotPitchResponseList = httpService.SendDotPitchReward(domain, request);

			if (dotPitchResponseList.getListData() != null) {
				dotPitchResponseList.getListData().forEach(dot ->{
					CpsClickEntity cpsClickEntity = cpsClickRepository.findByClickNum(Integer.parseInt(dot.getA_info()));
					if (null != cpsClickEntity) {
						CpsRewardEntity cpsRewardEntity = new CpsRewardEntity();
						cpsRewardEntity.setClickNum(Integer.parseInt(dot.getA_info()));
						cpsRewardEntity.setOrderNo(dot.getOrderid());
						cpsRewardEntity.setProductCode(dot.getOrderid());
						if (dot.getM_name().equals("11번가")) {
							cpsRewardEntity.setRegDay(Integer.parseInt(dot.getOrderid().substring(0, 8)));
							cpsRewardEntity.setRegYm(Integer.parseInt(dot.getOrderid().substring(0, 6)));
							cpsRewardEntity.setRegHour(cpsClickEntity.getRegHour());
						} else if (dot.getM_name().equals("옥션")) {
							cpsRewardEntity.setRegDay(cpsClickEntity.getRegDay());
							cpsRewardEntity.setRegYm(cpsClickEntity.getRegYm());
							cpsRewardEntity.setRegHour(cpsClickEntity.getRegHour());
						}
						cpsRewardEntity.setCampaignNum(cpsClickEntity.getCampaignNum());
						cpsRewardEntity.setMemberId("dot_" + cpsClickEntity.getMemberId());
						cpsRewardEntity.setAgencyId(cpsClickEntity.getAgencyId());
						cpsRewardEntity.setAffliateId(cpsClickEntity.getAffliateId());
						cpsRewardEntity.setZoneId(cpsClickEntity.getZoneId());
						cpsRewardEntity.setSite(cpsClickEntity.getSite());
						cpsRewardEntity.setOs(cpsClickEntity.getOs());
						cpsRewardEntity.setType(cpsClickEntity.getType());
						cpsRewardEntity.setUserId(cpsClickEntity.getUserId());
						cpsRewardEntity.setAdId(cpsClickEntity.getAdId());
						cpsRewardEntity.setStatus(100);
						if (null != dot.getOrder_flag()) {
							if ("확정".equals(dot.getOrder_flag())) {
								cpsRewardEntity.setStatus(210);
							} else {
								cpsRewardEntity.setStatus(200);
							}
						}
						cpsRewardEntity.setMemberName(dot.getM_name());
						cpsRewardEntity.setProductName(dot.getP_name());
						cpsRewardEntity.setProductCnt(Integer.parseInt(dot.getQuantity()));
						cpsRewardEntity.setProductPrice(Integer.parseInt(dot.getPrice()));
						cpsRewardEntity.setCommission(dot.getComm());
						cpsRewardEntity.setBaseCommission("0");
						cpsRewardEntity.setIncentiveCommission("0");
						cpsRewardEntity.setIpAddress(ipAddress);
						cpsRewardEntityList.add(cpsRewardEntity);
					}
				});

				//도트피치 리워드 저장
				if(cpsRewardEntityList.size()>0) {
					cpsRewardRepository.saveAll(cpsRewardEntityList);
					response.setSuccess();
				}else {
					response.setApiMessage(Constants.DOTPITCH_NOT_SEARCH.getCode(), Constants.DOTPITCH_NOT_SEARCH.getValue());
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
