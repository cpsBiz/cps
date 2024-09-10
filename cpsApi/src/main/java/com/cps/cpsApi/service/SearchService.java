package com.cps.cpsApi.service;

import com.cps.common.utils.AES256Utils;
import com.cps.cpsApi.dto.CpsMemberDto;
import com.cps.cpsApi.entity.CpsCampaignEntity;
import com.cps.cpsApi.entity.CpsMemberEntity;
import com.cps.cpsApi.packet.CpsCampaignPacket;
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
public class SearchService {

	private final EntityManager em;

	public List<CpsMemberEntity> memberSearch(CpsMemberPacket.MemberInfo.MemberSearcgRequest request){
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

	public List<CpsCampaignEntity> campaignSearch(CpsCampaignPacket.CampaignInfo.CampaignSearchRequest request){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CpsCampaignEntity> cq = cb.createQuery(CpsCampaignEntity.class);
		Root<CpsCampaignEntity> member = cq.from(CpsCampaignEntity.class);

		List<Predicate> predicates = new ArrayList<>();

		if (request.getCampaignNum() > 0) {
			predicates.add(cb.equal(member.get("campaignNum"), request.getCampaignNum()));
		}
		if (request.getMemberId() != null && !request.getMemberId().equals("")) {
			predicates.add(cb.like(member.get("memberId"), "%" + request.getMemberId() + "%"));
		}
		if (request.getManagerId() != null && !request.getManagerId().equals("")) {
			predicates.add(cb.like(member.get("managerId"), "%" + request.getManagerId() + "%"));
		}
		if (request.getCampaignName() != null && !request.getCampaignName().equals("")) {
			predicates.add(cb.like(member.get("campaignName"), "%" + request.getCampaignName() + "%"));
		}
		if (request.getCampaignStart() != null && !request.getCampaignStart().equals("")) {
			predicates.add(cb.equal(member.get("campaignStart"), request.getCampaignStart()));
		}
		if (request.getCampaignEnd() != null && !request.getCampaignEnd().equals("")) {
			predicates.add(cb.equal(member.get("campaignEnd"), request.getCampaignEnd()));
		}
		if (request.getClickUrl() != null && !request.getClickUrl().equals("")) {
			predicates.add(cb.like(member.get("clickUrl"), "%" + request.getClickUrl() + "%"));
		}
		if (request.getCategory() != null && !request.getCategory().equals("")) {
			predicates.add(cb.equal(member.get("category"), request.getCategory()));
		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));
		return em.createQuery(cq).getResultList();
	}
}
