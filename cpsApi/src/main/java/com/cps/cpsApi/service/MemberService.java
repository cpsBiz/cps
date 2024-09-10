package com.cps.cpsApi.service;

import com.cps.common.utils.AES256Utils;
import com.cps.cpsApi.dto.CpsMemberDto;
import com.cps.cpsApi.entity.CpsMemberEntity;
import com.cps.cpsApi.packet.CpsMemberPacket;
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

	private final AES256Utils aES256Utils;

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
			cpsMemberEntity.setMemberPw(aES256Utils.encrypt(request.getMemberPw()));
		}
		//aES256Utils.decrypt(cpsMemberEntity.getMemberPw());
		cpsMemberEntity.setManagerName(request.getManagerName());
		cpsMemberEntity.setOfficePhone(request.getOfficePhone());
		cpsMemberEntity.setPhone(request.getPhone());
		cpsMemberEntity.setMail(request.getMail());
		cpsMemberEntity.setAddress(request.getAddress());
		cpsMemberEntity.setBuisnessNumber(request.getBuisnessNumber());
		cpsMemberEntity.setCategory(request.getCategory());
		cpsMemberEntity.setRewardYn(request.getRewardYn());
		cpsMemberEntity.setMobileYn(request.getMobileYn());
		cpsMemberEntity.setReturnDay(request.getReturnDay());
		cpsMemberEntity.setUrl(request.getUrl());
		cpsMemberEntity.setLogo(request.getLogo());
		return cpsMemberEntity;
	}

	public List<CpsMemberEntity> memberSearch(CpsMemberPacket.MemberInfo.MemberListRequest request){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CpsMemberEntity> cq = cb.createQuery(CpsMemberEntity.class);
		Root<CpsMemberEntity> member = cq.from(CpsMemberEntity.class);

		List<Predicate> predicates = new ArrayList<>();

		if (request.getMemberId() != null && !request.getMemberId().equals("")) {
			predicates.add(cb.like(member.get("memberId"), "%" + request.getMemberId() + "%"));
		}
		if (request.getManagerId() != null && !request.getManagerId().equals("")) {
			predicates.add(cb.like(member.get("managerId"), "%" + request.getManagerId() + "%"));
		}
		if (request.getCompanyName() != null && !request.getCompanyName().equals("")) {
			predicates.add(cb.like(member.get("companyName"), "%" + request.getCompanyName() + "%"));
		}
		if (request.getType() != null && !request.getType().equals("")) {
			predicates.add(cb.equal(member.get("type"), request.getType()));
		}
		if (request.getStatus() != null && !request.getStatus().equals("")) {
			predicates.add(cb.equal(member.get("status"), request.getStatus()));
		}
		if (request.getManagerName() != null && !request.getManagerName().equals("")) {
			predicates.add(cb.like(member.get("managerName"), "%" + request.getManagerName() + "%"));
		}
		if (request.getOfficePhone() != null && !request.getOfficePhone().equals("")) {
			predicates.add(cb.like(member.get("officePhone"), "%" + request.getOfficePhone() + "%"));
		}
		if (request.getPhone() != null && !request.getPhone().equals("")) {
			predicates.add(cb.like(member.get("phone"), "%" + request.getPhone() + "%"));
		}
		if (request.getMail() != null && !request.getMail().equals("")) {
			predicates.add(cb.like(member.get("mail"), "%" + request.getMail() + "%"));
		}
		if (request.getAddress() != null && !request.getAddress().equals("")) {
			predicates.add(cb.like(member.get("address"), "%" + request.getAddress() + "%"));
		}
		if (request.getBuisnessNumber() != null && !request.getBuisnessNumber().equals("")) {
			predicates.add(cb.like(member.get("buisnessNumber"), "%" + request.getBuisnessNumber() + "%"));
		}
		if (request.getCategory() != null && !request.getCategory().equals("")) {
			predicates.add(cb.equal(member.get("category"), request.getCategory()));
		}
		if (request.getRewardYn() != null && !request.getRewardYn().equals("")) {
			predicates.add(cb.equal(member.get("rewardYn"), request.getRewardYn()));
		}
		if (request.getMobileYn() != null && !request.getMobileYn().equals("")) {
			predicates.add(cb.equal(member.get("mobileYn"), request.getMobileYn()));
		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));
		return em.createQuery(cq).getResultList();
	}

	public List<CpsMemberPacket.MemberInfo.MemberRequest> linkPriceAgencyMemberList(List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> linkPriceMerchantList){
		List<CpsMemberPacket.MemberInfo.MemberRequest> memberRequestList = new ArrayList<>();

		linkPriceMerchantList.forEach(linkPrice -> {
			CpsMemberPacket.MemberInfo.MemberRequest memberRequest = new CpsMemberPacket.MemberInfo.MemberRequest();
			memberRequest.setMemberId(linkPrice.getMerchantId());
			memberRequest.setManagerId("linkprice");
			memberRequest.setCompanyName(linkPrice.getMerchantName());
			memberRequest.setCategory(linkPrice.getCategoryId());
			memberRequest.setRewardYn(linkPrice.getRewardYn());
			memberRequest.setMobileYn(linkPrice.getMobileYn());
			memberRequest.setReturnDay(linkPrice.getReturnDay());
			memberRequest.setUrl(linkPrice.getMerchantUrl());
			memberRequest.setLogo(linkPrice.getMerchantLogo());
			memberRequestList.add(memberRequest);
		});

		return memberRequestList;
	}
}
