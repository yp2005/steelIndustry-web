package com.steelIndustry.service;

import java.util.List;

import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.Advertisement;


public interface AdvertisementService extends DataService<Advertisement, Integer> {
    public List<Advertisement> getAdvertisementList();
    
    public Advertisement saveAdvertisement(Advertisement advertisement);
}
