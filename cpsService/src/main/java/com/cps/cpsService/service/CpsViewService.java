package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.cpsService.dto.CpsViewDto;
import com.cps.cpsService.entity.CpsViewEntity;
import com.cps.cpsService.packet.CpsViewPacket;
import com.cps.cpsService.repository.CpsViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsViewService {

	private final CpsViewRepository cpsViewRepository;

	/**
	 * 노출 등록
	 * @date 2024-09-11
	 */
	public CpsViewPacket.ViewInfo.ViewResponse campaignView(CpsViewPacket.ViewInfo.ViewRequest request) throws Exception {
		CpsViewPacket.ViewInfo.ViewResponse response = new CpsViewPacket.ViewInfo.ViewResponse();
		List<CpsViewDto> CpsViewDtoList = null;
		try {
			if (request.getCategory().equals("favorites")) {
				CpsViewDtoList = cpsViewDtoList(cpsViewRepository.findActiveFavoritesCampaigns(request.getAffliateId(), request.getZoneId(), request.getSite(), request.getUserId(), request.getAdId(), request.getOs()));
			} else {
				CpsViewDtoList = cpsViewDtoList(cpsViewRepository.findActiveCampaigns(request.getAffliateId(), request.getZoneId(), request.getSite(), request.getUserId(), request.getAdId(), request.getOs(), request.getCategory()));
			}
			List<CpsViewEntity> cpsViewEntityList = cpsViewEntityList(CpsViewDtoList);

			if (cpsViewEntityList.size() > 0) {
				cpsViewRepository.saveAll(cpsViewEntityList);
				response.setSuccess();
				response.setDatas(CpsViewDtoList);
			} else {
				response.setApiMessage(Constants.VIEW_BLANK.getCode(), Constants.VIEW_BLANK.getValue());
				return response;
			}
		}catch (Exception e){
			response.setApiMessage(Constants.VIEW_EXCEPTION.getCode(), Constants.VIEW_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " campaignView api request : {}, exception :  {}", request, e);
		}
		return response;
	}

	public List<CpsViewDto> cpsViewDtoList(List<Object[]> object){
		String ipAddress = "0.0.0.0";
		try{
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		}catch (Exception e){
			log.error(Constant.EXCEPTION_MESSAGE + " cpsViewEntityList  {}", e);
		}

		return object.stream().map(result -> {
			CpsViewDto cpsViewDto = new CpsViewDto();
			cpsViewDto.setRegDay(Integer.parseInt((String) result[0]));
			cpsViewDto.setRegHour((String) result[1]);
			cpsViewDto.setCampaignNum(((Number) result[2]).intValue());
			cpsViewDto.setCampaignName((String) result[3]);
			cpsViewDto.setAgencyId((String) result[4]);
			cpsViewDto.setMemberId((String) result[5]);
			cpsViewDto.setClickUrl((String) result[6]);
			cpsViewDto.setIcon((String) result[7]);
			cpsViewDto.setLogo((String) result[8]);
			cpsViewDto.setType((String) result[9]);
			cpsViewDto.setAffliateId((String) result[10]);
			cpsViewDto.setZoneId((String) result[11]);
			cpsViewDto.setSite((String) result[12]);
			cpsViewDto.setUserId((String) result[13]);
			cpsViewDto.setAdId((String) result[14]);
			cpsViewDto.setOs((String) result[15]);
			return cpsViewDto;
		}).collect(Collectors.toList());
	}

	public List<CpsViewEntity> cpsViewEntityList(List<CpsViewDto> cpsViewDtoList){
		String ipAddress = "0.0.0.0";
		try{
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		}catch (Exception e){
			log.error(Constant.EXCEPTION_MESSAGE + " cpsViewEntityList  {}", e);
		}

		String finalIpAddress = ipAddress;

		return cpsViewDtoList.stream().map(result -> {
			CpsViewEntity cpsViewEntity = new CpsViewEntity();
			cpsViewEntity.setRegDay(result.getRegDay());
			cpsViewEntity.setRegHour(result.getRegHour());
			cpsViewEntity.setCampaignNum(result.getCampaignNum());
			cpsViewEntity.setCampaignName(result.getCampaignName());
			cpsViewEntity.setAgencyId(result.getAgencyId());
			cpsViewEntity.setMemberId(result.getMemberId());
			cpsViewEntity.setClickUrl(result.getClickUrl());
			cpsViewEntity.setType(result.getType());
			cpsViewEntity.setAffliateId(result.getAffliateId());
			cpsViewEntity.setZoneId(result.getZoneId());
			cpsViewEntity.setSite(result.getSite());
			cpsViewEntity.setUserId(result.getUserId());
			cpsViewEntity.setAdId(result.getAdId());
			cpsViewEntity.setOs(result.getOs());
			cpsViewEntity.setIpAddress(finalIpAddress);
			return cpsViewEntity;
		}).collect(Collectors.toList());
	}
}