package com.steelIndustry.service;

import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.Settings;

public interface SettingsService extends DataService<Settings, Integer> {
    public Settings getSettings();
    
    public Settings saveSettings(Settings settings);

    public int updateShareSwitch(short shareSwitch);

    public int updateIsCheckStore(short isCheckStore);

    public int updateIsCheckProject(short isCheckProject);

    public int updateIsCheckCard(short isCheckCard);

    public int updateIsCheckWork(short isCheckWork);

    public int updateMainPostPoints(int mainPostPoints);

    public int updateReplyingPoints(int replyingPoints);

    public int updateHomePageAdType(String homePageAdType);

    public int updateListPageAdType(String listPageAdType);

    public int updateDetailPageAdType(String detailPageAdType);

}
