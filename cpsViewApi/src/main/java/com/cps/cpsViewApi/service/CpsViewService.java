package com.cps.cpsViewApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.cpsViewApi.entity.CpsViewEntity;
import com.cps.cpsViewApi.packet.CpsViewPacket;
import com.cps.cpsViewApi.repository.CpsViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsViewService {

	private final CpsViewRepository cpsViewRepository;

	/**
	 * 노출 등록
	 * @date 2024-09-11
	 */
	public CpsViewPacket.ViewInfo.ViewResponse campaignView(CpsViewPacket.ViewInfo.ViewRequest request) throws Exception {
		CpsViewPacket.ViewInfo.ViewResponse response = new CpsViewPacket.ViewInfo.ViewResponse();
		try {
			List<CpsViewEntity> cpsViewEntityList = cpsViewEntityList(cpsViewRepository.findActiveCampaigns(request.getAffliateId(), request.getZoneId(), request.getSite(), request.getUserId(), request.getAdId(), request.getOs()));

			if (cpsViewEntityList.size() > 0) {
				cpsViewRepository.saveAll(cpsViewEntityList);
				response.setSuccess();
				response.setDatas(cpsViewEntityList);
			} else {
				response.setApiMessage(Constants.VIEW_BLANK.getCode(), Constants.VIEW_BLANK.getValue());
				return response;
			}
		}catch (Exception e){
			response.setApiMessage(Constants.VIEW_EXCEPTION.getCode(), Constants.VIEW_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " view api  {}", e);
		}
		return response;
	}

	public List<CpsViewEntity> cpsViewEntityList(List<Object[]> object){
		String ipAddress = "0.0.0.0";
		try{
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		}catch (Exception e){
			log.error(Constant.EXCEPTION_MESSAGE + " cpsViewEntityList  {}", e);
		}

		String finalIpAddress = ipAddress;

		return object.stream().map(result -> {
			CpsViewEntity cpsViewEntity = new CpsViewEntity();
			cpsViewEntity.setRegDay(Integer.parseInt((String) result[0]));
			cpsViewEntity.setRegHour((String) result[1]);
			cpsViewEntity.setCampaignNum(((Number) result[2]).intValue());
			cpsViewEntity.setCampaignName((String) result[3]);
			cpsViewEntity.setAgencyId((String) result[4]);
			cpsViewEntity.setMemberId((String) result[5]);
			cpsViewEntity.setClickUrl((String) result[6]);
			cpsViewEntity.setType((String) result[7]);
			cpsViewEntity.setAffliateId((String) result[8]);
			cpsViewEntity.setZoneId((String) result[9]);
			cpsViewEntity.setSite((String) result[10]);
			cpsViewEntity.setUserId((String) result[11]);
			cpsViewEntity.setAdId((String) result[12]);
			cpsViewEntity.setOs(Character.toString((Character) result[13]));
			cpsViewEntity.setIpAddress(finalIpAddress);
			return cpsViewEntity;
		}).collect(Collectors.toList());
	}
}
