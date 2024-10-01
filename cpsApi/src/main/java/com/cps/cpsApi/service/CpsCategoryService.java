package com.cps.cpsApi.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsCategoryDto;
import com.cps.cpsApi.entity.CpsCategoryEntity;
import com.cps.cpsApi.packet.CpsCategoryPacket;
import com.cps.cpsApi.repository.CpsCategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsCategoryService {
	private final CpsCategoryRepository cpsCategoryRepository;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 카테고리 등록
	 * @date 2024-09-03
	 */
	public GenericBaseResponse<CpsCategoryDto> category(CpsCategoryPacket.CategoryInfo.CategoryRequest request) throws Exception {
		CpsCategoryPacket.CategoryInfo.Response response = new CpsCategoryPacket.CategoryInfo.Response();
		List<CpsCategoryDto> cpsCategoryDtoList = new ArrayList<>();

		try {
			if(request.getApiType().equals("D")){
				request.getCategoryList().forEach(category->{
					cpsCategoryRepository.deleteByCategory(category.getCategory());
				});
			} else {
				for (CpsCategoryPacket.CategoryInfo.Category category : request.getCategoryList()) {
					CpsCategoryEntity cpsCategoryEntity = new CpsCategoryEntity();
					CpsCategoryDto cpsCategoryDto = new CpsCategoryDto();

					if (request.getApiType().equals("I")) {
						cpsCategoryEntity.setCategory(getMaxCategory("category", "CpsCategoryEntity"));
						CpsCategoryEntity categoryNameChk = cpsCategoryRepository.findByCategoryName(category.getCategoryName());
						if (null != categoryNameChk) {
							response.setApiMessage(Constants.CATEGORY_DUPLICATION.getCode(), Constants.CATEGORY_DUPLICATION.getValue());
							cpsCategoryDto.setCategoryName(category.getCategoryName());
							response.setData(cpsCategoryDto);
							break;
						}
					} else {
						cpsCategoryEntity.setCategory(category.getCategory());
					}
					cpsCategoryEntity.setCategoryName(category.getCategoryName());
					if (category.getCategoryRank() > 0) {
						cpsCategoryEntity.setCategoryRank(category.getCategoryRank());
					} else {
						cpsCategoryEntity.setCategoryRank(999);
					}
					cpsCategoryRepository.save(cpsCategoryEntity);
					cpsCategoryDto.setCategory(category.getCategory());
					cpsCategoryDto.setCategoryName(category.getCategoryName());
					cpsCategoryDto.setCategoryRank(category.getCategoryRank());
					cpsCategoryDtoList.add(cpsCategoryDto);
				}
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.CATEGORY_EXCEPTION.getCode(), Constants.CATEGORY_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " category api request : {}, exception : {}", request, e);
		}

		return response;
	}

	public String getMaxCategory(String column, String tableName) {
		TypedQuery<String> query = entityManager.createQuery("SELECT MAX("+column+") FROM "+tableName, String.class);
		String prefix = query.getSingleResult().substring(0, 3);
		int number = Integer.parseInt(query.getSingleResult().substring(3));
		number++;
		return prefix + String.format("%02d", number);
	}

	/**
	 * 카테고리 리스트
	 * @date 2024-09-30
	 */
	public GenericBaseResponse<CpsCategoryDto> categoryList() throws Exception {
		CpsCategoryPacket.CategoryInfo.Response response = new CpsCategoryPacket.CategoryInfo.Response();
		try {
			List<CpsCategoryDto> cpsCategoryDtoList =  cpsCategoryRepository.findCategoryCampaignCounts(Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
			response.setSuccess();
			response.setDatas(cpsCategoryDtoList);
		} catch (Exception e) {
			response.setApiMessage(Constants.CATEGORY_SEARCH_EXCEPTION.getCode(), Constants.CATEGORY_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " categoryList api exception : {}", e);
		}
		return response;
	}
}
