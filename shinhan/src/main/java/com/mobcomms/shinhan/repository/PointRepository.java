package com.mobcomms.shinhan.repository;

import com.mobcomms.shinhan.entity.PointBannerSettingEntity;
import com.mobcomms.shinhan.entity.PointEntity;
import com.mobcomms.shinhan.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 *
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
public interface PointRepository extends JpaRepository<PointEntity, Long> {
    //해당 유저의 가장 최근 적립 내역 1개 가져오기
    PointEntity findFirstByUserKeyOrderByRegDateDesc(String userKey);
}


