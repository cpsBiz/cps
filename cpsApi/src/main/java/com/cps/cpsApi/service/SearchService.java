package com.cps.cpsApi.service;

import com.cps.common.constant.Constants;
import com.cps.cpsApi.dto.CpsCampaignSearchDto;
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

	public CpsCampaignPacket.CampaignInfo.CampaignSearchResponse campaignSearch(CpsCampaignPacket.CampaignInfo.CampaignSearchRequest request){
		CpsCampaignPacket.CampaignInfo.CampaignSearchResponse response = new CpsCampaignPacket.CampaignInfo.CampaignSearchResponse();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CpsCampaignEntity> cq = cb.createQuery(CpsCampaignEntity.class);
		List<CpsCampaignSearchDto> campaignSearchList = new ArrayList<>();
		CpsCampaignSearchDto cpsCampaignSearchDto = new CpsCampaignSearchDto();
		Root<?> root = cq.from(CpsCampaignEntity.class);

		List<Predicate> predicates = new ArrayList<>();

		if (request.getCampaignNum() > 0) {
			predicates.add(cb.equal(root.get("campaignNum"), request.getCampaignNum()));
		}
		if (request.getMemberId() != null && !request.getMemberId().equals("")) {
			predicates.add(cb.like(root.get("memberId"), "%" + request.getMemberId() + "%"));
		}
		if (request.getAgencyId() != null && !request.getAgencyId().equals("")) {
			predicates.add(cb.like(root.get("agencyId"), "%" + request.getAgencyId () + "%"));
		}
		if (request.getCampaignName() != null && !request.getCampaignName().equals("")) {
			predicates.add(cb.like(root.get("campaignName"), "%" + request.getCampaignName() + "%"));
		}
		if (request.getCampaignStart() != null && !request.getCampaignStart().equals("")) {
			predicates.add(cb.equal(root.get("campaignStart"), request.getCampaignStart()));
		}
		if (request.getCampaignEnd() != null && !request.getCampaignEnd().equals("")) {
			predicates.add(cb.equal(root.get("campaignEnd"), request.getCampaignEnd()));
		}
		if (request.getClickUrl() != null && !request.getClickUrl().equals("")) {
			predicates.add(cb.like(root.get("clickUrl"), "%" + request.getClickUrl() + "%"));
		}
		if (request.getCategory() != null && !request.getCategory().equals("")) {
			predicates.add(cb.equal(root.get("category"), request.getCategory()));
		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));
		TypedQuery<CpsCampaignEntity> query = em.createQuery(cq);
		//전체 개수
		response.setTotalCount(query.getResultList().size());

		// 페이징 처리
		if (request.getSize() == 0)request.setSize(10);
		query = em.createQuery(cq);
		query.setFirstResult(request.getPage() * request.getSize());
		query.setMaxResults(request.getSize());

		query.getResultList().forEach(campaign -> {
			CpsCampaignSearchDto campaignSearch = new CpsCampaignSearchDto();
			campaignSearch.setCampaignNum(campaign.getCampaignNum());
			campaignSearch.setCampaignName(campaign.getCampaignName());
			campaignSearch.setMemberId(campaign.getMemberId());
			campaignSearch.setAgencyId(campaign.getAgencyId());
			campaignSearch.setCampaignStart(campaign.getCampaignStart());
			campaignSearch.setCampaignEnd(campaign.getCampaignEnd());
			campaignSearch.setUrl(campaign.getUrl());
			campaignSearch.setClickUrl(campaign.getClickUrl());
			campaignSearch.setCategory(campaign.getCategory());
			campaignSearch.setLogo(campaign.getLogo());
			campaignSearch.setIcon(campaign.getIcon());
			campaignSearch.setCampaignAuto(campaign.getCampaignAuto());
			campaignSearch.setRewardYn(campaign.getRewardYn());
			campaignSearch.setAosYn(campaign.getAosYn());
			campaignSearch.setIosYn(campaign.getIosYn());
			campaignSearch.setReturnDay(campaign.getReturnDay());
			campaignSearch.setCommissionSendYn(campaign.getCommissionSendYn());
			campaignSearch.setWhenTrans(campaign.getWhenTrans());
			campaignSearch.setTransReposition(campaign.getTransReposition());
			campaignSearch.setCommissionPaymentStandard(campaign.getCommissionPaymentStandard());
			campaignSearch.setDenyAd(campaign.getDenyAd());
			campaignSearch.setDenyProduct(campaign.getDenyProduct());
			campaignSearch.setNotice(campaign.getNotice());
			campaignSearch.setCampaignStatus(campaign.getCampaignStatus());
			campaignSearchList.add(campaignSearch);
		});

		response.setDatas(campaignSearchList);
		return response;
	}

	public SummaryPacket.SummaryInfo.SummaryResponse summarySearch(SummaryPacket.SummaryInfo.SummaryRequest request) {
		SummaryPacket.SummaryInfo.SummaryResponse response = new SummaryPacket.SummaryInfo.SummaryResponse();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SummaryDto> cq = cb.createQuery(SummaryDto.class);
		Root<?> root = cq.from(SummaryDayEntity.class);
		String orderByName = "regDay";

		List<Predicate> predicates = new ArrayList<>();

		// 날짜 조건 필수
		if ("MONTH".equals(request.getDayType())) {
			predicates.add(cb.between(root.get("regYm"), request.getRegStart(), request.getRegEnd()));
		} else if ("DAY".equals(request.getDayType())) {
			predicates.add(cb.between(root.get("regDay"), request.getRegStart(), request.getRegEnd()));
		} else if ("EQMONTH".equals(request.getDayType())) {
			predicates.add(cb.equal(root.get("regYm"), request.getKeyword()));
		} else {
			predicates.add(cb.equal(root.get("regDay"), request.getKeyword()));
		}

		// 영역
		if (request.getOs() != null && !request.getOs().equals("")) {
			predicates.add(cb.equal(root.get("os"), request.getOs()));
		}

		// 검색어 조회
		if (request.getKeyword() != null && !request.getKeyword().equals("")) {
			if ("MERCHANT".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				Predicate memberName = cb.like(root.get("memberName"), "%" + request.getKeyword() + "%");
				Predicate memberId = cb.like(root.get("memberId"), "%" + request.getKeyword() + "%");
				predicates.add(cb.or(memberName, memberId));
			}
			if ("SITE".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				predicates.add(cb.like(root.get("site"), "%" + request.getKeyword() + "%"));
			}
			if ("CAMPAIGN".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				Predicate campaignName = cb.like(root.get("campaignName"), "%" + request.getKeyword() + "%");
				Predicate campaignId = cb.like(root.get("campaignId"), "%" + request.getKeyword() + "%");
				predicates.add(cb.or(campaignName, campaignId));
			}
			if ("AFFLIATE".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				predicates.add(cb.like(root.get("affliateId"), "%" + request.getKeyword() + "%"));
			}

			if ("EQMEMBER".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("memberId"), request.getKeyword()));
			} else if ("EQSITE".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("site"), request.getKeyword()));
			} else if ("EQCAMPAIGN".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("campaignNum"), request.getKeyword()));
			} else if ("EQAFFLIATE".equals(request.getKeywordType()) || "ALL".equals(request.getKeywordType())) {
				predicates.add(cb.equal(root.get("affliateId"), request.getKeyword()));
			}
		}

		// Select 및 Group By 설정
		if ("DAY".equals(request.getSearchType())) {
			cq = createSummaryQuery(cb, cq, root,  "regDay", root.get("regDay"), root.get("regDay"));
		} else if ("MONTH".equals(request.getSearchType())) {
			cq = createSummaryQuery(cb, cq, root,  "regYm", root.get("regYm"), root.get("regYm"));
			orderByName = "regYm";
		} else if ("MERCHANT".equals(request.getSearchType())) {
			cq = createSummaryQuery(cb, cq, root,  "memberId", root.get("memberId"), root.get("memberName"));
			orderByName = "memberName";
		} else if ("CAMPAIGN".equals(request.getSearchType())) {
			cq = createSummaryQuery(cb, cq, root,  "campaignNum", root.get("campaignNum"), root.get("campaignName"));
			orderByName = "campaignName";
		} else if ("AFFLIATE".equals(request.getSearchType())) {
			cq = createSummaryQuery(cb, cq, root,  "affliateId", root.get("affliateId"), root.get("affliateName"));
			orderByName = "affliateName";
		} else if ("SITE".equals(request.getSearchType())) {
			cq = createSummaryQuery(cb, cq, root, "site", root.get("site"), root.get("site"));
			orderByName = "site";
		} else if ("MEMBERAGC".equals(request.getSearchType())) {
			cq = createSummaryQuery(cb, cq, root,  "agencyId", root.get("agencyId"), root.get("agencyName"));
			orderByName = "agencyName";
		} else if ("MEMBERAFF".equals(request.getSearchType())) {
			cq = createSummaryQuery(cb, cq, root,  "agencyId", root.get("agencyId"), root.get("agencyName"));
			orderByName = "agencyName";
		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));

		TypedQuery<SummaryDto> query = em.createQuery(cq);
		//전체 개수
		response.setTotalCount(query.getResultList().size());

		if ("ASC".equals(request.getOrderBy())) {
			cq.orderBy(cb.asc(root.get(orderByName)));
		} else if ("DESC".equals(request.getOrderBy())) {
			cq.orderBy(cb.desc(root.get(orderByName)));
		}

		// 페이징 처리
		if (request.getSize() == 0)request.setSize(10);
		query = em.createQuery(cq);
		query.setFirstResult(request.getPage() * request.getSize());
		query.setMaxResults(request.getSize());
		response.setDatas(query.getResultList());

		return response;
	}

	private <T> CriteriaQuery<SummaryDto> createSummaryQuery(CriteriaBuilder cb, CriteriaQuery<SummaryDto> cq, Root<T> root, String groupByField, Expression<?>... additionalFields) {
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
				additionalFields.length > 1 ? additionalFields[1] : cb.literal(null),
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
