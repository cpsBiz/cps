package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;

import com.cps.cpsApi.dto.CpsMemberDto;
import com.cps.cpsApi.entity.CpsCampaignEntity;
import com.cps.cpsApi.entity.CpsMemberEntity;
import com.cps.cpsApi.packet.CpsMemberPacket;
import com.cps.cpsApi.repository.CpsCampaignRepository;
import com.cps.cpsApi.repository.CpsMemberRepository;
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
	@Value("${linkPrice.domain.url}") String linkPriceDomain;

	@Value("${linkPrice.reco.url}") String linkPriceEndPoint;

	private final CpsMemberRepository cpsMemberRepository;

	private final CpsCampaignRepository cpsCampaignRepository;

	private final HttpService httpService;

	private final MemberService memberService;

	private final SearchService sarchService;

	private final CpsCampaignService cpsCampaignService;

	/**
	 * 회원 등록, 수정, 삭제
	 * @date 2024-09-05
	 */
	public GenericBaseResponse<CpsMemberDto> memberSignIn(CpsMemberPacket.MemberInfo.MemberRequest request) throws Exception {
		CpsMemberPacket.MemberInfo.Response response = new CpsMemberPacket.MemberInfo.Response();
		CpsMemberEntity cpsMemberEntity = memberService.cpsMember(request);
		CpsMemberDto cpsMemberDto = memberService.commonMemberDto(cpsMemberEntity);
		CpsMemberEntity memberCheck = cpsMemberRepository.findByMemberIdAndManagerId(request.getMemberId(), request.getManagerId());

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
				log.error(Constant.EXCEPTION_MESSAGE + " memberSignIn  {}", e);
			}
		} else if (null != memberCheck) {

			if (request.getApiType().equals("D")) {
				try {
					cpsMemberRepository.deleteByMemberId(request.getMemberId());
					response.setSuccess();
					response.setData(cpsMemberDto);
				} catch (Exception e) {
					response.setApiMessage(Constants.MEMBER_DELETE_EXCEPTION.getCode(), Constants.MEMBER_DELETE_EXCEPTION.getValue());
					log.error(Constant.EXCEPTION_MESSAGE + " memberDelete  {}", e);
				}
			} else if (request.getApiType().equals("U")) {
				try {
					//회원정보 테이블 수정
					cpsMemberDto = memberService.commonMemberDto(cpsMemberRepository.save(cpsMemberEntity));

					response.setSuccess();
					response.setData(cpsMemberDto);
				} catch (Exception e) {
					response.setApiMessage(Constants.MEMBER_UPDATE_EXCEPTION.getCode(), Constants.MEMBER_UPDATE_EXCEPTION.getValue());
					log.error(Constant.EXCEPTION_MESSAGE + " memberUpdate  {}", e);
				}
			}
		} else {
			response.setApiMessage(Constants.MEMBER_BLANK.getCode(), Constants.MEMBER_BLANK.getValue());
		}

		return response;
	}

	/**
	 * 링크프라이스 광고주 정보
	 * @date 2024-09-05
	 */
	public GenericBaseResponse<CpsMemberDto> linkPriceMemberSignIn(CpsMemberPacket.MemberInfo.AgencyMemberRequest request) throws Exception {
		CpsMemberPacket.MemberInfo.Response response = new CpsMemberPacket.MemberInfo.Response();

		List<CpsMemberEntity> cpsMemberEntityList = new ArrayList<>();
		List<CpsCampaignEntity> cpsCampaignEntityList = new ArrayList<>();
		List<CpsMemberPacket.MemberInfo.MemberRequest> memberRequestList = new ArrayList<>();

		try {
			//링크프라이스 광고주 데이터 가져오기
			CpsMemberPacket.MemberInfo.Domain linkPriceRequest = new CpsMemberPacket.MemberInfo.Domain();
			linkPriceRequest.setDomain(linkPriceDomain + linkPriceEndPoint + "/all/cps/detail");
			List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> linkPriceMerchantList = httpService.SendLinkPriceMerchant(linkPriceRequest);

			//CPS_MEMBER 데이터 전환
			memberRequestList = memberService.linkPriceAgencyMemberList(linkPriceMerchantList);

			memberRequestList.forEach(member -> {
				//회원 정보가 있는 경우 회원테이블 수정 제외 (관리자 화면에서만 수정)
				if (null == cpsMemberRepository.findByMemberIdAndManagerId(member.getMemberId(), member.getManagerId())) {
					member.setMemberPw("ENLIPLE_"+member.getMemberId());
					member.setType("C"); member.setStatus("N");
					if (member.getMemberId().equals("linkprice")) {
						member.setType("A");
						member.setStatus("Y");
					}
					member.setManagerId("linkprice");

					CpsMemberEntity cpsMemberEntity =  new CpsMemberEntity();
					CpsCampaignEntity cpsCampaignEntity = new CpsCampaignEntity();

					try {
						cpsMemberEntity = memberService.cpsMember(member);
						cpsCampaignEntity = cpsCampaignService.cpsMemberCampaign(member);
					} catch (Exception e) {
						e.printStackTrace();
					}
					cpsMemberEntityList.add(cpsMemberEntity);
					cpsCampaignEntityList.add(cpsCampaignEntity);
				}
			});

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
	 * 회원 정보 리스트
	 * @date 2024-09-09
	 */
	public GenericBaseResponse<CpsMemberEntity> memberSearch(CpsMemberPacket.MemberInfo.MemberSearcgRequest request) throws Exception {
		CpsMemberPacket.MemberInfo.MemberSearchResponse response = new CpsMemberPacket.MemberInfo.MemberSearchResponse();
		List<CpsMemberEntity> cpsMemberList = new ArrayList<>();

		try {
			cpsMemberList = sarchService.memberSearch(request);
			if (cpsMemberList.size() > 0) {
				response.setSuccess();
			} else {
				response.setApiMessage(Constants.MEMBER_BLANK.getCode(), Constants.MEMBER_BLANK.getValue());
			}
			response.setDatas(cpsMemberList);
		} catch (Exception e) {
			response.setApiMessage(Constants.MEMBER_SEARCH_EXCEPTION.getCode(), Constants.MEMBER_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberList api  {}", e);
		}

		return response;
	}
}
