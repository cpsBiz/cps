package com.cps.api.service;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.model.GenericBaseResponse;
import com.cps.common.utils.CommonUtil;
import com.cps.common.dto.CommissionDto;
import com.cps.common.dto.CpsRewardUnitDto;
import com.cps.common.dto.DotPitchRewardDto;
import com.cps.common.entity.CpsRewardEntity;
import com.cps.common.entity.CpsRewardFirstEntity;
import com.cps.common.entity.CpsRewardTempEntity;
import com.cps.common.entity.CpsRewardUnitEntity;
import com.cps.common.packet.CpsRewardPacket;
import com.cps.common.repository.CpsClickRepository;
import com.cps.common.repository.CpsRewardFirstRepository;
import com.cps.common.repository.CpsRewardRepository;
import com.cps.common.repository.CpsRewardUnitRepository;
import com.cps.common.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpsRewardService {
	@Value("${dotPitch.domain.url}") String dotPitchDomain;

	@Value("${dotPitch.reco.cps.url}") String dotPitchCpsEndPoint;

	@Value("${dotPitch.reco.order.url}") String dotPitchOrderEndPoint;

	@Value("${dotPitch.id}") String dotPitchId;

	@Value("${dotPitch.pw}") String dotPtichPw;

	@Value("${linkPrice.domain.url}") String linkPriceDomain;

	@Value("${linkPrice.domain.reward.url}") String linkPriceReward;

	@Value("${linkPrice.authKey}") String linkPriceAuthKey;

	@Value("${linkPrice.site.code}") String linkPriceSiteCode;

	@Value("${coupang.domain.url}") String coupangDomain;

	@Value("${coupang.domain.order.url}") String coupangOrderDomain;

	@Value("${coupang.domain.cancel.url}") String coupangCancelDomain;

	@Value("${coupang.subId}") String coupangSubId;

	private final HttpService httpService;

	private final CpsClickRepository cpsClickRepository;

	private final CpsRewardRepository cpsRewardRepository;

	private final CpsRewardFirstRepository cpsRewardFirstRepository;

	private final CpsRewardUnitRepository cpsRewardUnitRepository;

	public CpsRewardFirstEntity cpsRewardEntityList(CpsRewardEntity rewardEntity){
		CpsRewardFirstEntity cpsRewardFirstEntity = new CpsRewardFirstEntity();
		cpsRewardFirstEntity.setClickNum(rewardEntity.getClickNum());
		cpsRewardFirstEntity.setRegDay(rewardEntity.getRegDay());
		cpsRewardFirstEntity.setRegYm(rewardEntity.getRegYm());
		cpsRewardFirstEntity.setRegHour(rewardEntity.getRegHour());
		cpsRewardFirstEntity.setOrderNo(rewardEntity.getOrderNo());
		cpsRewardFirstEntity.setProductCode(rewardEntity.getProductCode());
		cpsRewardFirstEntity.setCampaignNum(rewardEntity.getCampaignNum());
		cpsRewardFirstEntity.setMerchantId(rewardEntity.getMerchantId());
		cpsRewardFirstEntity.setAgencyId(rewardEntity.getAgencyId());
		cpsRewardFirstEntity.setAffliateId(rewardEntity.getAffliateId());
		cpsRewardFirstEntity.setZoneId(rewardEntity.getZoneId());
		cpsRewardFirstEntity.setSite(rewardEntity.getSite());
		cpsRewardFirstEntity.setOs(rewardEntity.getOs());
		cpsRewardFirstEntity.setType(rewardEntity.getType());
		cpsRewardFirstEntity.setUserId(rewardEntity.getUserId());
		cpsRewardFirstEntity.setAdId(rewardEntity.getAdId());
		cpsRewardFirstEntity.setMemberName(rewardEntity.getMemberName());
		cpsRewardFirstEntity.setProductName(rewardEntity.getProductName());
		cpsRewardFirstEntity.setProductCnt(rewardEntity.getProductCnt());
		cpsRewardFirstEntity.setProductPrice(rewardEntity.getProductPrice());
		cpsRewardFirstEntity.setCommission(rewardEntity.getCommission());
		cpsRewardFirstEntity.setCommissionProfit(rewardEntity.getCommissionProfit());
		cpsRewardFirstEntity.setAffliateCommission(rewardEntity.getAffliateCommission());
		cpsRewardFirstEntity.setUserCommission(rewardEntity.getUserCommission());
		cpsRewardFirstEntity.setCommissionRate(rewardEntity.getCommissionRate());
		cpsRewardFirstEntity.setStatus(rewardEntity.getStatus());
		cpsRewardFirstEntity.setBaseCommission(rewardEntity.getBaseCommission());
		cpsRewardFirstEntity.setIncentiveCommission(rewardEntity.getIncentiveCommission());
		cpsRewardFirstEntity.setIpAddress(rewardEntity.getIpAddress());
		return cpsRewardFirstEntity;
	}

	public CpsRewardTempEntity cpsRewardTempEntity (CpsRewardEntity cpsRewardEntity){
		CpsRewardTempEntity cpsRewardTempEntity = new CpsRewardTempEntity();
		if (cpsRewardEntity.getRewardNum() != 0) {
			cpsRewardTempEntity.setRewardNum(cpsRewardEntity.getRewardNum());
		}
		cpsRewardTempEntity.setClickNum(cpsRewardEntity.getClickNum());
		cpsRewardTempEntity.setOrderNo(cpsRewardEntity.getOrderNo());
		cpsRewardTempEntity.setProductCode(cpsRewardEntity.getProductCode());
		cpsRewardTempEntity.setRegDay(cpsRewardEntity.getRegDay());
		cpsRewardTempEntity.setRegYm(cpsRewardEntity.getRegYm());
		cpsRewardTempEntity.setRegHour(cpsRewardEntity.getRegHour());
		cpsRewardTempEntity.setCampaignNum(cpsRewardEntity.getCampaignNum());
		cpsRewardTempEntity.setMerchantId(cpsRewardEntity.getMerchantId());
		cpsRewardTempEntity.setAgencyId(cpsRewardEntity.getAgencyId());
		cpsRewardTempEntity.setAffliateId(cpsRewardEntity.getAffliateId());
		cpsRewardTempEntity.setZoneId(cpsRewardEntity.getZoneId());
		cpsRewardTempEntity.setSite(cpsRewardEntity.getSite());
		cpsRewardTempEntity.setOs(cpsRewardEntity.getOs());
		cpsRewardTempEntity.setType(cpsRewardEntity.getType());
		cpsRewardTempEntity.setUserId(cpsRewardEntity.getUserId());
		cpsRewardTempEntity.setAdId(cpsRewardEntity.getAdId());
		cpsRewardTempEntity.setStatus(cpsRewardEntity.getStatus());
		cpsRewardTempEntity.setMemberName(cpsRewardEntity.getMemberName());
		cpsRewardTempEntity.setProductName(cpsRewardEntity.getProductName());
		cpsRewardTempEntity.setProductCnt(cpsRewardEntity.getProductCnt());
		cpsRewardTempEntity.setProductPrice(cpsRewardEntity.getProductPrice());
		cpsRewardTempEntity.setTransComment(cpsRewardEntity.getTransComment());
		cpsRewardTempEntity.setCategory(cpsRewardEntity.getCategory());
		cpsRewardTempEntity.setCommission(cpsRewardEntity.getCommission());
		cpsRewardTempEntity.setCommissionProfit(cpsRewardEntity.getCommissionProfit());
		cpsRewardTempEntity.setAffliateCommission(cpsRewardEntity.getAffliateCommission());
		cpsRewardTempEntity.setUserCommission(cpsRewardEntity.getUserCommission());
		cpsRewardTempEntity.setCommissionRate(cpsRewardEntity.getCommissionRate());
		cpsRewardTempEntity.setBaseCommission(cpsRewardEntity.getBaseCommission());
		cpsRewardTempEntity.setIncentiveCommission(cpsRewardEntity.getIncentiveCommission());
		cpsRewardTempEntity.setIpAddress(cpsRewardEntity.getIpAddress());

		return cpsRewardTempEntity;
	}

	public void cpsRewardEntityListSave (List<CpsRewardEntity> cpsRewardEntityList, List<CpsRewardFirstEntity> cpsRewardFirstEntityList){
		int batchSize = 1000; // 한번에 저장할 배치 크기
		List<CpsRewardEntity> cpsRewardEntityListBatch = new ArrayList<>();
		List<CpsRewardFirstEntity> cpsRewardFirstEntityBatch = new ArrayList<>();
		
		//리워드 저장
		if (cpsRewardEntityList.size() > 0) {
			for (int i = 0; i < cpsRewardEntityList.size(); i++) {
				cpsRewardEntityListBatch.add(cpsRewardEntityList.get(i));

				if (cpsRewardEntityListBatch.size() == batchSize) {
					cpsRewardRepository.saveAll(cpsRewardEntityListBatch);
					cpsRewardEntityListBatch.clear();
				}
			}
			if (!cpsRewardEntityListBatch.isEmpty()) {
				cpsRewardRepository.saveAll(cpsRewardEntityListBatch); 
			}
		}

		//첫데이터 저장
		if (cpsRewardFirstEntityList.size() > 0) {
			for (int i = 0; i < cpsRewardFirstEntityList.size(); i++) {
				cpsRewardFirstEntityBatch.add(cpsRewardFirstEntityList.get(i));

				if (cpsRewardFirstEntityBatch.size() == batchSize) {
					cpsRewardFirstRepository.saveAll(cpsRewardFirstEntityBatch);
					cpsRewardFirstEntityBatch.clear();
				}
			}
			if (!cpsRewardFirstEntityBatch.isEmpty()) {
				cpsRewardFirstRepository.saveAll(cpsRewardFirstEntityBatch);
			}
		}

	}
}
