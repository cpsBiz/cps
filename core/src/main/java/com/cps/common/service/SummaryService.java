package com.cps.common.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericReportPageBaseResponse;
import com.cps.common.dto.SummaryDto;
import com.cps.common.packet.SummaryPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryService {

	private final SearchService searchService;

	/**
	 * 리포트 조회
	 * @date 2024-09-19
	 */
	public GenericReportPageBaseResponse<SummaryDto> summaryCount(SummaryPacket.SummaryInfo.SummaryRequest request) throws Exception {
		SummaryPacket.SummaryInfo.SummaryResponse response = new SummaryPacket.SummaryInfo.SummaryResponse();
		try {
			//노출
			response = searchService.summarySearch(request);
			if (null == response) {
				response.setApiMessage(Constants.VIEW_BLANK.getCode(), Constants.VIEW_BLANK.getValue());
			} else {
				response.setSuccess(response.getTotalCount(), response.getCnt(), response.getClickCnt(), response.getRewardCnt(), response.getProductPrice(), response.getCommission(), response.getCommissionProfit(), response.getAffliateCommission(), response.getUserCommission());
			}
		}catch (Exception e){
			response.setApiMessage(Constants.VIEW_SEARCH_EXCEPTION.getCode(), Constants.VIEW_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + "summaryCount request : {}, exception : {}", request, e);
		}
		return response;
	}
}
