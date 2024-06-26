package com.mobcomms.shinhan.repository;

import com.mobcomms.shinhan.entity.PointBannerSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointBannerSettingRepository extends JpaRepository<PointBannerSettingEntity, Long> {
    //사용 여부에 따른 가장 최근 세팅값 한개 가져오기
    PointBannerSettingEntity findFirstByUseYnOrderByRegDateDesc(String useYN);
}
