package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CategoryDto;
import com.cps.cpsApi.entity.CategoryEntity;
import com.cps.cpsApi.packet.CategoryPacket;
import com.cps.cpsApi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;


	/**
	 * 카테고리 등록
	 * @date 2024-09-03
	 */
	public GenericBaseResponse<CategoryDto> category(CategoryPacket.CategoryInfo.CategoryRequest request) throws Exception {
		CategoryPacket.CategoryInfo.Response response = new CategoryPacket.CategoryInfo.Response();
		CategoryEntity categoryEntity = new CategoryEntity();
		CategoryDto categoryDto = new CategoryDto();
		try {
			categoryEntity.setCategory(request.getCategory());
			categoryEntity.setCategoryName(request.getCategoryName());

			if(request.getApiType().equals("D")){
				categoryRepository.deleteByCategory(categoryEntity.getCategory());
			} else {
				if (request.getCategoryRank() > 0) {
					categoryEntity.setCategoryRank(request.getCategoryRank());
				} else {
					categoryEntity.setCategoryRank(999);
				}
				categoryRepository.save(categoryEntity);
			}

			categoryDto.setCategory(categoryEntity.getCategory());
			categoryDto.setCategoryName(categoryEntity.getCategoryName());

			response.setSuccess();
			response.setData(categoryDto);
		} catch (Exception e) {
			response.setApiMessage(Constants.CATEGORY_EXCEPTION.getCode(), Constants.CATEGORY_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " category  {}", e);
		}

		return response;
	}
}
