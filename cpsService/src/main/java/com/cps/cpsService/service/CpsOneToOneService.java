package com.cps.cpsService.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.common.model.GenericPageBaseResponse;
import com.cps.cpsService.repository.CpsInquiryRepository;
import com.cps.cpsService.dto.CpsOneToOneDto;
import com.cps.cpsService.entity.CpsAnswerEntity;
import com.cps.cpsService.entity.CpsInquiryEntity;
import com.cps.cpsService.packet.CpsOnetoOnePacket;
import com.cps.cpsService.repository.CpsAnswerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsOneToOneService {
	private final CpsInquiryRepository cpsInquiryRepository;

	private final CpsAnswerRepository cpsAnswerRepository;

	private final EntityManager em;

	/**
	 * 문의 등록
	 * @date 2024-10-01
	 */
	public GenericBaseResponse<CpsOneToOneDto.CpsInquiry> inquiry(CpsOnetoOnePacket.CpsOnetoOneInfo.Inquiry request) throws Exception {
		CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryResponse response = new CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryResponse();
		CpsInquiryEntity cpsInquiryEntity = new CpsInquiryEntity();
		CpsOneToOneDto.CpsInquiry cpsInquiryDto = new CpsOneToOneDto.CpsInquiry();
		try {
			cpsInquiryEntity.setUserId(request.getUserId());
			cpsInquiryEntity.setInquiryType(request.getInquiryType());
			cpsInquiryEntity.setCampaignNum(request.getCampaignNum());
			cpsInquiryEntity.setMemberId(request.getUserId());
			cpsInquiryEntity.setPurpose(request.getPurpose());
			cpsInquiryEntity.setRegDay(request.getRegDay());
			cpsInquiryEntity.setUserName(request.getUserName());
			cpsInquiryEntity.setOrderNo(request.getOrderNo());
			cpsInquiryEntity.setProductCode(request.getProductCode());
			cpsInquiryEntity.setCurrency(request.getCurrency());
			cpsInquiryEntity.setPayment(request.getPayment());
			cpsInquiryEntity.setProductPrice(request.getProductPrice());
			cpsInquiryEntity.setProductCnt(request.getProductCnt());
			cpsInquiryEntity.setNote(request.getNote());
			cpsInquiryEntity.setEmail(request.getEmail());
			cpsInquiryEntity.setInformation(request.getInformation());
			cpsInquiryEntity.setAnswerYn("N");
			cpsInquiryRepository.save(cpsInquiryEntity);

			cpsInquiryDto = cpsInquiryDto(cpsInquiryEntity);
			response.setSuccess();
			response.setData(cpsInquiryDto);
		} catch (Exception e) {
			response.setApiMessage(Constants.INQUIRY_EXCEPTION.getCode(), Constants.INQUIRY_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " inquiry api request : {}, exception : {}", request, e);
		}

		return response;
	}

	/**
	 * 답변 등록
	 * @date 2024-10-01
	 */
	public GenericBaseResponse<CpsOneToOneDto.OneToOne> answer(CpsOnetoOnePacket.CpsOnetoOneInfo.OnetoOneRequest request) throws Exception {
		CpsOnetoOnePacket.CpsOnetoOneInfo.OneToOneResponse response = new CpsOnetoOnePacket.CpsOnetoOneInfo.OneToOneResponse();
		CpsAnswerEntity cpsAnswerEntity = new CpsAnswerEntity();
		CpsOneToOneDto.OneToOne cpsOneToOneDto = new CpsOneToOneDto.OneToOne();
		try {
			cpsAnswerEntity.setInquiryNum(request.getInquiryNum());
			cpsAnswerEntity.setNote(request.getNote());
			cpsAnswerRepository.save(cpsAnswerEntity);

			//답변완료 Y 업데이트
			int inquiry = cpsInquiryRepository.updateAnswerYnByInquiryNum(request.getInquiryNum());

			if(inquiry > 0) {
				//본문 조회
				cpsOneToOneDto.setCpsInquiry(cpsInquiryDto(cpsInquiryRepository.findByInquiryNum(request.getInquiryNum())));

				//답변
				cpsOneToOneDto.setCpsAnswer(cpsAnsweryDto(cpsAnswerEntity));

				response.setSuccess();
				response.setData(cpsOneToOneDto);
			}else{
				response.setApiMessage(Constants.INQUIRY_BLANK.getCode(), Constants.INQUIRY_BLANK.getValue());
			}
		} catch (Exception e) {
			response.setApiMessage(Constants.ANSWER_EXCEPTION.getCode(), Constants.ANSWER_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " answer api request : {}, exception : {}", request, e);
		}

		return response;
	}

	/**
	 * 문의 리스트
	 * @date 2024-10-01
	 */
	public GenericPageBaseResponse<CpsOneToOneDto.CpsInquiry> inquiryList(CpsOnetoOnePacket.CpsOnetoOneInfo.InquirySearchRequest request) throws Exception {
		CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryPageResponse response = new CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryPageResponse();
		List<CpsOneToOneDto.CpsInquiry> cpsInquiryDtoList = new ArrayList<>();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<CpsInquiryEntity> cq = cb.createQuery(CpsInquiryEntity.class);
			Root<?> root = cq.from(CpsInquiryEntity.class);

			List<Predicate> predicates = new ArrayList<>();

			if (null != request.getSearchKeyword() && !"".equals(request.getSearchKeyword())) {
				predicates.add(cb.like(root.get("userName"), "%" + request.getSearchKeyword() + "%"));
				predicates.add(cb.like(root.get("orderNo"), "%" + request.getSearchKeyword() + "%"));
			}

			if (!"ALL".equals(request.getAnswerYn())) {
				predicates.add(cb.equal(root.get("answerYn"), request.getAnswerYn()));
			}

			if (!predicates.isEmpty()) {
				cq.where(cb.or(predicates.toArray(new Predicate[0])));
			}

			TypedQuery<CpsInquiryEntity> query = em.createQuery(cq);
			//전체 개수
			int TotalPage = query.getResultList().size();

			// 페이징 처리
			if (request.getSize() == 0)request.setSize(10);
			query = em.createQuery(cq);
			query.setFirstResult(request.getPage() * request.getSize());
			query.setMaxResults(request.getSize());

			query.getResultList().forEach(inquiry -> {
				cpsInquiryDtoList.add(cpsInquiryDto(inquiry));
			});

			if (cpsInquiryDtoList.size() > 0) {
				response.setSuccess(TotalPage);
			} else {
				response.setApiMessage(Constants.INQUIRY_BLANK.getCode(), Constants.INQUIRY_BLANK.getValue());
			}
			response.setDatas(cpsInquiryDtoList);
		} catch (Exception e) {
			response.setApiMessage(Constants.INQUIRY_EXCEPTION.getCode(), Constants.INQUIRY_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " inquiryList api request : {}, exception : {}", request, e);
		}

		return response;
	}

	/**
	 * 답변 등록
	 * @date 2024-10-01
	 */
	public GenericBaseResponse<CpsOneToOneDto.OneToOne> inquiryDetail(CpsOnetoOnePacket.CpsOnetoOneInfo.OnetoOneRequest request) throws Exception {
		CpsOnetoOnePacket.CpsOnetoOneInfo.OneToOneResponse response = new CpsOnetoOnePacket.CpsOnetoOneInfo.OneToOneResponse();
		CpsOneToOneDto.OneToOne cpsOneToOneDto = new CpsOneToOneDto.OneToOne();

		try {
			//본문 조회
			cpsOneToOneDto.setCpsInquiry(cpsInquiryDto(cpsInquiryRepository.findByInquiryNum(request.getInquiryNum())));

			//답변 조회
			CpsAnswerEntity cpsAnswerEntity = cpsAnswerRepository.findByInquiryNum(request.getInquiryNum());
			if (null != cpsAnswerEntity) {
				cpsOneToOneDto.setCpsAnswer(cpsAnsweryDto(cpsAnswerRepository.findByInquiryNum(request.getInquiryNum())));
			}

			response.setSuccess();
			response.setData(cpsOneToOneDto);
		} catch (Exception e) {
			response.setApiMessage(Constants.ANSWER_SEARCH_EXCEPTION.getCode(), Constants.ANSWER_SEARCH_EXCEPTION.getValue());
			log.error(Constant.EXCEPTION_MESSAGE + " inquiryDetail api request : {}, exception : {}", request, e);
		}

		return response;
	}

	public CpsOneToOneDto.CpsInquiry cpsInquiryDto(CpsInquiryEntity cpsInquiryEntity){
		CpsOneToOneDto.CpsInquiry cpsInquiryDto = new CpsOneToOneDto.CpsInquiry();
		cpsInquiryDto.setInquiryNum(cpsInquiryEntity.getInquiryNum());
		cpsInquiryDto.setUserId(cpsInquiryEntity.getUserId());
		cpsInquiryDto.setInquiryType(cpsInquiryEntity.getInquiryType());
		cpsInquiryDto.setMemberId(cpsInquiryEntity.getUserId());
		cpsInquiryDto.setPurpose(cpsInquiryEntity.getPurpose());
		cpsInquiryDto.setRegDay(cpsInquiryEntity.getRegDay());
		cpsInquiryDto.setUserName(cpsInquiryEntity.getUserName());
		cpsInquiryDto.setOrderNo(cpsInquiryEntity.getOrderNo());
		cpsInquiryDto.setProductCode(cpsInquiryEntity.getProductCode());
		cpsInquiryDto.setCurrency(cpsInquiryEntity.getCurrency());
		cpsInquiryDto.setPayment(cpsInquiryEntity.getPayment());
		cpsInquiryDto.setProductPrice(cpsInquiryEntity.getProductPrice());
		cpsInquiryDto.setProductCnt(cpsInquiryEntity.getProductCnt());
		cpsInquiryDto.setNote(cpsInquiryEntity.getNote());
		cpsInquiryDto.setEmail(cpsInquiryEntity.getEmail());
		cpsInquiryDto.setInformation(cpsInquiryEntity.getInformation());
		cpsInquiryDto.setAnswerYn(cpsInquiryEntity.getAnswerYn());
		cpsInquiryDto.setRegDate(cpsInquiryEntity.getRegDate());
		return cpsInquiryDto;
	}

	public CpsOneToOneDto.CpsAnswer cpsAnsweryDto(CpsAnswerEntity cpsAnswerEntity){
		CpsOneToOneDto.CpsAnswer cpsAnswerDto = new CpsOneToOneDto.CpsAnswer();
		cpsAnswerDto.setInquiryNum(cpsAnswerEntity.getInquiryNum());
		cpsAnswerDto.setNote(cpsAnswerEntity.getNote());
		return cpsAnswerDto;
	}
}
