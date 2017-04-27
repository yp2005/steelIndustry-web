package com.steelIndustry.service;

import java.util.List;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.Advertisement;


public interface AdvertisementService extends DataService<Advertisement, Integer> {
    public List<Advertisement> getAdvertisementList(Conditions conditions);
    
    public Advertisement saveAdvertisement(Advertisement advertisement);
    
    public int deleteAdvertisement(int id);
    
    public Advertisement getAdvertisement(int id);
    
    public Advertisement getAllianceAd();
    
    public int updateAllianceAd(String content);
}
