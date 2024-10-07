package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericPageBaseResponse;
import com.cps.cpsService.dto.SummaryDto;
import com.cps.cpsService.packet.SummaryPacket;
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
	public GenericPageBaseResponse<SummaryDto> summaryCount(SummaryPacket.SummaryInfo.SummaryRequest request) throws Exception {
		SummaryPacket.SummaryInfo.SummaryResponse response = new SummaryPacket.SummaryInfo.SummaryResponse();
		try {
			//노출
			response = searchService.summarySearch(request);
			if (null == response) {
				response.setApiMessage(Constants.VIEW_BLANK.getCode(), Constants.VIEW_BLANK.getValue());
			} else {
				response.setSuccess(response.getTotalCount());
			}
		}catch (Exception e){
			response.setApiMessage(Constants.VIEW_SEARCH_EXCEPTION.getCode(), Constants.VIEW_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + "summaryCount request : {}, exception : {}", request, e);
		}
		return response;
	}
}
