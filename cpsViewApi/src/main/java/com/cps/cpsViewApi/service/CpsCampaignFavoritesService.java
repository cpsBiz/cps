package com.cps.cpsViewApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsViewApi.dto.CpsCampaignFavoritesDto;
import com.cps.cpsViewApi.dto.CpsViewDto;
import com.cps.cpsViewApi.entity.CpsCampaignFavoritesEntity;
import com.cps.cpsViewApi.entity.CpsViewEntity;
import com.cps.cpsViewApi.packet.CpsCampaignFavoritesPacket;
import com.cps.cpsViewApi.packet.CpsViewPacket;
import com.cps.cpsViewApi.repository.CpsCampaignFavoritesRepository;
import com.cps.cpsViewApi.repository.CpsViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

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
