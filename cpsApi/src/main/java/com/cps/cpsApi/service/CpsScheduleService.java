package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsViewrDto;
import com.cps.cpsApi.entity.CpsViewEntity;
import com.cps.cpsApi.packet.CpsViewSchedulePacket;
import com.cps.cpsApi.repository.CpsViewScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsScheduleService {

	private final CpsViewScheduleRepository cpsViewScheduleRepository;

	/**
	 * 노출 통계
	 * @date 2024-09-11
	 */
	public GenericBaseResponse<CpsViewrDto> summarySchedule(CpsViewSchedulePacket.ScheduleInfo.ViewScheduleRequest request) throws Exception {
		CpsViewSchedulePacket.ScheduleInfo.ViewSchduelResponse response = new CpsViewSchedulePacket.ScheduleInfo.ViewSchduelResponse();
		try {
			//노출 전체 데이터 시간별 합계
			cpsViewScheduleRepository.insertSummaryViewHour(request.getMinute());
			//노출 전체 데이터 일별 합계
			cpsViewScheduleRepository.insertSummaryViewDay(request.getMinute());
			//클릭 전체 데이터 시간별 합계
			cpsViewScheduleRepository.insertSummaryClickHour(request.getMinute());
			//클릭 전체 데이터 일별 합계
			cpsViewScheduleRepository.insertSummaryClickDay(request.getMinute());
		} catch (Exception e) {
			response.setApiMessage(Constants.STAT_HOUR_EXCEPTION.getCode(), Constants.STAT_HOUR_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " summarySchedule service  {}", e);
		}
		response.setDatas(null);
		return response;
	}








	public List<CpsViewrDto> commonViewDto(List<CpsViewEntity> cpsViewEntityList){
		List<CpsViewrDto> cpsViewrDtoList = new ArrayList<>();
		cpsViewEntityList.forEach(view -> {
			CpsViewrDto cpsViewrDto = new CpsViewrDto();
			cpsViewrDto.setRegDay(view.getRegDay());
			cpsViewrDto.setRegHour(view.getRegHour());
			cpsViewrDto.setCampaignNum(view.getCampaignNum());
			cpsViewrDto.setCampaignName(view.getCampaignName());
			cpsViewrDto.setAffliateId(view.getAffliateId());
			cpsViewrDto.setZoneId(view.getZoneId());
			cpsViewrDto.setAgencyId(view.getAgencyId());
			cpsViewrDto.setMemberId(view.getMemberId());
			cpsViewrDto.setType(view.getType());
			cpsViewrDto.setSite(view.getSite());
			cpsViewrDto.setUserId(view.getUserId());
			cpsViewrDto.setOs(view.getOs());
			cpsViewrDto.setAdId(view.getAdId());
			cpsViewrDtoList.add(cpsViewrDto);
		});
		return cpsViewrDtoList;
	}
}
