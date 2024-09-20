package com.cps.cpsApi.service;

import com.cps.common.utils.AES256Utils;
import com.cps.cpsApi.dto.CpsUserDto;
import com.cps.cpsApi.entity.CpsAgencyEntity;
import com.cps.cpsApi.entity.CpsUserEntity;
import com.cps.cpsApi.packet.CpsUserPacket;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final AES256Utils aes256;

	private final EntityManager em;

	public CpsUserDto commonMemberDto(CpsUserEntity cpsUserEntity){
		CpsUserDto cpsUserDto = new CpsUserDto();
		cpsUserDto.setMemberId(cpsUserEntity.getMemberId());
		cpsUserDto.setCompanyName(cpsUserEntity.getCompanyName());
		cpsUserDto.setStatus(cpsUserEntity.getStatus());
		cpsUserDto.setType(cpsUserEntity.getType());
		return cpsUserDto;
	}

	public CpsUserEntity cpsUser(CpsUserPacket.UserInfo.UserRequest request){
		CpsUserEntity cpsUserEntity = new CpsUserEntity();
		cpsUserEntity.setMemberId(request.getMemberId());
		if (null != request.getMemberPw()) {
			cpsUserEntity.setMemberPw(aes256.encrypt(request.getMemberPw()));
		}
		cpsUserEntity.setCompanyName(request.getCompanyName());
		cpsUserEntity.setType(request.getType());
		cpsUserEntity.setBusinessType(request.getBusinessType());
		cpsUserEntity.setAgencyId(request.getAgencyId());
		if (null == cpsUserEntity.getAgencyId() || cpsUserEntity.getAgencyId().equals("")) {
			cpsUserEntity.setAgencyId("ENLIPLE");
		}
		cpsUserEntity.setBank(request.getBank());
		cpsUserEntity.setAccountName(request.getAccountName());
		cpsUserEntity.setStatus(request.getStatus());
		cpsUserEntity.setCeoName(request.getCeoName());
		cpsUserEntity.setBusinessNumber(request.getBusinessNumber());
		cpsUserEntity.setCompanyAddress(request.getCompanyAddress());
		cpsUserEntity.setBusinessCategory(request.getBusinessCategory());
		cpsUserEntity.setBuisinessSector(request.getBusinessSector());
		cpsUserEntity.setManagerName(request.getManagerName());
		cpsUserEntity.setManagerEmail(request.getManagerEmail());
		cpsUserEntity.setManagerPhone(request.getManagerPhone());
		cpsUserEntity.setCompanyPhone(request.getCompanyPhone());
		cpsUserEntity.setLicense(request.getLicense());
		cpsUserEntity.setBirthYear(request.getBirthYear());
		cpsUserEntity.setSex(request.getSex());
		//aES256Utils.decrypt(cpsMemberEntity.getMemberPw());
		return cpsUserEntity;
	}

	public CpsAgencyEntity cpsAgency(CpsUserPacket.UserInfo.UserCampaignRequest request){
		CpsAgencyEntity cpsAgencyEntity = new CpsAgencyEntity();
		cpsAgencyEntity.setAgencyId(request.getAgencyId());
		cpsAgencyEntity.setMemberId(request.getMemberId());
		cpsAgencyEntity.setRewardYn(request.getRewardYn());
		cpsAgencyEntity.setMobileYn(request.getMobileYn());
		cpsAgencyEntity.setReturnDay(request.getReturnDay());
		cpsAgencyEntity.setUrl(request.getUrl());
		cpsAgencyEntity.setLogo(request.getLogo());
		cpsAgencyEntity.setClickUrl(request.getClickUrl());
		cpsAgencyEntity.setWhenTrans(request.getWhenTrans());
		cpsAgencyEntity.setTransReposition(request.getTransReposition());
		cpsAgencyEntity.setDenyAd(request.getDenyAd());
		cpsAgencyEntity.setDenyProduct(request.getDenyProduct());
		cpsAgencyEntity.setNotice(request.getNotice());
		cpsAgencyEntity.setCommissionPaymentStandard(request.getCommissionPaymentStandard());
		return cpsAgencyEntity;
	}

	public List<CpsUserPacket.UserInfo.UserCampaignRequest> linkPriceAgencyMemberList(List<CpsUserPacket.UserInfo.LinkPriceAgencyResponse> linkPriceMerchantList){
		List<CpsUserPacket.UserInfo.UserCampaignRequest> memberRequestList = new ArrayList<>();

		linkPriceMerchantList.forEach(linkPrice -> {
			CpsUserPacket.UserInfo.UserCampaignRequest userRequest = new CpsUserPacket.UserInfo.UserCampaignRequest();
			userRequest.setAgencyId("linkprice");
			userRequest.setMemberId(linkPrice.getMerchantId());
			userRequest.setCompanyName(linkPrice.getMerchantName());
			userRequest.setRewardYn(linkPrice.getRewardYn());
			userRequest.setMobileYn(linkPrice.getMobileYn());
			userRequest.setReturnDay(linkPrice.getReturnDay());
			userRequest.setUrl(linkPrice.getMerchantUrl());
			userRequest.setLogo(linkPrice.getMerchantLogo());
			userRequest.setClickUrl(linkPrice.getClickUrl());
			userRequest.setWhenTrans(linkPrice.getWhenTrans());
			userRequest.setCommissionPaymentStandard(linkPrice.getCommissionPaymentStandard());
			userRequest.setDenyAd(linkPrice.getDenyAd());
			userRequest.setDenyProduct(linkPrice.getDenyProduct());
			userRequest.setNotice(linkPrice.getNotice());
			memberRequestList.add(userRequest);
		});

		return memberRequestList;
	}
}
