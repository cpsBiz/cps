package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsCampaignCategoryDto;
import com.cps.cpsApi.entity.CpsCampaignCategoryEntity;
import com.cps.cpsApi.packet.CpsCampaignCategoryPacket;
import com.cps.cpsApi.repository.CpsCampaignCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsCampaignCategoryService {
	private final CpsCampaignCategoryRepository cpsCampaignCategoryRepository;

	private final SearchService searchService;

	/**
	 * 캠페인 카테고리 등록, 수정
	 * @date 2024-09-10
	 */
	public GenericBaseResponse<CpsCampaignCategoryDto> campaignCategory(List<CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategoryRequest> request) throws Exception {
		CpsCampaignCategoryPacket.CampaignCategoryInfo.Response response = new CpsCampaignCategoryPacket.CampaignCategoryInfo.Response();
		List<CpsCampaignCategoryEntity> cpsCampaignCategoryEntityList = cpsCampaignCategory(request);
		List<CpsCampaignCategoryDto> CpsCampaignCategoryDto = commonCampaignCategoryDto(cpsCampaignCategoryEntityList);

		List<String> categories = cpsCampaignCategoryEntityList.stream().map(CpsCampaignCategoryEntity::getCategory).collect(Collectors.toList());

		try {
			//캠페인 카테고리 미인증 리셋
			cpsCampaignCategoryRepository.updateDenyYn(request.get(0).getCampaignNum(), categories);

			//캠페인 카테고리 등록, 수정
			cpsCampaignCategoryRepository.saveAll(cpsCampaignCategoryEntityList);
			
			response.setSuccess();
			response.setDatas(CpsCampaignCategoryDto);
		} catch (Exception e) {
			response.setApiMessage(Constants.CAMPAIGN_CATEGORY_EXCEPTION.getCode(), Constants.CAMPAIGN_CATEGORY_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " campaignCategory {}", e);
		}

		return response;
	}

	/**
	 * 캠페인 카테고리 상세 조회
	 * @date 2024-09-10
	 */
	public GenericBaseResponse<CpsCampaignCategoryEntity> campaignCategorySearch(CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategorySearchRequest request) throws Exception {
		CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategorySearchResponse response = new CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategorySearchResponse();
		List<CpsCampaignCategoryEntity> cpsCampaignCategoryEntityList = new ArrayList<>();

		try {
			cpsCampaignCategoryEntityList = cpsCampaignCategoryRepository.findByCampaignNum(request.getCampaignNum());

			if (cpsCampaignCategoryEntityList.size() > 0) {
				response.setSuccess();
			} else {
				response.setApiMessage(Constants.CAMPAIGN_CATEGORY_BLANK.getCode(), Constants.CAMPAIGN_CATEGORY_BLANK.getValue());
			}
			response.setDatas(cpsCampaignCategoryEntityList);
		} catch (Exception e) {
			response.setApiMessage(Constants.CAMPAIGN_CATEGORY_SEARCH_EXCEPTION.getCode(), Constants.CAMPAIGN_CATEGORY_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " campaignCategorySearch api  {}", e);
		}

		return response;
	}

	public List<CpsCampaignCategoryEntity> cpsCampaignCategory(List<CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategoryRequest> request) throws Exception {

		List<CpsCampaignCategoryEntity> cpsCampaignCategoryEntityList = new ArrayList<>();

		request.forEach(campaignCategory->{
			CpsCampaignCategoryEntity cpsCampaignCategoryEntity = new CpsCampaignCategoryEntity();
			cpsCampaignCategoryEntity.setCategoryNum(campaignCategory.getCategoryNum());
			cpsCampaignCategoryEntity.setCampaignNum(campaignCategory.getCampaignNum());
			cpsCampaignCategoryEntity.setCategory(campaignCategory.getCategory());
			cpsCampaignCategoryEntity.setRs(campaignCategory.getRs());
			cpsCampaignCategoryEntity.setDenyYn(campaignCategory.getDenyYn());
			cpsCampaignCategoryEntityList.add(cpsCampaignCategoryEntity);
		});

		return cpsCampaignCategoryEntityList;
	}


	public List<CpsCampaignCategoryDto> commonCampaignCategoryDto(List<CpsCampaignCategoryEntity> request){

		List<CpsCampaignCategoryDto> cpsCampaignDtoList = new ArrayList<>();

		request.forEach(campaignCategory->{
			CpsCampaignCategoryDto cpsCampaignCategoryDto = new CpsCampaignCategoryDto();
			cpsCampaignCategoryDto.setCampaignNum(campaignCategory.getCampaignNum());
			cpsCampaignCategoryDto.setCategory(campaignCategory.getCategory());
			cpsCampaignCategoryDto.setRs(campaignCategory.getRs());
			cpsCampaignCategoryDto.setDenyYn(campaignCategory.getDenyYn());
			cpsCampaignDtoList.add(cpsCampaignCategoryDto);
		});

		return cpsCampaignDtoList;
	}
}
