package com.cps.finnq.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.ResultCode;
import com.cps.common.model.GenericBaseResponse;
import com.cps.common.utils.DateTime;
import com.cps.common.utils.IPAdressUtil;
import com.cps.finnq.entity.OfferwallMediaEntity;
import com.cps.finnq.repository.OfferwallMediaRepository;
import com.cps.finnq.repository.OfferwallRepository;
import com.cps.finnq.repository.UserRepository;
import com.cps.finnq.dto.OfferwallDto;
import com.cps.finnq.dto.OfferwallInfoDto;
import com.cps.finnq.dto.packet.FinnqPacket;
import com.cps.finnq.dto.packet.OfferwallPacket;
import com.cps.finnq.entity.OfferwallEntity;
import com.cps.finnq.entity.UserEntity;
import com.cps.finnq.util.HmacSHA;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferwallService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final OfferwallMediaRepository offerwallMediaRepository;
	private final UserRepository userRepository;
	private final OfferwallRepository offerwallRepository;
	private final FinnqHttpService finnqHttpService;

	private final String ALINCD = "ANIC100";

	//private final String ANICK_ADINFO = "quiz";

	/**
	 * 오퍼월 포미션 url 조회
	 * @date 2024-08-19
	 */
	public GenericBaseResponse<OfferwallInfoDto> getPomission() throws Exception {
		OfferwallPacket.GetOfferwall.Response result = new OfferwallPacket.GetOfferwall.Response();

		try {
			OfferwallInfoDto offerwallInfoDto = new OfferwallInfoDto();
			OfferwallMediaEntity offerwallMediaEntity = offerwallMediaRepository.findAllByCode("1");
			offerwallInfoDto.setUrl(offerwallMediaEntity.getUrl());

			result.setSuccess();
			result.setData(offerwallInfoDto);
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " getPomission  {}",e);
			result.setError(e.getMessage());
		}
		return result;
	}


	/**
	 * 오퍼월 포미션 적립
	 * @date 2024-08-19
	 */
	public OfferwallPacket.PostOfferwall.Response postPomission(OfferwallDto request) throws Exception {
		HttpServletRequest httpRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

		OfferwallPacket.PostOfferwall.Response result = new OfferwallPacket.PostOfferwall.Response();

		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();

		String regDateNum = DateTime.getCurrDate();
		OfferwallEntity offerwallEntity = new OfferwallEntity();


		try{
			offerwallEntity.setUserId(request.getUserKey());
			offerwallEntity.setRegDateNum(regDateNum);
			offerwallEntity.setAdId(request.getParticipationSeq());
			offerwallEntity.setMedia("1");
			offerwallEntity.setCode("0");
			offerwallEntity.setAdType(request.getMissionType());
			offerwallEntity.setAdName(request.getAdverName());
			offerwallEntity.setPoint(request.getUserPoint());
			offerwallEntity.setIpAddress(ipAddress);

			double exchange = request.getUserPoint() / 1.4;
			double profit = request.getUserPoint() - exchange;
			offerwallEntity.setPoint(request.getUserPoint());
			offerwallEntity.setExchange(exchange);
			offerwallEntity.setProfit(profit);
			offerwallEntity.setCode("0");
			OfferwallEntity saveChk = offerwallRepository.save(offerwallEntity);

			/* 접근 허용 IP 확인 :: 각 매체별 접근이 허용된 IP 인지 확인 */
			if (!accessAllowCheck(httpRequest, offerwallEntity.getMedia())) {
				logger.info("접근 불가");
				result.setResult(false);
				result.setResultCode(5002);
				result.setResultMsg("접근 불가");
				return result;
			}

			/* 회원 정보 조회 */
			UserEntity userEntity = userRepository.findAllByUserUuid(offerwallEntity.getUserId());

			if (userEntity == null) {
				result.setResult(false);
				result.setResultCode(2001);
				result.setResultMsg(ResultCode.RESULT_MSG_2001);
				return result;
			}



			if (null == saveChk) {
				result.setResult(false);
				result.setResultCode(5001);
				result.setResultMsg(ResultCode.RESULT_MSG_5001);
				return result;
			} else {
				long offerwall = offerwallRepository.countByUserIdAndMediaAndAdIdAndCode(offerwallEntity.getUserId(), offerwallEntity.getMedia(), offerwallEntity.getAdId(), "1");

				if (offerwall > 0) {
					logger.info("리워드중복지급");
					/* 중복 참여 에러 */
					result.setResult(false);
					result.setResultCode(5003);
					result.setResultMsg(ResultCode.RESULT_MSG_5003);
					return result;
				}

				FinnqPacket.GetFinnqInfo.Request finnqGetPacket = FinnqPacket.GetFinnqInfo.Request.builder()
						.trsnKey("O"+String.valueOf(offerwallEntity.getOfferwallId()))
						.alinCd(ALINCD)
						.userId(offerwallEntity.getUserId())
						.amt(offerwallEntity.getPoint())
						.adId(userEntity.getUserAdid())
						.adCode("")
						.adTitle("")
						.adInfo(request.getAdInfo())
						.hmac(HmacSHA.hmacKey("O"+String.valueOf(offerwallEntity.getOfferwallId()), "",offerwallEntity.getUserId(), String.valueOf(offerwallEntity.getPoint())))
						.build();

				FinnqPacket.GetFinnqInfo.Response response = finnqHttpService.SendFinnq(finnqGetPacket); //하나 api 통신

				if (null != response.getRsltCd()) {
					result.setResult(true);
					result.setResultCode(0);
					result.setResultMsg(ResultCode.RESULT_MSG_0);
				} else {
					result.setResult(false);
					result.setResultCode(5004);
					result.setResultMsg(ResultCode.RESULT_MSG_5004);
				}
			}
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " postPomission  {}",e);
			result.setResult(false);
			result.setResultCode(9999);
			result.setResultMsg(ResultCode.RESULT_MSG_9999);
		} finally {
			if (offerwallEntity.getOfferwallId() > 0) {
				offerwallEntity.setCode(result.getResultCode()+"");
				offerwallEntity.setRes(result.getResultMsg());
				offerwallRepository.save(offerwallEntity);
			}
		}
		return result;
	}

	/**
	 * 접근 허용 IP 확인
	 * @date 2024-08-19
	 */
	public boolean accessAllowCheck(HttpServletRequest request, String media) throws Exception {
		/* 변수 선언 */
		String ip			= IPAdressUtil.getIpAdress(request);
		String[] allow_ip	= {};

		logger.info("Request IP: "+ip);

		/* 오퍼월 매체 정보 조회 :: 접근 가능한 IP 조회 */
		OfferwallMediaEntity offerwallMediaEntity = offerwallMediaRepository.findAllByCode("1");
		allow_ip = String.valueOf(offerwallMediaEntity.getAllowIp()).split("\\|");

		/* 허용 가능한 IP 인지 확인 */
		if( !Arrays.asList(allow_ip).contains(ip) ) {
			return false;
		} else {
			return true;
		}
	}



}