package com.mobcomms.hanapay.service;

import com.mobcomms.common.constant.Constant;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.hanapay.dto.UserDto;
import com.mobcomms.hanapay.dto.packet.UserPacket;
import com.mobcomms.hanapay.entity.UserEntity;
import com.mobcomms.hanapay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserRepository userRepository;

	/**
	 * 회원정보 동의여부 수정
	 * @date 2024-08-13
	 */
	public GenericBaseResponse<UserDto> postUserAgree(UserDto request) throws Exception {
		var result = new UserPacket.UpdateUserAgreeTerms.Response();

		try {
			UserEntity userEntity = userRepository.findAllByUserUuid(request.getUserKey());
			//추후 유저의 데이터가 DB 에 없는 경우 JWT, 전처리건 앞에서 처리
			if (userEntity == null) {
				result.setError(Constant.EMPTY_USER);
				return result;
			}

			userEntity.setUserUuid(request.getUserKey());
			userEntity.setUserAgreeTerms(request.getAgreeTerms());

			UserDto userDto = commonUserDto(userRepository.save(userEntity));

			result.setSuccess();
			result.setData(userDto);
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " postUserAgree  {}",e);
			result.setError(e.getMessage());
		}
		return result;
	}


	/**
	 * 회원정보 등록&수정 요청
	 * @date 2024-08-13
	 */
	public GenericBaseResponse<UserDto> postUserInfo(UserDto request) throws Exception {
		var result = new UserPacket.PostUserinfo.Response();

		try {
			UserEntity userEntity = userRepository.findAllByUserUuid(request.getUserKey());

			if (userEntity == null) {
				userEntity = new UserEntity();
				userEntity.setUserAgreeTerms("Y");
			} else {
				userEntity.setUserAgreeTerms(request.getAgreeTerms());
			}

			userEntity.setUserAdid(request.getAdId());
			userEntity.setUserUuid(request.getUserKey());
			userEntity.setUserAppOs(request.getOs());

			UserDto userDto = commonUserDto(userRepository.save(userEntity));

			result.setSuccess();
			result.setData(userDto);
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " postUserInfo  {}",e);
			result.setError(e.getMessage());
		}
		return result;
	}


	/**
	 * 회원정보조회
	 * @date 2024-08-13
	 */
	public GenericBaseResponse<UserDto> getUserInfo(UserDto request) throws Exception {
		UserPacket.Response result = new UserPacket.Response();

		try {
			UserEntity userEntity = userRepository.findAllByUserUuid(request.getUserKey());

			if (userEntity == null) {
				result.setError(Constant.EMPTY_USER);
				return result;
			}

			UserDto userDto = commonUserDto(userRepository.save(userEntity));

			result.setSuccess();
			result.setData(userDto);
		} catch (Exception e) {
			log.error(Constant.EXCEPTION_MESSAGE + " getUserInfo  {}",e);
			result.setError(e.getMessage());
		}
		return result;
	}

	public UserDto commonUserDto (UserEntity userEntity) {
		UserDto userDto = new UserDto();
		userDto.setUserKey(userEntity.getUserUuid());
		userDto.setOs(userEntity.getUserAppOs());
		userDto.setAgreeTerms(userEntity.getUserAgreeTerms());
		userDto.setAdId(userEntity.getUserAdid());
		return userDto;
	}
}
