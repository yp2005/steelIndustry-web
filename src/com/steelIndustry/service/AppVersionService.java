package com.steelIndustry.service;

import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.AppVersion;


public interface AppVersionService extends DataService<AppVersion, Integer> {
    public AppVersion getLatestAppVersion();
}
