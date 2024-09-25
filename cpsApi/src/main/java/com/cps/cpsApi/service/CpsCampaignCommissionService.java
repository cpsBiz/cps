package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsCampaignCommissionDto;
import com.cps.cpsApi.entity.CpsCampaignCommissionEntity;
import com.cps.cpsApi.packet.CpsCampaignCommissionPacket;
import com.cps.cpsApi.repository.CpsCampaignCommissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsCampaignCommissionService {

	private final CpsCampaignCommissionRepository cpsAffliateCampaignRepository;

	/**
	 * 매체 캠페인 승인
	 * @date 2024-09-12
	 */
	public GenericBaseResponse<CpsCampaignCommissionDto> campaignCommission(CpsCampaignCommissionPacket.CpsAffiliateCampaignInfo.CpsAffiliateCampaignRequest request) throws Exception {
		CpsCampaignCommissionPacket.CpsAffiliateCampaignInfo.Response response = new CpsCampaignCommissionPacket.CpsAffiliateCampaignInfo.Response();
		CpsCampaignCommissionEntity cpsCampaignCommissionEntity = new CpsCampaignCommissionEntity();

		try {
			cpsCampaignCommissionEntity.setCampaignNum(request.getCampaignNum());
			cpsCampaignCommissionEntity.setMemberId(request.getMemberId());
			cpsCampaignCommissionEntity.setStatus(request.getStatus());
			cpsCampaignCommissionEntity.setMemberCommissionShare(request.getMemberCommissionShare());
			cpsCampaignCommissionEntity.setUserCommissionShare(request.getUserCommissionShare());

			cpsAffliateCampaignRepository.save(cpsCampaignCommissionEntity);
			response.setSuccess();
			response.setData(commonCpsAffiliateCampaignDto(cpsCampaignCommissionEntity));
		}catch (Exception e){
			response.setApiMessage(Constants.AFFILIATE_CAMPAIGN_EXCEPTION.getCode(), Constants.AFFILIATE_CAMPAIGN_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " affiliateCampaign api request : {}, exception : {}", request, e);
		}
		return response;
	}

	public CpsCampaignCommissionDto commonCpsAffiliateCampaignDto(CpsCampaignCommissionEntity cpsCampaignCommissionEntity){
		CpsCampaignCommissionDto cpsCampaignCommissionDto = new CpsCampaignCommissionDto();
		cpsCampaignCommissionDto.setMemberId(cpsCampaignCommissionEntity.getMemberId());
		cpsCampaignCommissionDto.setMemberCommissionShare(cpsCampaignCommissionEntity.getMemberCommissionShare());
		cpsCampaignCommissionDto.setUserCommissionShare(cpsCampaignCommissionEntity.getUserCommissionShare());
		cpsCampaignCommissionDto.setStatus(cpsCampaignCommissionEntity.getStatus());
		cpsCampaignCommissionDto.setCampaignNum(cpsCampaignCommissionEntity.getCampaignNum());
		return cpsCampaignCommissionDto;
	}
}
