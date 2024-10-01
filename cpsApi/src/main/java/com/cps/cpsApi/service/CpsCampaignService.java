package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.common.model.GenericPageBaseResponse;
import com.cps.cpsApi.dto.CpsCampaignCategoryDto;
import com.cps.cpsApi.dto.CpsCampaignDto;
import com.cps.cpsApi.dto.CpsCampaignSearchDto;
import com.cps.cpsApi.entity.CpsCampaignEntity;
import com.cps.cpsApi.packet.CpsCampaignPacket;
import com.cps.cpsApi.packet.CpsMemberPacket;
import com.cps.cpsApi.repository.CpsCampaignRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsCampaignService {
	private final CpsCampaignRepository cpsCampaignRepository;

	private final SearchService searchService;

	/**
	 * 캠페인 등록, 수정, 삭제
	 * @date 2024-09-10
	 */
	public GenericBaseResponse<CpsCampaignDto> campaign(CpsCampaignPacket.CampaignInfo.CampaignRequest request) throws Exception {
		CpsCampaignPacket.CampaignInfo.Response response = new CpsCampaignPacket.CampaignInfo.Response();
		CpsCampaignEntity cpsCampaignEntity = cpsCampaign(request);
		CpsCampaignDto cpsCampaignDto = commonCmapaignDto(cpsCampaignEntity);

		CpsCampaignEntity campaignCheck = cpsCampaignRepository.findByCampaignNum(request.getCampaignNum());

		if (request.getApiType().equals("I")) {
			try {
				if (null == campaignCheck) {
					//캠페인 테이블 등록
					cpsCampaignRepository.save(cpsCampaignEntity);
					response.setSuccess();
				} else {
					response.setApiMessage(Constants.CAMPAIGN_DUPLICATION.getCode(), Constants.CAMPAIGN_DUPLICATION.getValue());
					return response;
				}
				response.setData(cpsCampaignDto);
			} catch (Exception e) {
				response.setApiMessage(Constants.CAMPAIGN_EXCEPTION.getCode(), Constants.CAMPAIGN_EXCEPTION.getValue());
				log.error(Constant.EXCEPTION_MESSAGE + " campaignInsert  {}", e);
			}
		} else if (null != campaignCheck) {

			if (request.getApiType().equals("D")) {
				try {
					cpsCampaignRepository.deleteByCampaignNum(request.getCampaignNum());
					response.setSuccess();
					response.setData(cpsCampaignDto);
				} catch (Exception e) {
					response.setApiMessage(Constants.CAMPAIGN_DELETE_EXCEPTION.getCode(), Constants.CAMPAIGN_DELETE_EXCEPTION.getValue());
					log.error(Constant.EXCEPTION_MESSAGE + " campaignDelete  {}", e);
				}
			} else if (request.getApiType().equals("U")) {
				try {
					//회원정보 테이블 수정
					cpsCampaignRepository.save(cpsCampaignEntity);

					response.setSuccess();
					response.setData(cpsCampaignDto);
				} catch (Exception e) {
					response.setApiMessage(Constants.CAMPAIGN_UPDATE_EXCEPTION.getCode(), Constants.CAMPAIGN_UPDATE_EXCEPTION.getValue());
					log.error(Constant.EXCEPTION_MESSAGE + " campaignUpdate  {}", e);
				}
			}
		} else {
			response.setApiMessage(Constants.CAMPAIGN_BLANK.getCode(), Constants.CAMPAIGN_BLANK.getValue());
		}

		return response;
	}

	/**
	 * 캠페인 조회
	 * @date 2024-09-10
	 */
	public GenericPageBaseResponse<CpsCampaignSearchDto> campaignList(CpsCampaignPacket.CampaignInfo.CampaignSearchRequest request) throws Exception {
		CpsCampaignPacket.CampaignInfo.CampaignSearchResponse response = new CpsCampaignPacket.CampaignInfo.CampaignSearchResponse();

		try {
			response = searchService.campaignSearch(request);
			if (null == response) {
				response.setApiMessage(Constants.CAMPAIGN_BLANK.getCode(), Constants.CAMPAIGN_BLANK.getValue());
			} else {
				response.setSuccess(response.getTotalPage());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.CAMPAIGN_SEARCH_EXCEPTION.getCode(), Constants.CAMPAIGN_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + "campaignList api request : {}, exception : {}", request, e);

		}
		return response;
	}

	/**
	 * 캠페인 카테고리 일괄 수정
	 * @date 2024-10-01
	 */
	public GenericBaseResponse<CpsCampaignCategoryDto> campaignCategory(CpsCampaignPacket.CampaignInfo.CampaignCategoryListRequest request) throws Exception {
		CpsCampaignPacket.CampaignInfo.CampaignCategoryResponse response = new CpsCampaignPacket.CampaignInfo.CampaignCategoryResponse();
		List<CpsCampaignCategoryDto> cpsCampaignList = new ArrayList<>();

		request.getCampaignCategoryList().forEach(campaign -> {
			cpsCampaignRepository.updateCategoryByCampaignNum(campaign.getCategory(), campaign.getCampaignNum());

			CpsCampaignCategoryDto cpsCampaignCategoryDto = new CpsCampaignCategoryDto();
			cpsCampaignCategoryDto.setCampaignName(campaign.getCampaignName());
			cpsCampaignCategoryDto.setCampaignNum(campaign.getCampaignNum());
			cpsCampaignCategoryDto.setCategory(campaign.getCategory());
			cpsCampaignList.add(cpsCampaignCategoryDto);
		});

		response.setSuccess();
		response.setDatas(cpsCampaignList);

		return response;
	}

	public CpsCampaignEntity cpsCampaign(CpsCampaignPacket.CampaignInfo.CampaignRequest request) throws Exception {
		CpsCampaignEntity cpsCampaignEntity = new CpsCampaignEntity();

		cpsCampaignEntity.setCampaignNum(request.getCampaignNum());
		cpsCampaignEntity.setAgencyId(request.getAgencyId());
		cpsCampaignEntity.setMemberId(request.getMemberId());
		cpsCampaignEntity.setCampaignName(request.getCampaignName());
		cpsCampaignEntity.setCampaignStart(request.getCampaignStart());
		cpsCampaignEntity.setCampaignEnd(request.getCampaignEnd());
		cpsCampaignEntity.setClickUrl(request.getClickUrl());
		cpsCampaignEntity.setCategory(request.getCategory());
		cpsCampaignEntity.setLogo(request.getLogo());
		cpsCampaignEntity.setIcon(request.getIcon());
		cpsCampaignEntity.setCampaignAuto(request.getCampaignAuto());
		cpsCampaignEntity.setRewardYn(request.getRewardYn());
		cpsCampaignEntity.setPcYn(request.getPcYn());
		cpsCampaignEntity.setMobileYn(request.getMobileYn());
		cpsCampaignEntity.setAosYn(request.getAodYn());
		cpsCampaignEntity.setIosYn(request.getIosYn());
		cpsCampaignEntity.setReturnDay(request.getReturnDay());
		cpsCampaignEntity.setCommissionSendYn(request.getCommissionSendYn());
		cpsCampaignEntity.setWhenTrans(request.getWhenTrans());
		cpsCampaignEntity.setTransReposition(request.getTransReposition());
		cpsCampaignEntity.setCommissionPaymentStandard(request.getCommissionPaymentStandard());
		cpsCampaignEntity.setDenyAd(request.getDenyAd());
		cpsCampaignEntity.setDenyProduct(request.getDenyProduct());
		cpsCampaignEntity.setNotice(request.getNotice());
		cpsCampaignEntity.setCampaignStatus(request.getCampaignStatus());
		return cpsCampaignEntity;
	}

	public CpsCampaignEntity cpsMemberCampaign(CpsMemberPacket.MemberInfo.MemberCampaignRequest request) throws Exception {
		CpsCampaignEntity cpsCampaignEntity = new CpsCampaignEntity();
		cpsCampaignEntity.setAgencyId(request.getAgencyId());
		cpsCampaignEntity.setMemberId(request.getMemberId());
		cpsCampaignEntity.setCampaignName(request.getMemberId()+" 캠페인 자동 등록");
		cpsCampaignEntity.setClickUrl(request.getClickUrl());
		cpsCampaignEntity.setLogo(request.getLogo());
		cpsCampaignEntity.setIcon(request.getIcon());
		cpsCampaignEntity.setRewardYn(request.getRewardYn());
		cpsCampaignEntity.setMobileYn(request.getMobileYn());
		cpsCampaignEntity.setReturnDay(request.getReturnDay());
		cpsCampaignEntity.setWhenTrans(request.getWhenTrans());
		cpsCampaignEntity.setTransReposition(request.getTransReposition());
		cpsCampaignEntity.setCommissionPaymentStandard(request.getCommissionPaymentStandard());
		cpsCampaignEntity.setDenyAd(request.getDenyAd());
		cpsCampaignEntity.setDenyProduct(request.getDenyProduct());
		cpsCampaignEntity.setNotice(request.getNotice());
		return cpsCampaignEntity;
	}

	public CpsCampaignDto commonCmapaignDto(CpsCampaignEntity cpsCampaignEntity){
		CpsCampaignDto cpsCampaignDto = new CpsCampaignDto();
		cpsCampaignDto.setCampaignNum(cpsCampaignEntity.getCampaignNum());
		cpsCampaignDto.setCampaignName(cpsCampaignEntity.getCampaignName());
		cpsCampaignDto.setCampaignStart(cpsCampaignEntity.getCampaignStart());
		cpsCampaignDto.setCampaignEnd(cpsCampaignEntity.getCampaignEnd());
		return cpsCampaignDto;
	}
}
