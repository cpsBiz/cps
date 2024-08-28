package com.mobcomms.finnq.service;

import com.mobcomms.common.constant.Constant;
import com.mobcomms.common.constant.ConstantMoneyBox;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.common.utils.DateTime;
import com.mobcomms.finnq.dto.PointBoxDto;
import com.mobcomms.finnq.dto.PointDto;
import com.mobcomms.finnq.dto.PointInfoDto;
import com.mobcomms.finnq.dto.packet.FinnqPacket;
import com.mobcomms.finnq.dto.packet.PointPacket;
import com.mobcomms.finnq.entity.PointEntity;
import com.mobcomms.finnq.entity.UserEntity;
import com.mobcomms.finnq.repository.PointRepository;
import com.mobcomms.finnq.repository.PointSettingRepository;
import com.mobcomms.finnq.repository.UserRepository;
import com.mobcomms.finnq.util.HmacSHA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


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
	@Value("${finnqDomain.hmacKey}") String hmac;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PointRepository pointRepository;
	private final PointSettingRepository pointSettingRepository;
	private final UserRepository userRepository;
	private final FinnqHttpService finnqHttpService;

	/**
	 * 포인트 적립 내역 조회
	 * @date 2024-08-26
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

			String regDateNum = DateTime.getCurrDate();

			List<PointEntity> pointList = pointRepository.findAllByUserIdAndRegDateNumAndCode(request.getUserKey(), regDateNum, "0000");

			IntStream.range(1, 4).forEach(i -> {
				PointBoxDto pointBoxDto = new PointBoxDto();
				pointBoxDto.setAdName("box_" + i);
				pointBoxDto.setPoint(0);
				pointList.stream().filter(point -> pointBoxDto.getAdName().equals(point.getBox()))
						.findFirst()
						.ifPresent(point -> {pointBoxDto.setPoint(point.getPoint());
						});
				returnPointList.add(pointBoxDto);
			});

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
	 * @date 2024-08-26
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
	 * @date 2024-08-26
	 */
	public GenericBaseResponse<PointBoxDto> postPoint(PointDto request) throws Exception {
		PointPacket.Response result = new PointPacket.Response();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		UUID u = UUID.randomUUID();

		String regDateNum = DateTime.getCurrDate();
		String os = "ADID";
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

			if (!"finnqbox1aos".equals(request.getAdId()) && !"finnqbox2aos".equals(request.getAdId()) && !"finnqbox1ios".equals(request.getAdId()) && !"finnqbox2ios".equals(request.getAdId())) {
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

				if (userEntity.getUserAppOs().equals("ios")) {
					os = "IDFA";
				}

				pointEntity.setPoint(pointSettingRepository.findAllByType(Integer.parseInt(pointEntity.getType())).getPoint()) ;

				/* 포인트 정보 조회 */
				List<PointEntity> pointList = pointRepository.findAllByUserIdAndRegDateNumAndCode(request.getUserKey(), regDateNum, "0000");
				if (pointList.size() > 0) {
					if (pointList.stream().filter(point -> pointEntity.getBox().equals(point.getBox())
							&& "0000".equals(point.getCode())).collect(Collectors.toList()).size() > 0) {
						//동일 한 박스이고 이미 적립된 포인트 요청 시
						result.setApiMessage(Constant.RESULT_CODE_ERROR_BIZ,ConstantMoneyBox.DUPLICATION_BOX);
						return result;
					} else if ((pointList.stream().filter(point -> point.getCode().equals("0000")).collect(Collectors.toList()).size()) > 2) {
						//일 적립 3회 초과
						result.setApiMessage(Constant.RESULT_CODE_ERROR_BIZ,ConstantMoneyBox.OVER_POINT);
						return result;
					}
				}

				FinnqPacket.GetFinnqInfo.Request finnqGetPacket = FinnqPacket.GetFinnqInfo.Request.builder()
						.trsnKey("P" + String.valueOf(pointEntity.getPointId()))
						.alinCd("TES1000")
						.userId(pointEntity.getUserId())
						.amt(pointEntity.getPoint())
						.adId(os)
						.adCode("")
						.adTitle("")
						.adInfo("")
						.hmac(HmacSHA.hmacKey("P" + String.valueOf(pointEntity.getPointId()), "TES1000", pointEntity.getUserId(), String.valueOf(pointEntity.getPoint())))
						.build();

				FinnqPacket.GetFinnqInfo.Response response = finnqHttpService.SendFinnq(finnqGetPacket); //핀크 api 통신

				if (null != response.getRsltCd()) {
					//적립 성공인 경우 박스명, 포인트 데이터를 넣는다
					if (response.getRsltCd().equals(ConstantMoneyBox.FINNQ_COMMON_RESPONSE_SUCCESS)) {
						pointBoxDto.setPoint(pointEntity.getPoint());
						pointBoxDto.setAdName(pointEntity.getBox());
						result.setData(pointBoxDto);
						result.setSuccess();
					}else{
						result.setApiMessage(Constant.RESULT_CODE_ERROR_EXTERNAL_API,responseMsg(response.getRsltCd()));
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

	public String responseMsg(String code){
		switch (code){
			case "00":
				return "거래성공";
			case "01":
				return "Parameter 값 오류";
			case "02":
				return "비정상 회원";
			case "05":
				return "핀크식별키(회원정보) 오류";
			case "11":
				return "트랜잭션키 중복거래 요청";
			case "12":
				return "적립오류";
			case "13":
				return "데이터 유효성 검증 오류";
			case "14":
				return "원거래 정보 없음";
			case "15":
				return "취소 거래 금액";
			case "16":
				return "기 취소 거래 요청";
			case "17":
				return "원거래 적립 실패거래 취소 요청";
			default:
				return "기타오류";
		}
	}

	/**
	 * 통신 오류 체크
	 * @date 2024-08-26
	 */
	public void updateZero() throws Exception {
		pointRepository.updateCodeAndModDateAndUniqueInsertByStatusDttm(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),"3007");
	}

}
