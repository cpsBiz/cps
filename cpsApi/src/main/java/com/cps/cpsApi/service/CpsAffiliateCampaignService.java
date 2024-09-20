package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsAffiliateCampaignDto;
import com.cps.cpsApi.entity.CpsAffilateCampaignEntity;
import com.cps.cpsApi.packet.CpsAffiliateCampaignPacket;
import com.cps.cpsApi.repository.CpsAffliateCampaignRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsAffiliateCampaignService {

	private final CpsAffliateCampaignRepository cpsAffliateCampaignRepository;

	/**
	 * 매체 캠페인 승인
	 * @date 2024-09-12
	 */
	public GenericBaseResponse<CpsAffiliateCampaignDto> affiliateCampaign(CpsAffiliateCampaignPacket.CpsAffiliateCampaignInfo.CpsAffiliateCampaignRequest request) throws Exception {
		CpsAffiliateCampaignPacket.CpsAffiliateCampaignInfo.Response response = new CpsAffiliateCampaignPacket.CpsAffiliateCampaignInfo.Response();
		CpsAffilateCampaignEntity cpsAffilateCampaignEntity = new CpsAffilateCampaignEntity();

		try {
			cpsAffilateCampaignEntity.setAffiliateId(request.getAffiliateId());
			cpsAffilateCampaignEntity.setMemberId(request.getMemberId());
			cpsAffilateCampaignEntity.setCampaignNum(request.getCampaignNum());
			cpsAffilateCampaignEntity.setStatus(request.getStatus());

			cpsAffliateCampaignRepository.save(cpsAffilateCampaignEntity);
			response.setSuccess();
			response.setData(commonCpsAffiliateCampaignDto(cpsAffilateCampaignEntity));
		}catch (Exception e){
			response.setApiMessage(Constants.AFFILIATE_CAMPAIGN_EXCEPTION.getCode(), Constants.AFFILIATE_CAMPAIGN_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " affiliateCampaign api  {}", e);
		}
		return response;
	}

	public CpsAffiliateCampaignDto commonCpsAffiliateCampaignDto(CpsAffilateCampaignEntity cpsAffilateCampaignEntity){
		CpsAffiliateCampaignDto cpsAffiliateCampaignDto = new CpsAffiliateCampaignDto();
		cpsAffiliateCampaignDto.setAffiliateId(cpsAffilateCampaignEntity.getAffiliateId());
		cpsAffiliateCampaignDto.setMemberId(cpsAffilateCampaignEntity.getMemberId());
		cpsAffiliateCampaignDto.setStatus(cpsAffilateCampaignEntity.getStatus());
		cpsAffiliateCampaignDto.setCampaignNum(cpsAffilateCampaignEntity.getCampaignNum());
		return cpsAffiliateCampaignDto;
	}
}
