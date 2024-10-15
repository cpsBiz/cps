package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.dto.CpsCampaignCommissionDto;
import com.cps.cpsService.entity.CpsCampaignCommissionEntity;
import com.cps.cpsService.packet.CpsCampaignCommissionPacket;
import com.cps.cpsService.repository.CpsCampaignCommissionRepository;
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
	public GenericBaseResponse<CpsCampaignCommissionDto> campaignCommission(CpsCampaignCommissionPacket.AffiliateCampaignInfo.AffiliateCampaignRequest request) throws Exception {
		CpsCampaignCommissionPacket.AffiliateCampaignInfo.Response response = new CpsCampaignCommissionPacket.AffiliateCampaignInfo.Response();
		CpsCampaignCommissionEntity cpsCampaignCommissionEntity = new CpsCampaignCommissionEntity();

		try {
			cpsCampaignCommissionEntity.setCampaignNum(request.getCampaignNum());
			cpsCampaignCommissionEntity.setAffliateId(request.getAffliateId());
			cpsCampaignCommissionEntity.setStatus(request.getStatus());
			cpsCampaignCommissionEntity.setMemberCommissionShare(request.getMemberCommissionShare());
			cpsCampaignCommissionEntity.setUserCommissionShare(request.getUserCommissionShare());
			cpsCampaignCommissionEntity.setPointRate(request.getPointRate());

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
		cpsCampaignCommissionDto.setAffliateId(cpsCampaignCommissionEntity.getAffliateId());
		cpsCampaignCommissionDto.setMemberCommissionShare(cpsCampaignCommissionEntity.getMemberCommissionShare());
		cpsCampaignCommissionDto.setUserCommissionShare(cpsCampaignCommissionEntity.getUserCommissionShare());
		cpsCampaignCommissionDto.setStatus(cpsCampaignCommissionEntity.getStatus());
		cpsCampaignCommissionDto.setCampaignNum(cpsCampaignCommissionEntity.getCampaignNum());
		return cpsCampaignCommissionDto;
	}
}
