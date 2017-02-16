package com.steelIndustry.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.dao.SettingsDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.Settings;
import com.steelIndustry.service.SettingsService;

@Service("settingsService")
public class SettingsServiceImpl extends DataServiceImpl<Settings, Integer> implements SettingsService {

	@Resource
	private SettingsDao settingsDao;
	
	@Override
	public int updateShareSwitch(short shareSwitch) {
	    return settingsDao.updateShareSwitch(shareSwitch);
	}
	
	@Override
	public Settings getSettings() {
	    return settingsDao.getSettings();
	}

    @Override
    public EntityJpaDao<Settings, Integer> getRepository() {
        return  settingsDao;
    }

}
