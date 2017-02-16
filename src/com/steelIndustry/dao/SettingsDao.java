package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.Settings;

@Repository
public interface SettingsDao extends EntityJpaDao<Settings, Integer> {
    @Query("select s from Settings s")
    public Settings getSettings();

    @Modifying
    @Query("update Settings s set s.shareSwitch=:shareSwitch")
    public int updateShareSwitch(@Param("shareSwitch") short shareSwitch);
}
