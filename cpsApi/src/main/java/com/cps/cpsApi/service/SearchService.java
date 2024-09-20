package com.cps.cpsApi.service;

import com.cps.cpsApi.dto.SummaryDto;
import com.cps.cpsApi.entity.CpsCampaignEntity;
import com.cps.cpsApi.entity.SummaryDayEntity;
import com.cps.cpsApi.packet.CpsCampaignPacket;
import com.cps.cpsApi.packet.SummaryPacket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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

	public List<SummaryDto> summarySearch(SummaryPacket.SummaryInfo.SummaryRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SummaryDto> cq = cb.createQuery(SummaryDto.class);
		Root<?> root = cq.from(SummaryDayEntity.class);

		List<Predicate> predicates = new ArrayList<>();

		// 날짜 조건 필수
		if ("MONTH".equals(request.getDayType())) {
			predicates.add(cb.between(root.get("regYm"), request.getRegStart(), request.getRegEnd()));
		} else {
			predicates.add(cb.between(root.get("regDay"), request.getRegStart(), request.getRegEnd()));
		}

		// 영역
		if (request.getOs() != null && !request.getOs().equals("")) {
			predicates.add(cb.equal(root.get("os"), request.getOs()));
		}

		// 검색어 조회
		if (request.getKeyword() != null && !request.getKeyword().equals("")) {
			if ("MEMBER".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				Predicate memberName = cb.like(root.get("memberName"), "%" + request.getKeyword() + "%");
				Predicate memberId = cb.like(root.get("memberId"), "%" + request.getKeyword() + "%");
				predicates.add(cb.or(memberName, memberId));
			}
			if ("SITE".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				predicates.add(cb.like(root.get("site"), "%" + request.getKeyword() + "%"));
			}
			if ("CAMPAIGN".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				predicates.add(cb.like(root.get("campaignName"), "%" + request.getKeyword() + "%"));
			}
			if ("AFFLIATE".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				predicates.add(cb.like(root.get("affliateId"), "%" + request.getKeyword() + "%"));
			}
		}

		// Select 및 Group By 설정
		if ("DAY".equals(request.getSubSearchType())) {
			cq.select(cb.construct(SummaryDto.class, root.get("regDay"), cb.literal("DAY"), cb.sum(root.get("cnt")), cb.sum(root.get("clickCnt")))).groupBy(root.get("regDay"));
		} else if ("MONTH".equals(request.getSubSearchType())) {
			cq.select(cb.construct(SummaryDto.class, root.get("regYm"), cb.literal("MONTH"), cb.sum(root.get("cnt")), cb.sum(root.get("clickCnt")))).groupBy(root.get("regYm"));
		} else if ("MEMBER".equals(request.getSubSearchType())) {
			cq.select(cb.construct(SummaryDto.class, root.get("memberId"), root.get("memberName"), cb.literal("MEMBER"), cb.sum(root.get("cnt")), cb.sum(root.get("clickCnt")))).groupBy(root.get("memberId"), root.get("memberName"));
		} else if ("CAMPAIGN".equals(request.getSubSearchType())) {
			cq.select(cb.construct(SummaryDto.class, root.get("campaignNum"), root.get("campaignName"), cb.literal("CAMPAIGN"), cb.sum(root.get("cnt")), cb.sum(root.get("clickCnt")))).groupBy(root.get("campaignNum"));
		} else if ("AFFLIATE".equals(request.getSubSearchType())) {
			cq.select(cb.construct(SummaryDto.class, root.get("affliateId"), root.get("affliateName"), cb.literal("AFFLIATE"), cb.sum(root.get("cnt")), cb.sum(root.get("clickCnt")))).groupBy(root.get("affliateId"));
		} else if ("SITE".equals(request.getSubSearchType())) {
			cq.select(cb.construct(SummaryDto.class, root.get("site"), cb.literal("SITE"), cb.sum(root.get("cnt")), cb.sum(root.get("clickCnt")))).groupBy(root.get("site"));
		} else if ("MEMBERAGC".equals(request.getSubSearchType())) {
			cq.select(cb.construct(SummaryDto.class, root.get("agencyId"), root.get("agencyName"), cb.literal("MEMBERAGC"), cb.sum(root.get("cnt")), cb.sum(root.get("clickCnt")))).groupBy(root.get("agencyId"), root.get("agencyName"));
		} else if ("MEMBERAFF".equals(request.getSubSearchType())) {
			cq.select(cb.construct(SummaryDto.class, root.get("agencyId"), root.get("agencyName"), cb.literal("MEMBERAFF"), cb.sum(root.get("cnt")), cb.sum(root.get("clickCnt")))).groupBy(root.get("agencyId"), root.get("agencyName"));
		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));
		TypedQuery<SummaryDto> query = em.createQuery(cq);
		return query.getResultList();
	}
}
