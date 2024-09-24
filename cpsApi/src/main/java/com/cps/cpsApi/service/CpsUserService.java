package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsUserDto;
import com.cps.cpsApi.entity.CpsAgencyEntity;
import com.cps.cpsApi.entity.CpsCampaignEntity;
import com.cps.cpsApi.entity.CpsUserEntity;
import com.cps.cpsApi.packet.CpsUserPacket;
import com.cps.cpsApi.repository.CpsAgencyRepository;
import com.cps.cpsApi.repository.CpsCampaignRepository;
import com.cps.cpsApi.repository.CpsUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsUserService {
	@Value("${linkPrice.domain.url}") String linkPriceDomain;

	@Value("${linkPrice.reco.url}") String linkPriceEndPoint;

	private final CpsUserRepository cpsUserRepository;

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
	public GenericBaseResponse<CpsUserDto> userSignIn(CpsUserPacket.UserInfo.UserRequest request) throws Exception {
		CpsUserPacket.UserInfo.Response response = new CpsUserPacket.UserInfo.Response();
		CpsUserEntity cpsUserEntity = userService.cpsUser(request);
		CpsUserDto cpsUserDto = userService.commonMemberDto(cpsUserEntity);
		CpsUserEntity memberCheck = cpsUserRepository.findByMemberId(request.getMemberId());

		if (request.getApiType().equals("I")) {
			try {
				if (null == memberCheck) {
					//회원정보 테이블 등록
					cpsUserRepository.save(cpsUserEntity);
					response.setSuccess();
				} else {
					response.setApiMessage(Constants.MEMBER_DUPLICATION.getCode(), Constants.MEMBER_DUPLICATION.getValue());
					return response;
				}
				response.setData(cpsUserDto);
			} catch (Exception e) {
				response.setApiMessage(Constants.MEMBER_EXCEPTION.getCode(), Constants.MEMBER_EXCEPTION.getValue());
				log.error(Constant.EXCEPTION_MESSAGE + "memberSignIn request : {}, exception : {}", request, e);
			}
		} else if (null != memberCheck) {

			if (request.getApiType().equals("D")) {
				try {
					cpsUserRepository.deleteByMemberId(request.getMemberId());
					response.setSuccess();
					response.setData(cpsUserDto);
				} catch (Exception e) {
					response.setApiMessage(Constants.MEMBER_DELETE_EXCEPTION.getCode(), Constants.MEMBER_DELETE_EXCEPTION.getValue());
					log.error(Constant.EXCEPTION_MESSAGE + "memberDelete request : {}, exception : {}", request, e);
				}
			} else if (request.getApiType().equals("U")) {
				try {
					//회원정보 테이블 수정
					cpsUserDto = userService.commonMemberDto(cpsUserRepository.save(cpsUserEntity));

					response.setSuccess();
					response.setData(cpsUserDto);
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
	public GenericBaseResponse<CpsUserDto> linkPriceUserSignIn(CpsUserPacket.UserInfo.AgencyMemberRequest request) throws Exception {
		CpsUserPacket.UserInfo.Response response = new CpsUserPacket.UserInfo.Response();

		List<CpsUserEntity> cpsUserEntityList = new ArrayList<>();
		List<CpsAgencyEntity> cpsAgencyEntityList = new ArrayList<>();
		List<CpsCampaignEntity> cpsCampaignEntityList = new ArrayList<>();

		try {
			//링크프라이스 광고주 데이터 가져오기
			CpsUserPacket.UserInfo.Domain linkPriceRequest = new CpsUserPacket.UserInfo.Domain();
			linkPriceRequest.setDomain(linkPriceDomain + linkPriceEndPoint + "/all/cps/detail");
			List<CpsUserPacket.UserInfo.LinkPriceAgencyResponse> linkPriceMerchantList = httpService.SendUserLinkPriceMerchant(linkPriceRequest);

			//CPS_USER 데이터 전환
			List<CpsUserPacket.UserInfo.UserCampaignRequest> memberRequestList = userService.linkPriceAgencyMemberList(linkPriceMerchantList);

			memberRequestList.forEach(member -> {
				cpsAgencyEntityList.add(userService.cpsAgency(member));

				//회원 정보가 있는 경우 회원테이블 수정 제외 (관리자 화면에서만 수정)
				if (null == cpsUserRepository.findByMemberId("link_"+member.getMemberId())) {
					member.setMemberId("link_"+member.getMemberId());
					member.setMemberPw("link_"+member.getMemberId());
					member.setType("MEMBER"); member.setStatus("N");member.setBusinessType("B");
					member.setAgencyId("linkprice");
					CpsUserEntity cpsUserEntity =  new CpsUserEntity();
					CpsCampaignEntity cpsCampaignEntity = new CpsCampaignEntity();
					CpsUserPacket.UserInfo.UserCampaignRequest campaign = new CpsUserPacket.UserInfo.UserCampaignRequest();

					try {
						cpsUserEntity = userService.cpsUser(member);
						cpsCampaignEntity = cpsCampaignService.cpsUserCampaign(member);
					} catch (Exception e) {
						e.printStackTrace();
					}
					cpsUserEntityList.add(cpsUserEntity);
					cpsCampaignEntityList.add(cpsCampaignEntity);
				}
			});

			cpsAgencyRepository.saveAll(cpsAgencyEntityList);

			//회원, 캠페인 자동 등록
			if (cpsUserEntityList.size() > 0) {
				cpsUserRepository.saveAll(cpsUserEntityList);
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
