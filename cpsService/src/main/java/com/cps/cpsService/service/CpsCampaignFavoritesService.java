package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsService.repository.CpsCampaignFavoritesRepository;
import com.cps.cpsService.dto.CpsCampaignFavoritesDto;
import com.cps.cpsService.entity.CpsCampaignFavoritesEntity;
import com.cps.cpsService.packet.CpsCampaignFavoritesPacket;
import com.cps.cpsService.repository.CpsCampaignFavoritesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsCampaignFavoritesService {

	private final CpsCampaignFavoritesRepository cpsCampaignFavoritesRepository;

	/**
	 * 캠페인 즐겨찾기 등록, 삭제
	 * @date 2024-10-01
	 */
	public GenericBaseResponse<CpsCampaignFavoritesDto> favorites(CpsCampaignFavoritesPacket.CampaignFavoritesInfo.CampaignFavoritesRequest request) throws Exception {
		CpsCampaignFavoritesPacket.CampaignFavoritesInfo.CampaignFavoritesResponse response = new CpsCampaignFavoritesPacket.CampaignFavoritesInfo.CampaignFavoritesResponse();
		CpsCampaignFavoritesEntity cpsCampaignFavoritesEntity = new CpsCampaignFavoritesEntity();

		cpsCampaignFavoritesEntity.setCampaignNum(request.getCampaignNum());
		cpsCampaignFavoritesEntity.setUserId(request.getUserId());

		try {
			if (request.getApiType().equals("I")) {
				cpsCampaignFavoritesRepository.save(cpsCampaignFavoritesEntity);
			} else{
				cpsCampaignFavoritesRepository.delete(cpsCampaignFavoritesEntity);
			}

		}catch (Exception e){
			response.setApiMessage(Constants.FAVORITES_EXCEPTION.getCode(), Constants.FAVORITES_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " favorites api request : {}, exception :  {}", request, e);
		}
		return response;
	}
}
