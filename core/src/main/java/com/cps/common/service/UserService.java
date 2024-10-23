package com.cps.common.service;

import com.cps.common.utils.AES256Utils;
import com.cps.common.dto.CpsMemberDto;
import com.cps.common.entity.CpsAgencyEntity;
import com.cps.common.entity.CpsMemberEntity;
import com.cps.common.packet.CpsMemberPacket;
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

	public CpsMemberDto commonMemberDto(CpsMemberEntity cpsMemberEntity){
		CpsMemberDto cpsMemberDto = new CpsMemberDto();
		cpsMemberDto.setMemberId(cpsMemberEntity.getMemberId());
		cpsMemberDto.setMemberName(cpsMemberEntity.getManagerName());
		cpsMemberDto.setStatus(cpsMemberEntity.getStatus());
		cpsMemberDto.setType(cpsMemberEntity.getType());
		return cpsMemberDto;
	}

	public CpsMemberEntity cpsUser(CpsMemberPacket.MemberInfo.MemberRequest request){
		CpsMemberEntity cpsMemberEntity = new CpsMemberEntity();
		cpsMemberEntity.setMemberId(request.getMemberId());
		if (null != request.getMemberPw()) {
			cpsMemberEntity.setMemberPw(aes256.encrypt(request.getMemberPw()));
		}
		cpsMemberEntity.setMemberName(request.getMemberName());
		cpsMemberEntity.setType(request.getType());
		cpsMemberEntity.setBusinessType(request.getBusinessType());
		cpsMemberEntity.setAgencyId(request.getAgencyId());
		if (null == cpsMemberEntity.getAgencyId() || cpsMemberEntity.getAgencyId().equals("")) {
			cpsMemberEntity.setAgencyId("ENLIPLE");
		}
		cpsMemberEntity.setBank(request.getBank());
		cpsMemberEntity.setAccountName(request.getAccountName());
		cpsMemberEntity.setStatus(request.getStatus());
		cpsMemberEntity.setCeoName(request.getCeoName());
		cpsMemberEntity.setBusinessNumber(request.getBusinessNumber());
		cpsMemberEntity.setCompanyAddress(request.getCompanyAddress());
		cpsMemberEntity.setBusinessCategory(request.getBusinessCategory());
		cpsMemberEntity.setBuisinessSector(request.getBusinessSector());
		cpsMemberEntity.setManagerName(request.getManagerName());
		cpsMemberEntity.setManagerEmail(request.getManagerEmail());
		cpsMemberEntity.setManagerPhone(request.getManagerPhone());
		cpsMemberEntity.setCompanyPhone(request.getCompanyPhone());
		cpsMemberEntity.setLicense(request.getLicense());
		cpsMemberEntity.setBirthYear(request.getBirthYear());
		cpsMemberEntity.setSex(request.getSex());
		//aES256Utils.decrypt(cpsMemberEntity.getMemberPw());
		return cpsMemberEntity;
	}

	public CpsAgencyEntity cpsAgency(CpsMemberPacket.MemberInfo.MemberCampaignRequest request){
		CpsAgencyEntity cpsAgencyEntity = new CpsAgencyEntity();
		cpsAgencyEntity.setAgencyId(request.getAgencyId());
		cpsAgencyEntity.setMerchantId("link_"+request.getMemberId());
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

	public List<CpsMemberPacket.MemberInfo.MemberCampaignRequest> linkPriceAgencyMemberList(List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> linkPriceMerchantList){
		List<CpsMemberPacket.MemberInfo.MemberCampaignRequest> memberRequestList = new ArrayList<>();

		linkPriceMerchantList.forEach(linkPrice -> {
			CpsMemberPacket.MemberInfo.MemberCampaignRequest userRequest = new CpsMemberPacket.MemberInfo.MemberCampaignRequest();
			userRequest.setAgencyId("linkprice");
			userRequest.setMemberId(linkPrice.getMerchantId());
			userRequest.setMemberName(linkPrice.getMerchantName());
			userRequest.setMaxCommissionPc(linkPrice.getMaxCommissionPc());
			userRequest.setMaxCommissionMobile(linkPrice.getMaxCommissionMobile());
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
