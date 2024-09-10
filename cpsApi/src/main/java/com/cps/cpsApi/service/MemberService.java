package com.cps.cpsApi.service;

import com.cps.common.utils.AES256Utils;
import com.cps.cpsApi.dto.CpsMemberDto;
import com.cps.cpsApi.entity.CpsMemberEntity;
import com.cps.cpsApi.packet.CpsMemberPacket;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final AES256Utils aes256;

	private final EntityManager em;

	public CpsMemberDto commonMemberDto(CpsMemberEntity cpsMemberEntity){
		CpsMemberDto cpsMemberDto = new CpsMemberDto();
		cpsMemberDto.setMemberId(cpsMemberEntity.getMemberId());
		cpsMemberDto.setCompanyName(cpsMemberEntity.getCompanyName());
		cpsMemberDto.setStatus(cpsMemberEntity.getStatus());
		cpsMemberDto.setMemberType(cpsMemberEntity.getType());
		return cpsMemberDto;
	}

	public CpsMemberEntity cpsMember(CpsMemberPacket.MemberInfo.MemberRequest request){
		CpsMemberEntity cpsMemberEntity = new CpsMemberEntity();
		cpsMemberEntity.setManagerId(request.getManagerId());
		cpsMemberEntity.setMemberId(request.getMemberId());
		cpsMemberEntity.setCompanyName(request.getCompanyName());
		cpsMemberEntity.setType(request.getType());
		cpsMemberEntity.setStatus(request.getStatus());
		if (null != request.getMemberPw()) {
			cpsMemberEntity.setMemberPw(aes256.encrypt(request.getMemberPw()));
		}
		//aES256Utils.decrypt(cpsMemberEntity.getMemberPw());
		cpsMemberEntity.setManagerName(request.getManagerName());
		cpsMemberEntity.setOfficePhone(request.getOfficePhone());
		cpsMemberEntity.setPhone(request.getPhone());
		cpsMemberEntity.setMail(request.getMail());
		cpsMemberEntity.setAddress(request.getAddress());
		cpsMemberEntity.setBuisnessNumber(request.getBuisnessNumber());
		cpsMemberEntity.setCategory(request.getCategory());
		cpsMemberEntity.setCategoryName(request.getCategoryName());
		cpsMemberEntity.setRewardYn(request.getRewardYn());
		cpsMemberEntity.setMobileYn(request.getMobileYn());
		cpsMemberEntity.setReturnDay(request.getReturnDay());
		cpsMemberEntity.setUrl(request.getUrl());
		cpsMemberEntity.setLogo(request.getLogo());
		cpsMemberEntity.setClickUrl(request.getClickUrl());
		cpsMemberEntity.setWhenTrans(request.getWhenTrans());
		cpsMemberEntity.setTransReposition(request.getTransReposition());
		cpsMemberEntity.setDenyAd(request.getDenyAd());
		cpsMemberEntity.setDenyProduct(request.getDenyProduct());
		cpsMemberEntity.setNotice(request.getNotice());
		cpsMemberEntity.setCommissionPaymentStandard(request.getCommissionPaymentStandard());
		return cpsMemberEntity;
	}

	public List<CpsMemberPacket.MemberInfo.MemberRequest> linkPriceAgencyMemberList(List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> linkPriceMerchantList){
		List<CpsMemberPacket.MemberInfo.MemberRequest> memberRequestList = new ArrayList<>();

		linkPriceMerchantList.forEach(linkPrice -> {
			CpsMemberPacket.MemberInfo.MemberRequest memberRequest = new CpsMemberPacket.MemberInfo.MemberRequest();
			memberRequest.setMemberId(linkPrice.getMerchantId());
			memberRequest.setManagerId("linkprice");
			memberRequest.setCompanyName(linkPrice.getMerchantName());
			memberRequest.setCategory(linkPrice.getCategoryId());
			memberRequest.setCategoryName(linkPrice.getCategoryName());
			memberRequest.setRewardYn(linkPrice.getRewardYn());
			memberRequest.setMobileYn(linkPrice.getMobileYn());
			memberRequest.setReturnDay(linkPrice.getReturnDay());
			memberRequest.setUrl(linkPrice.getMerchantUrl());
			memberRequest.setLogo(linkPrice.getMerchantLogo());
			memberRequest.setClickUrl(linkPrice.getClickUrl());
			memberRequest.setWhenTrans(linkPrice.getWhenTrans());
			memberRequest.setTransReposition(linkPrice.getTransReposition());
			memberRequest.setCommissionPaymentStandard(linkPrice.getCommissionPaymentStandard());
			memberRequest.setDenyAd(linkPrice.getDenyAd());
			memberRequest.setDenyProduct(linkPrice.getDenyProduct());
			memberRequest.setNotice(linkPrice.getNotice());
			memberRequest.setCommissionPaymentStandard(linkPrice.getCommissionPaymentStandard());
			memberRequestList.add(memberRequest);
		});

		return memberRequestList;
	}
}
