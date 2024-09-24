package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.SummaryDto;
import com.cps.cpsApi.packet.SummaryPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryService {

	private final SearchService searchService;

	/**
	 * 리포트 조회
	 * @date 2024-09-19
	 */
	public GenericBaseResponse<SummaryDto> summaryCount(SummaryPacket.SummaryInfo.SummaryRequest request) throws Exception {
		SummaryPacket.SummaryInfo.SummaryResponse response = new SummaryPacket.SummaryInfo.SummaryResponse();

		if (null == request.getSubSearchType() || request.getSubSearchType().equals("")) {request.setSubSearchType(request.getSearchType());}

		try {
			//노출
			List<SummaryDto> summaryDayList = searchService.summarySearch(request);
			if (summaryDayList.size() > 0) {
				response.setSuccess();
				response.setDatas(summaryDayList);
			} else {
				response.setApiMessage(Constants.VIEW_BLANK.getCode(), Constants.VIEW_BLANK.getValue());
			}
		}catch (Exception e){
			response.setApiMessage(Constants.VIEW_SEARCH_EXCEPTION.getCode(), Constants.VIEW_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + "summaryCount request : {}, exception : {}", request, e);
		}
		return response;
	}
}
