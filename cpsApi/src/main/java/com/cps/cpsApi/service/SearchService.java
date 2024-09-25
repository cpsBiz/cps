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

			//상세검색
			if ("MEMBER_LIST".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("memberId"), request.getKeyword()));
			}
			if ("SITE_LIST".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("site"), request.getKeyword()));
			}
			if ("CAMPAIGN_LIST".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("campaignNum"), request.getKeyword()));
			}
			if ("AFFLIATE_LIST".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("affliateId"), request.getKeyword()));
			}
			if ("MEMBERAGC_LIST".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("agencyId"), request.getKeyword()));
			}
			if ("MEMBERAFF_LIST".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("agencyId"), request.getKeyword()));
			}
		}

		// Select 및 Group By 설정
		if ("DAY".equals(request.getSubSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "DAY", "regDay", root.get("regDay"));
		} else if ("MONTH".equals(request.getSubSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "MONTH", "regDay", root.get("regYm"));
		} else if ("MEMBER".equals(request.getSubSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "MEMBER", "memberId", root.get("memberId"), root.get("memberName"));
		} else if ("CAMPAIGN".equals(request.getSubSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "CAMPAIGN", "campaignNum", root.get("campaignNum"), root.get("campaignName"));
		} else if ("AFFLIATE".equals(request.getSubSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "AFFLIATE", "affliateId", root.get("affliateId"), root.get("affliateName"));
		} else if ("SITE".equals(request.getSubSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "SITE", "site", root.get("site"), root.get("site"));
		} else if ("MEMBERAGC".equals(request.getSubSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "MEMBERAGC", "agencyId", root.get("agencyId"), root.get("agencyName"));
		} else if ("MEMBERAFF".equals(request.getSubSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "MEMBERAFF", "agencyId", root.get("agencyId"), root.get("agencyName"));
		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));
		TypedQuery<SummaryDto> query = em.createQuery(cq);
		return query.getResultList();
	}

	private <T> CriteriaQuery<SummaryDto> createSummaryQuery(CriteriaBuilder cb, CriteriaQuery<SummaryDto> cq, Root<T> root, String subSearchType, String groupByField, Expression<?>... additionalFields) {
		Expression<String> subSearchTypeLiteral = cb.literal(subSearchType); // 서브 검색 타입 리터럴
		Expression<Long> sumCnt = cb.sum(root.get("cnt"));
		Expression<Long> sumClickCnt = cb.sum(root.get("clickCnt"));
		Expression<Long> rewardCnt = cb.sum(root.get("rewardCnt"));
		Expression<Long> confirmRewardCnt = cb.sum(root.get("confirmRewardCnt"));
		Expression<Long> cancelRewardCnt = cb.sum(root.get("cancelRewardCnt"));
		Expression<Long> productPrice = cb.sum(root.get("productPrice"));
		Expression<Long> confirmProductPrice = cb.sum(root.get("confirmProductPrice"));
		Expression<Long> cancelProductPrice = cb.sum(root.get("cancelProductPrice"));
		Expression<Long> commission = cb.sum(root.get("commission"));
		Expression<Long> confirmCommission = cb.sum(root.get("confirmCommission"));
		Expression<Long> cancelCommission = cb.sum(root.get("cancelCommission"));
		Expression<Long> commissionProfit = cb.sum(root.get("commissionProfit"));
		Expression<Long> confirmCommissionProfit = cb.sum(root.get("confirmCommissionProfit"));
		Expression<Long> cancelCommissionProfit = cb.sum(root.get("cancelCommissionProfit"));
		Expression<Long> affliateCommission = cb.sum(root.get("affliateCommission"));
		Expression<Long> confirmAffliateCommission = cb.sum(root.get("confirmAffliateCommission"));
		Expression<Long> cancelAffliateCommission = cb.sum(root.get("cancelAffliateCommission"));
		Expression<Long> userCommission = cb.sum(root.get("userCommission"));
		Expression<Long> confirmUserCommission = cb.sum(root.get("confirmUserCommission"));
		Expression<Long> cancelUserCommission = cb.sum(root.get("cancelUserCommission"));

		// 공통 construct 구조에 필요한 필드 추가
		cq.select(cb.construct(
				SummaryDto.class,
				additionalFields[0],
				additionalFields.length > 1 ? additionalFields[1] : subSearchTypeLiteral,
				subSearchTypeLiteral,
				sumCnt,
				sumClickCnt,
				rewardCnt,
				confirmRewardCnt,
				cancelRewardCnt,
				productPrice,
				confirmProductPrice,
				cancelProductPrice,
				commission,
				confirmCommission,
				cancelCommission,
				commissionProfit,
				confirmCommissionProfit,
				cancelCommissionProfit,
				affliateCommission,
				confirmAffliateCommission,
				cancelAffliateCommission,
				userCommission,
				confirmUserCommission,
				cancelUserCommission
		)).groupBy(root.get(groupByField));

		return cq;
	}
}
