package com.steelIndustry.service;

import java.util.List;

import com.steelIndustry.model.AreaData;
import com.stellIndustry.framework.service.DataService;


public interface AreaDataService extends DataService<AreaData, Integer> {
    public List<AreaData> allAreaData();
}
