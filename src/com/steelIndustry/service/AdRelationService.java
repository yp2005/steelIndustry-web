package com.steelIndustry.service;

import java.util.List;

import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.AdRelation;
import com.steelIndustry.model.Advertisement;


public interface AdRelationService extends DataService<AdRelation, Integer> {
    public List<Advertisement> getPositionAdList(String position);
    
    public AdRelation saveAdRelation(AdRelation adRelation);
    
    public int deleteAdRelation(String position, int adId);
}
