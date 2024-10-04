package com.cps.viewService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.viewService.dto.CpsMemberCommissionDto;
import com.cps.viewService.dto.CpsMemberCommissionListDto;
import com.cps.viewService.packet.CpsMemberCommissionPacket;
import com.cps.viewService.repository.CpsMemberCommissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsMemberCommissionService {

	private final CpsMemberCommissionRepository cpsMemberCommissionRepository;

	/**
	 * 회원 적립금 조회
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsMemberCommissionDto> memberCommission(CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionRequest request) throws Exception {
		CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionResponse response = new CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionResponse();

		try {
			CpsMemberCommissionDto result = cpsMemberCommissionRepository.findRewardsByUserId(request.getUserId(), request.getAffliateId());

			if (null != result) {
				response.setSuccess();
				response.setData(result);
			}else{
				response.setApiMessage(Constants.MEMBER_COMMISSION_BLANK.getCode(), Constants.MEMBER_COMMISSION_BLANK.getValue());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.MEMBER_COMMISSION_SEARCH_EXCEPTION.getCode(), Constants.MEMBER_COMMISSION_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberCommission api request : {}, exception :  {}", request, e);
		}
		return response;
	}

	/**
	 * 회원 적립금 조회
	 * @date 2024-10-02
	 */
	public GenericBaseResponse<CpsMemberCommissionListDto.MemberCommissionList> memberCommissionList(CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListRequest request) throws Exception {
		CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListResponse response = new CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListResponse();
		List<CpsMemberCommissionListDto.MemberCommissionList> cpsMemberCommissionList = new ArrayList<>();
		List<Integer> statusList = new ArrayList<>();

		try {

			if (request.getStatus() == 100) {
				statusList.add(request.getStatus());
				statusList.add(200);
			} else if (request.getStatus() == 0) { //전체
				statusList.add(100);
				statusList.add(200);
				statusList.add(210);
				statusList.add(310);
			} else {
				statusList.add(request.getStatus());
			}

			List<Object[]> result = cpsMemberCommissionRepository.findRewardsByUserIdAndRegYm(request.getUserId(), request.getRegYm(), request.getAffliateId(), statusList);

			if (result.size() > 0) {
				cpsMemberCommissionList = cpsMemberCommissionList(result);
				response.setSuccess();
				response.setDatas(cpsMemberCommissionList);
			}else{
				response.setApiMessage(Constants.MEMBER_COMMISSION_BLANK.getCode(), Constants.MEMBER_COMMISSION_BLANK.getValue());
			}
		}catch (Exception e){
			response.setApiMessage(Constants.MEMBER_COMMISSION_SEARCH_EXCEPTION.getCode(), Constants.MEMBER_COMMISSION_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " memberCommissionList api request : {}, exception :  {}", request, e);
		}
		return response;
	}

	public List<CpsMemberCommissionListDto.MemberCommissionList> cpsMemberCommissionList (List<Object[]> memberCommissionList){
		List<CpsMemberCommissionListDto.MemberCommissionList> cpsMemberCommissionList = new ArrayList<>();
		memberCommissionList.forEach(commission ->{
			CpsMemberCommissionListDto.MemberCommissionList cpsMemberCommissionDto = new CpsMemberCommissionListDto.MemberCommissionList();
			cpsMemberCommissionDto.setUserId((String) commission[0]);
			cpsMemberCommissionDto.setRegDay((Integer) commission[1]);
			cpsMemberCommissionDto.setRegYm((Integer) commission[2]);
			cpsMemberCommissionDto.setCampaignName((String) commission[3]);
			cpsMemberCommissionDto.setProductName((String) commission[4]);
			cpsMemberCommissionDto.setProductPrice((Integer) commission[5]);
			cpsMemberCommissionDto.setUserCommission((Integer) commission[6]);
			cpsMemberCommissionDto.setProductCnt((Integer) commission[7]);
			cpsMemberCommissionDto.setMemberId((String) commission[8]);
			cpsMemberCommissionDto.setStatus((Integer) commission[9]);
			cpsMemberCommissionDto.setCommissionPaymentStandard((String) commission[10]);
			cpsMemberCommissionList.add(cpsMemberCommissionDto);
		});

		return cpsMemberCommissionList;
	}
}
