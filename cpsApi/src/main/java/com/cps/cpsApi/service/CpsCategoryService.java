package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsCategoryDto;
import com.cps.cpsApi.entity.CpsCategoryEntity;
import com.cps.cpsApi.packet.CpsCategoryPacket;
import com.cps.cpsApi.repository.CpsCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsCategoryService {
	private final CpsCategoryRepository cpsCategoryRepository;


	/**
	 * 카테고리 등록
	 * @date 2024-09-03
	 */
	public GenericBaseResponse<CpsCategoryDto> category(CpsCategoryPacket.CategoryInfo.CategoryRequest request) throws Exception {
		CpsCategoryPacket.CategoryInfo.Response response = new CpsCategoryPacket.CategoryInfo.Response();
		CpsCategoryEntity cpsCategoryEntity = new CpsCategoryEntity();
		CpsCategoryDto cpsCategoryDto = new CpsCategoryDto();
		try {
			cpsCategoryEntity.setCategory(request.getCategory());
			cpsCategoryEntity.setCategoryName(request.getCategoryName());

			if(request.getApiType().equals("D")){
				cpsCategoryRepository.deleteByCategory(cpsCategoryEntity.getCategory());
			} else {
				if (request.getCategoryRank() > 0) {
					cpsCategoryEntity.setCategoryRank(request.getCategoryRank());
				} else {
					cpsCategoryEntity.setCategoryRank(999);
				}
				cpsCategoryRepository.save(cpsCategoryEntity);
			}

			cpsCategoryDto.setCategory(cpsCategoryEntity.getCategory());
			cpsCategoryDto.setCategoryName(cpsCategoryEntity.getCategoryName());

			response.setSuccess();
			response.setData(cpsCategoryDto);
		} catch (Exception e) {
			response.setApiMessage(Constants.CATEGORY_EXCEPTION.getCode(), Constants.CATEGORY_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + "category api request : {}, exception : {}", request, e);
		}

		return response;
	}
}
