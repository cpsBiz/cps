package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsMemberDto;
import com.cps.cpsApi.entity.CpsAgencyEntity;
import com.cps.cpsApi.entity.CpsCampaignEntity;
import com.cps.cpsApi.entity.CpsMemberEntity;
import com.cps.cpsApi.packet.CpsMemberPacket;
import com.cps.cpsApi.repository.CpsAgencyRepository;
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

	private final CpsAgencyRepository cpsAgencyRepository;

	private final HttpService httpService;

	private final UserService userService;

	private final SearchService sarchService;

	private final CpsCampaignService cpsCampaignService;

	/**
	 * 회원 등록, 수정, 삭제
	 * @date 2024-09-05
	 */
	public GenericBaseResponse<CpsMemberDto> userSignIn(CpsMemberPacket.UserInfo.UserRequest request) throws Exception {
		CpsMemberPacket.UserInfo.Response response = new CpsMemberPacket.UserInfo.Response();
		CpsMemberEntity cpsMemberEntity = userService.cpsUser(request);
		CpsMemberDto cpsMemberDto = userService.commonMemberDto(cpsMemberEntity);
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

		return response;
	}

	/**
	 * 링크프라이스 광고주 정보
	 * @date 2024-09-05
	 */
	public GenericBaseResponse<CpsMemberDto> linkPriceUserSignIn(CpsMemberPacket.UserInfo.AgencyMemberRequest request) throws Exception {
		CpsMemberPacket.UserInfo.Response response = new CpsMemberPacket.UserInfo.Response();

		List<CpsMemberEntity> cpsMemberEntityList = new ArrayList<>();
		List<CpsAgencyEntity> cpsAgencyEntityList = new ArrayList<>();
		List<CpsCampaignEntity> cpsCampaignEntityList = new ArrayList<>();

		try {
			//링크프라이스 광고주 데이터 가져오기
			CpsMemberPacket.UserInfo.Domain linkPriceRequest = new CpsMemberPacket.UserInfo.Domain();
			linkPriceRequest.setDomain(linkPriceDomain + linkPriceEndPoint + "/all/cps/detail");
			List<CpsMemberPacket.UserInfo.LinkPriceAgencyResponse> linkPriceMerchantList = httpService.SendUserLinkPriceMerchant(linkPriceRequest);

			//CPS_MEMBER 데이터 전환
			List<CpsMemberPacket.UserInfo.UserCampaignRequest> memberRequestList = userService.linkPriceAgencyMemberList(linkPriceMerchantList);

			memberRequestList.forEach(member -> {
				cpsAgencyEntityList.add(userService.cpsAgency(member));

				//회원 정보가 있는 경우 회원테이블 수정 제외 (관리자 화면에서만 수정)
				if (null == cpsMemberRepository.findByMemberId("link_"+member.getMemberId())) {
					member.setMemberId("link_"+member.getMemberId());
					member.setMemberPw("link_"+member.getMemberId());
					member.setType("MEMBER"); member.setStatus("N");member.setBusinessType("B");
					member.setAgencyId("linkprice");
					CpsMemberEntity cpsMemberEntity =  new CpsMemberEntity();
					CpsCampaignEntity cpsCampaignEntity = new CpsCampaignEntity();
					CpsMemberPacket.UserInfo.UserCampaignRequest campaign = new CpsMemberPacket.UserInfo.UserCampaignRequest();

					try {
						cpsMemberEntity = userService.cpsUser(member);
						cpsCampaignEntity = cpsCampaignService.cpsUserCampaign(member);
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
}