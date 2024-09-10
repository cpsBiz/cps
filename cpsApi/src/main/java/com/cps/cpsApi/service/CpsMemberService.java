package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;

import com.cps.cpsApi.dto.CpsMemberDto;
import com.cps.cpsApi.dto.CpsMemberListDto;
import com.cps.cpsApi.entity.CpsMemberEntity;
import com.cps.cpsApi.packet.CpsMemberPacket;
import com.cps.cpsApi.repository.CpsMemberRepository;
import jakarta.persistence.EntityManager;
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
	@Value("${linkPrice.domain.url}") String linkPriceDomain;

	@Value("${linkPrice.reco.url}") String linkPriceEndPoint;

	private final CpsMemberRepository cpsMemberRepository;

	private final HttpService httpService;

	private final MemberService memberService;

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
		List<CpsMemberPacket.MemberInfo.MemberRequest> memberRequestList = new ArrayList<>();

		String[] allAprArray = {"/all", "/apr"};
		String[] cpsCpaArray = {"/cps", "/cpa"};

		try {
			for (String allApr : allAprArray) {
				for (String cpsCpa : cpsCpaArray) {
					//링크프라이스 광고주 데이터 가져오기
					CpsMemberPacket.MemberInfo.Domain linkPriceRequest = new CpsMemberPacket.MemberInfo.Domain();
					linkPriceRequest.setDomain(linkPriceDomain + linkPriceEndPoint + allApr + cpsCpa);
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
							CpsMemberEntity cpsMemberEntity = memberService.cpsMember(member);
							cpsMemberEntityList.add(cpsMemberEntity);
						}
					});

					if (cpsMemberEntityList.size() > 0) {
						cpsMemberRepository.saveAll(cpsMemberEntityList);
					}
					response.setSuccess();
				}
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
	public GenericBaseResponse<CpsMemberListDto> memberSearch(CpsMemberPacket.MemberInfo.MemberListRequest request) throws Exception {
		CpsMemberPacket.MemberInfo.MemberListResponse response = new CpsMemberPacket.MemberInfo.MemberListResponse();
		List<CpsMemberListDto> cpsMemberList = new ArrayList<>();

		try {
			List<CpsMemberEntity> cpsMemberEntityList = memberService.memberSearch(request);

			if (cpsMemberEntityList.size() > 0) {
				cpsMemberEntityList.forEach(member -> {
					CpsMemberListDto cpsMemberListDto = new CpsMemberListDto();
					cpsMemberListDto.setManagerId(member.getManagerId());
					cpsMemberListDto.setMemberId(member.getMemberId());
					cpsMemberListDto.setCompanyName(member.getCompanyName());
					cpsMemberListDto.setType(member.getType());
					cpsMemberListDto.setStatus(member.getStatus());
					cpsMemberListDto.setManagerName(member.getManagerName());
					cpsMemberListDto.setOfficePhone(member.getOfficePhone());
					cpsMemberListDto.setPhone(member.getPhone());
					cpsMemberListDto.setMail(member.getMail());
					cpsMemberListDto.setAddress(member.getAddress());
					cpsMemberListDto.setBuisnessNumber(member.getBuisnessNumber());
					cpsMemberListDto.setCategory(member.getCategory());
					cpsMemberListDto.setRewardYn(member.getRewardYn());
					cpsMemberListDto.setUrl(member.getUrl());
					cpsMemberListDto.setLogo(member.getLogo());
					cpsMemberList.add(cpsMemberListDto);
				});

				response.setSuccess();
			} else {
				response.setApiMessage(Constants.MEMBER_BLANK.getCode(), Constants.MEMBER_BLANK.getValue());
			}
			response.setDatas(cpsMemberList);
		} catch (Exception e) {
			response.setApiMessage(Constants.MEMBER_LIST_EXCEPTION.getCode(), Constants.MEMBER_LIST_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberList api  {}", e);
		}

		return response;
	}
}
