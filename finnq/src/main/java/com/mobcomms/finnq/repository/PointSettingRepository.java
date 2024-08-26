package com.mobcomms.finnq.repository;

import com.mobcomms.finnq.entity.PointSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointSettingRepository extends JpaRepository<PointSettingEntity, String> {
    List<PointSettingEntity> findAllByUseYN(String useYN);

    PointSettingEntity findAllByType(int type);
}
