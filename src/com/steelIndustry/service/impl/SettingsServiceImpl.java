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
    public Settings saveSettings(Settings settings) {
        return settingsDao.save(settings);
    }

    @Override
    public int updateIsCheckStore(short isCheckStore) {
        return settingsDao.updateIsCheckStore(isCheckStore);
    }

    @Override
    public int updateIsCheckProject(short isCheckProject) {
        return settingsDao.updateIsCheckProject(isCheckProject);
    }

    @Override
    public int updateIsCheckCard(short isCheckCard) {
        return settingsDao.updateIsCheckCard(isCheckCard);
    }

    @Override
    public int updateIsCheckWork(short isCheckWork) {
        return settingsDao.updateIsCheckWork(isCheckWork);
    }

    @Override
    public int updateMainPostPoints(int mainPostPoints) {
        return settingsDao.updateMainPostPoints(mainPostPoints);
    }

    @Override
    public int updateReplyingPoints(int replyingPoints) {
        return settingsDao.updateReplyingPoints(replyingPoints);
    }

    @Override
    public int updateHomePageAdType(String homePageAdType) {
        return settingsDao.updateHomePageAdType(homePageAdType);
    }

    @Override
    public int updateListPageAdType(String listPageAdType) {
        return settingsDao.updateListPageAdType(listPageAdType);
    }

    @Override
    public int updateDetailPageAdType(String detailPageAdType) {
        return settingsDao.updateDetailPageAdType(detailPageAdType);
    }

    @Override
    public EntityJpaDao<Settings, Integer> getRepository() {
        return settingsDao;
    }

}
