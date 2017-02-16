package com.steelIndustry.service;

import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.Settings;


public interface SettingsService extends DataService<Settings, Integer> {
    public int updateShareSwitch(short shareSwitch);
    
    public Settings getSettings();
}
