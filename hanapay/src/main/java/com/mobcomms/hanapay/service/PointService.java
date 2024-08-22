package com.mobcomms.hanapay.service;

import com.mobcomms.common.constant.Constant;
import com.mobcomms.common.constant.ConstantMoneyBox;
import com.mobcomms.common.constant.ResultCode;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.common.utils.DateTime;
import com.mobcomms.hanapay.dto.PointBoxDto;
import com.mobcomms.hanapay.dto.PointDto;
import com.mobcomms.hanapay.dto.PointInfoDto;
import com.mobcomms.hanapay.dto.packet.HanaPacket;
import com.mobcomms.hanapay.dto.packet.PointPacket;
import com.mobcomms.hanapay.entity.PointEntity;
import com.mobcomms.hanapay.entity.UserEntity;
import com.mobcomms.hanapay.repository.PointRepository;
import com.mobcomms.hanapay.repository.PointSettingRepository;
import com.mobcomms.hanapay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.net.InetAddress;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PointRepository pointRepository;
	private final PointSettingRepository pointSettingRepository;
	private final UserRepository userRepository;
	private final HanaHttpService hanaHttpService;

	/**
	 * 포인트 적립 내역 조회
	 * @date 2024-08-13
	 */
	public GenericBaseResponse<PointBoxDto> getPoint(PointDto request) throws Exception {
		PointPacket.Response result = new PointPacket.Response();

		try {
			UserEntity userEntity = userRepository.findAllByUserUuid(request.getUserKey());

			if (userEntity == null) {
				result.setError(Constant.EMPTY_USER);
				return result;
			}

			List<PointBoxDto> returnPointList = new ArrayList<>();

			IntStream.range(1, 4).forEach(i -> {
				PointBoxDto pointBoxDto = new PointBoxDto();
				pointBoxDto.setPoint("0");
				pointBoxDto.setAdName("box_" + i);
				returnPointList.add(pointBoxDto);
			});

			int i = 0;
			String regDateNum = DateTime.getCurrDate();

			List<PointEntity> pointList = pointRepository.findAllByUserIdAndRegDateNum(request.getUserKey(), regDateNum);

			for (PointEntity pointEntity : pointList) {
				if (pointEntity.getBox().equals(returnPointList.get(i).getAdName())) {
					returnPointList.get(i).setPoint(pointEntity.getPoint());
				}
				i++;
			}

			result.setSuccess();
			result.setDatas(returnPointList);
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " getPoint  {}",e);
			result.setError(e.getMessage());
		}
		return result;
	}

	/**
	 * 포인트 정보 조회
	 * @date 2024-08-13
	 */
	public GenericBaseResponse<PointInfoDto> getPointInfo() throws Exception {
		PointPacket.GetPointSetting.Response result = new PointPacket.GetPointSetting.Response();

		try {
			List<PointInfoDto> pointInfoList = pointSettingRepository.findAllByUseYN("Y").stream()
					.map(point -> {
						PointInfoDto pointInfoDto = new PointInfoDto();
						pointInfoDto.setUnit(point.getUnit());
						pointInfoDto.setPoint(point.getPoint());
						pointInfoDto.setName(point.getName());
						pointInfoDto.setType(point.getType());
						pointInfoDto.setUseYn(point.getUseYN());
						return pointInfoDto;
					}).collect(Collectors.toList());

			result.setSuccess();
			result.setDatas(pointInfoList);
		} catch (Exception e){
			log.error(Constant.EXCEPTION_MESSAGE + " getPointInfo  {}",e);
			result.setError(e.getMessage());
		}
		return result;
	}

	/**
	 * 포인트 적립 요청
	 * @date 2024-08-13
	 */
	public GenericBaseResponse<PointBoxDto> postPoint(PointDto request) throws Exception {
		PointPacket.Response result = new PointPacket.Response();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		UUID u = UUID.randomUUID();

		String regDateNum = DateTime.getCurrDate();
		PointEntity pointEntity = new PointEntity();
		PointBoxDto pointBoxDto = new PointBoxDto();

		try {
			pointEntity.setUserId(request.getUserKey());
			pointEntity.setRegDateNum(regDateNum);
			pointEntity.setBox(request.getAdName());
			pointEntity.setZone(request.getAdId());
			pointEntity.setType("2");
			pointEntity.setCode("0");
			pointEntity.setUniqueInsert("0");
			pointEntity.setIpAddress(ipAddress);
			pointEntity.setRegDateNum(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

			if (!"hanacardADAPIaos".equals(request.getAdId()) && !"hanacardADAPIios".equals(request.getAdId()) && !"houseIos".equals(request.getAdId()) && !"houseAos".equals(request.getAdId())) {
				pointEntity.setType("1");
			}

			/*포인트 적립 내역 등록*/
			PointEntity saveChk = pointRepository.save(pointEntity);

			if (null == saveChk) {
				result.setApiMessage(Constant.RESULT_CODE_ERROR_BIZ,ConstantMoneyBox.INSERT_FAIL);
				return result;
			} else {
				/* 지정된 박스 명칭이 아닌 경우 */
				if (!pointEntity.getBox().equals("box_1") && !pointEntity.getBox().equals("box_2") && !pointEntity.getBox().equals("box_3")) {
					result.setApiMessage(Constant.RESULT_CODE_ERROR_BIZ,ConstantMoneyBox.INVALID_BOX);
					return result;
				}

				/* 회원 정보 조회 */
				UserEntity userEntity = userRepository.findAllByUserUuid(request.getUserKey());
				if (userEntity == null) {
					result.setError(Constant.EMPTY_USER);
					return result;
				}

				pointEntity.setPoint(String.valueOf(pointSettingRepository.findAllByType(Integer.parseInt(pointEntity.getType())).getPoint())) ;

				/* 포인트 정보 조회 */
				List<PointEntity> pointList = pointRepository.findAllByUserIdAndRegDateNumAndCode(request.getUserKey(), regDateNum, "1");
				if (pointList.size() > 0) {
					if (pointList.stream().filter(point -> pointEntity.getBox().equals(point.getBox())
							&& "1".equals(point.getCode())).collect(Collectors.toList()).size() > 0) {
						//동일 한 박스이고 이미 적립된 포인트 요청 시
						result.setApiMessage(Constant.RESULT_CODE_ERROR_BIZ,ConstantMoneyBox.DUPLICATION_BOX);
						return result;
					} else if ((pointList.stream().filter(point -> point.getCode().equals("1")).collect(Collectors.toList()).size()) > 3) {
						//일 적립 3회 초과
						result.setApiMessage(Constant.RESULT_CODE_ERROR_BIZ,ConstantMoneyBox.OVER_POINT);
						return result;
					}
				}

				HanaPacket.GetHanaInfo.Request hanaGetPacket = new HanaPacket.GetHanaInfo.Request();
				hanaGetPacket.setAdv_enpc("Adv_enpc");
				hanaGetPacket.setAdv_enc_nm("mobcomms");
				hanaGetPacket.setUsn(pointEntity.getUserId());
				hanaGetPacket.setQuantity(pointEntity.getPoint());
				hanaGetPacket.setCampaign_key("point_box");
				hanaGetPacket.setReward_key("P" + String.valueOf(pointEntity.getPointId()));

				HanaPacket.GetHanaInfo.Response response = hanaHttpService.SendHana(hanaGetPacket); //하나 api 통신

				if (null != response.getData().getResultList().get(0).getResultCode()) {
					//적립 성공인 경우 박스명, 포인트 데이터를 넣는다
					if (response.getData().getResultList().get(0).getResultCode().equals(ConstantMoneyBox.HANAPAY_COMMON_RESPONSE_SUCCESS)) {
						pointBoxDto.setPoint(pointEntity.getPoint());
						pointBoxDto.setAdName(pointEntity.getBox());
						result.setData(pointBoxDto);
						result.setSuccess();
					}else{
						result.setApiMessage(Constant.RESULT_CODE_ERROR_EXTERNAL_API,response.getData().getResultList().get(0).getResultMsg());
					}
				} else {
					result.setApiMessage(Constant.RESULT_CODE_ERROR_BIZ,ConstantMoneyBox.EMPTY_DATA);
				}
				pointEntity.setUniqueInsert(u.toString());
			}
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " postPoint  {}",e);
			result.setError(e.getMessage());
		} finally {
			if(pointEntity.getPointId() > 0){
				pointEntity.setCode(result.getResultCode());
				pointEntity.setRes(result.getResultMessage());
				pointRepository.save(pointEntity);
			}
		}

		return result;
	}
}
