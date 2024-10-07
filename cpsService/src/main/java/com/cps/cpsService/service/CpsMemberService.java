package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.common.model.GenericPageBaseResponse;
import com.cps.common.utils.AES256Utils;
import com.cps.cpsService.dto.CpsMemberDetailDto;
import com.cps.cpsService.dto.CpsMemberDto;
import com.cps.cpsService.dto.CpsMemberListDto;
import com.cps.cpsService.entity.CpsAgencyEntity;
import com.cps.cpsService.entity.CpsCampaignEntity;
import com.cps.cpsService.entity.CpsMemberEntity;
import com.cps.cpsService.entity.CpsMemberSiteEntity;
import com.cps.cpsService.packet.CpsMemberPacket;
import com.cps.cpsService.repository.CpsAgencyRepository;
import com.cps.cpsService.repository.CpsCampaignRepository;
import com.cps.cpsService.repository.CpsMemberRepository;
import com.cps.cpsService.repository.CpsMemberSiteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsMemberService {
	private final CpsMemberRepository cpsMemberRepository;

	private final CpsMemberSiteRepository cpsMemberSiteRepository;

	private final CpsCampaignRepository cpsCampaignRepository;

	private final CpsAgencyRepository cpsAgencyRepository;

	private final HttpService httpService;

	private final UserService userService;

	private final CpsCampaignService cpsCampaignService;

	private final EntityManager em;

	private final AES256Utils aES256Utils;
	/**
	 * 회원 등록, 수정, 삭제
	 * @date 2024-09-05
	 */
	public GenericBaseResponse<CpsMemberDto> memberSignIn(CpsMemberPacket.MemberInfo.MemberSiteListRequest request) throws Exception {
		CpsMemberPacket.MemberInfo.Response response = new CpsMemberPacket.MemberInfo.Response();
		CpsMemberEntity cpsMemberEntity = userService.cpsUser(request);
		CpsMemberDto cpsMemberDto = userService.commonMemberDto(cpsMemberEntity);
		List<CpsMemberDto.CpsMemberSite> cpsMemberSiteDtoList = new ArrayList<>();
		CpsMemberEntity memberCheck = cpsMemberRepository.findByMemberId(request.getMemberId());

		if (request.getApiType().equals("I")) {
			try {
				if (null == memberCheck) {
					//회원정보 테이블 등록
					cpsMemberRepository.save(cpsMemberEntity);
					response.setSuccess();
				} else {
					response.setApiMessage(Constants.MEMBER_DUPLICATION.getCode(), Constants.MEMBER_DUPLICATION.getValue());
					return response;
				}
				response.setData(cpsMemberDto);
			} catch (Exception e) {
				response.setApiMessage(Constants.MEMBER_EXCEPTION.getCode(), Constants.MEMBER_EXCEPTION.getValue());
				log.error(Constant.EXCEPTION_MESSAGE + "memberSignIn request : {}, exception : {}", request, e);
			}
		} else if (null != memberCheck) {

			if (request.getApiType().equals("D")) {
				try {
					cpsMemberRepository.deleteByMemberId(request.getMemberId());
					cpsMemberSiteRepository.deleteByMemberId(request.getMemberId());
					response.setSuccess();
					response.setData(cpsMemberDto);
				} catch (Exception e) {
					response.setApiMessage(Constants.MEMBER_DELETE_EXCEPTION.getCode(), Constants.MEMBER_DELETE_EXCEPTION.getValue());
					log.error(Constant.EXCEPTION_MESSAGE + "memberDelete request : {}, exception : {}", request, e);
				}
			} else if (request.getApiType().equals("U")) {
				try {
					//회원정보 테이블 수정
					cpsMemberDto = userService.commonMemberDto(cpsMemberRepository.save(cpsMemberEntity));

					response.setSuccess();
					response.setData(cpsMemberDto);
				} catch (Exception e) {
					response.setApiMessage(Constants.MEMBER_UPDATE_EXCEPTION.getCode(), Constants.MEMBER_UPDATE_EXCEPTION.getValue());
					log.error(Constant.EXCEPTION_MESSAGE + "memberUpdate request : {}, exception : {}", request, e);
				}
			}
		} else {
			response.setApiMessage(Constants.MEMBER_BLANK.getCode(), Constants.MEMBER_BLANK.getValue());
		}

		//회원별 사이트 등록
		if (request.getMemberSiteList().size() > 0 && !request.getApiType().equals("D")) {
			cpsMemberSiteRepository.deleteByMemberId(request.getMemberId());
			List<CpsMemberSiteEntity> cpsMemberSiteList = new ArrayList<>();
			request.getMemberSiteList().forEach(site -> {
				CpsMemberSiteEntity cpsMemberSiteEntity = new CpsMemberSiteEntity();
				cpsMemberSiteEntity.setMemberId(request.getMemberId());
				cpsMemberSiteEntity.setSite(site.getSite());
				cpsMemberSiteEntity.setSiteName(site.getSiteName());
				cpsMemberSiteEntity.setCategory(site.getCategory());
				cpsMemberSiteList.add(cpsMemberSiteEntity);

				CpsMemberDto.CpsMemberSite cpsMemberSite = new CpsMemberDto.CpsMemberSite();
				cpsMemberSite.setSite(site.getSite());
				cpsMemberSite.setSiteName(site.getSiteName());
				cpsMemberSite.setCategory(site.getCategory());
				cpsMemberSiteDtoList.add(cpsMemberSite);
			});
			cpsMemberSiteRepository.saveAll(cpsMemberSiteList);
		}
		if (cpsMemberSiteDtoList.size() > 0) {
			cpsMemberDto.setSiteList(cpsMemberSiteDtoList);
		}

		response.setData(cpsMemberDto);
		return response;
	}

	/**
	 * 링크프라이스 광고주 정보
	 * @date 2024-09-05
	 */
	public GenericBaseResponse<CpsMemberDto> linkPriceUserSignIn(CpsMemberPacket.MemberInfo.AgencyMemberRequest request) throws Exception {
		CpsMemberPacket.MemberInfo.Response response = new CpsMemberPacket.MemberInfo.Response();

		List<CpsMemberEntity> cpsMemberEntityList = new ArrayList<>();
		List<CpsAgencyEntity> cpsAgencyEntityList = new ArrayList<>();
		List<CpsCampaignEntity> cpsCampaignEntityList = new ArrayList<>();

		try {
			//링크프라이스 광고주 데이터 가져오기
			CpsMemberPacket.MemberInfo.Domain linkPriceRequest = new CpsMemberPacket.MemberInfo.Domain();
			linkPriceRequest.setDomain("http://api.linkprice.com" + "/ci/service/all_merchant/A100000131" + "/all/cps/detail");
			List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> linkPriceMerchantList = httpService.SendUserLinkPriceMerchant(linkPriceRequest);

			//CPS_MEMBER 데이터 전환
			List<CpsMemberPacket.MemberInfo.MemberCampaignRequest> memberRequestList = userService.linkPriceAgencyMemberList(linkPriceMerchantList);

			memberRequestList.forEach(member -> {
				cpsAgencyEntityList.add(userService.cpsAgency(member));

				//회원 정보가 있는 경우 회원테이블 수정 제외 (관리자 화면에서만 수정)
				if (null == cpsMemberRepository.findByMemberId("link_"+member.getMemberId())) {
					member.setMemberId("link_"+member.getMemberId());
					member.setMemberPw(member.getMemberId());
					member.setType("MERCHANT"); member.setStatus("N");member.setBusinessType("B");
					member.setAgencyId("linkprice");
					CpsMemberEntity cpsMemberEntity =  new CpsMemberEntity();
					CpsCampaignEntity cpsCampaignEntity = new CpsCampaignEntity();
					CpsMemberPacket.MemberInfo.MemberCampaignRequest campaign = new CpsMemberPacket.MemberInfo.MemberCampaignRequest();

					try {
						cpsMemberEntity = userService.cpsUser(member);
						cpsCampaignEntity = cpsCampaignService.cpsMemberCampaign(member);
					} catch (Exception e) {
						e.printStackTrace();
					}
					cpsMemberEntityList.add(cpsMemberEntity);
					cpsCampaignEntityList.add(cpsCampaignEntity);
				}
			});

			cpsAgencyRepository.saveAll(cpsAgencyEntityList);

			//회원, 캠페인 자동 등록
			if (cpsMemberEntityList.size() > 0) {
				cpsMemberRepository.saveAll(cpsMemberEntityList);
				cpsCampaignRepository.saveAll(cpsCampaignEntityList);
				response.setSuccess();
			} else {
				response.setApiMessage(Constants.AGENCY_NOT_SEARCH.getCode(), Constants.AGENCY_NOT_SEARCH.getValue());
			}


		} catch (Exception e) {
			response.setApiMessage(Constants.API_EXCEPTION.getCode(), Constants.API_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " linkPriceMemberSignIn api  {}", e);
		}

		return response;
	}

	/**
	 * 회원정보조회
	 * @date 2024-09-30
	 */
	public GenericPageBaseResponse<CpsMemberListDto> memberList(CpsMemberPacket.MemberInfo.MemberListRequest request) throws Exception {
		CpsMemberPacket.MemberInfo.MemberListResponse response = new CpsMemberPacket.MemberInfo.MemberListResponse();
		List<CpsMemberListDto> CpsMemberListDtoList = new ArrayList<>();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<CpsMemberEntity> cq = cb.createQuery(CpsMemberEntity.class);
			Root<?> root = cq.from(CpsMemberEntity.class);

			List<Predicate> predicates = new ArrayList<>();

			if (null != request.getSearchKeyword() && !"".equals(request.getSearchKeyword())) {
				predicates.add(cb.like(root.get("memberId"), "%" + request.getSearchKeyword() + "%"));
				predicates.add(cb.like(root.get("memberName"), "%" + request.getSearchKeyword() + "%"));
			}

			if (!predicates.isEmpty()) {
				cq.where(cb.or(predicates.toArray(new Predicate[0])));
			}

			TypedQuery<CpsMemberEntity> query = em.createQuery(cq);
			//전체 개수
			int TotalPage = query.getResultList().size();

			// 페이징 처리
			if (request.getSize() == 0)request.setSize(10);
			query = em.createQuery(cq);
			query.setFirstResult(request.getPage() * request.getSize());
			query.setMaxResults(request.getSize());

			query.getResultList().forEach(member ->{
				CpsMemberListDto cpsMemberListDto = new CpsMemberListDto();
				cpsMemberListDto.setMemberId(member.getMemberId());
				cpsMemberListDto.setMemberPw(aES256Utils.decrypt(member.getMemberPw()));
				cpsMemberListDto.setMemberName(member.getMemberName());
				cpsMemberListDto.setManagerName(member.getManagerName());
				cpsMemberListDto.setManagerPhone(member.getManagerPhone());
				cpsMemberListDto.setManagerEmail(member.getManagerEmail());
				cpsMemberListDto.setCompanyPhone(member.getCompanyPhone());
				cpsMemberListDto.setType(member.getType());
				CpsMemberListDtoList.add(cpsMemberListDto);
			});

			if (CpsMemberListDtoList.size() > 0) {
				response.setSuccess(TotalPage);
			} else {
				response.setApiMessage(Constants.MEMBER_BLANK.getCode(), Constants.MEMBER_BLANK.getValue());
			}
			response.setDatas(CpsMemberListDtoList);
		} catch (Exception e) {
			response.setApiMessage(Constants.MEMBER_SEARCH_EXCEPTION.getCode(), Constants.MEMBER_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberList api  {}", e);
		}

		return response;
	}

	/**
	 * 회원상세조회
	 * @date 2024-10-01
	 */
	public GenericBaseResponse<CpsMemberDetailDto> memberDetail(CpsMemberPacket.MemberInfo.MemberDetail request) throws Exception {
		CpsMemberPacket.MemberInfo.MemberDetailResponse response = new CpsMemberPacket.MemberInfo.MemberDetailResponse();
		List<CpsMemberDetailDto.CpsMemberSite> cpsEmeberSiteList = new ArrayList<>();
		CpsMemberDetailDto cpsMemberDetail = new CpsMemberDetailDto();
		try {
			CpsMemberEntity cpsMemberEntity = cpsMemberRepository.findByMemberId(request.getMemberId());

			if(null != cpsMemberEntity){
				cpsMemberDetail = cpsMemberDetailDto(cpsMemberEntity);
				List<CpsMemberSiteEntity> cpsMemberSiteList = cpsMemberSiteRepository.findByMemberId(request.getMemberId());
				if (cpsMemberSiteList.size() > 0) {
					cpsMemberSiteList.forEach(site->{
						CpsMemberDetailDto.CpsMemberSite cpsEmeberSite = new CpsMemberDetailDto.CpsMemberSite();
						cpsEmeberSite.setSite(site.getSite());
						cpsEmeberSite.setCategory(site.getCategory());
						cpsEmeberSite.setSiteName(site.getSiteName());
						cpsEmeberSiteList.add(cpsEmeberSite);
					});
					cpsMemberDetail.setSiteList(cpsEmeberSiteList);
				}
				response.setSuccess();
				response.setData(cpsMemberDetail);
			} else {
				response.setApiMessage(Constants.MEMBER_BLANK.getCode(), Constants.MEMBER_BLANK.getValue());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.MEMBER_SEARCH_EXCEPTION.getCode(), Constants.MEMBER_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberDetail api  {}", e);
		}

		return response;
	}

	public CpsMemberDetailDto cpsMemberDetailDto (CpsMemberEntity cpsMemberEntity){
		CpsMemberDetailDto cpsMemberDetailDto = new CpsMemberDetailDto();
		cpsMemberDetailDto.setMemberId(cpsMemberEntity.getMemberId());
		cpsMemberDetailDto.setMemberPw(aES256Utils.decrypt(cpsMemberEntity.getMemberPw()));
		cpsMemberDetailDto.setMemberName(cpsMemberEntity.getMemberName());
		cpsMemberDetailDto.setType(cpsMemberEntity.getType());
		cpsMemberDetailDto.setBusinessType(cpsMemberEntity.getBusinessType());
		cpsMemberDetailDto.setAgencyId(cpsMemberEntity.getAgencyId());
		cpsMemberDetailDto.setBank(cpsMemberEntity.getBank());
		cpsMemberDetailDto.setAccountName(cpsMemberEntity.getAccountName());
		cpsMemberDetailDto.setStatus(cpsMemberEntity.getStatus());
		cpsMemberDetailDto.setCeoName(cpsMemberEntity.getCeoName());
		cpsMemberDetailDto.setBusinessNumber(cpsMemberEntity.getBusinessNumber());
		cpsMemberDetailDto.setCompanyAddress(cpsMemberEntity.getCompanyAddress());
		cpsMemberDetailDto.setBusinessCategory(cpsMemberEntity.getBusinessCategory());
		cpsMemberDetailDto.setBusinessSector(cpsMemberEntity.getBuisinessSector());
		cpsMemberDetailDto.setManagerName(cpsMemberEntity.getManagerName());
		cpsMemberDetailDto.setManagerEmail(cpsMemberEntity.getManagerEmail());
		cpsMemberDetailDto.setManagerPhone(cpsMemberEntity.getManagerPhone());
		cpsMemberDetailDto.setCompanyPhone(cpsMemberEntity.getCompanyPhone());
		cpsMemberDetailDto.setBirthYear(cpsMemberEntity.getBirthYear());
		cpsMemberDetailDto.setSex(cpsMemberEntity.getSex());
		cpsMemberDetailDto.setLicense(cpsMemberEntity.getLicense());
		cpsMemberDetailDto.setApiType(cpsMemberEntity.getType());
		return cpsMemberDetailDto;
	}
}
