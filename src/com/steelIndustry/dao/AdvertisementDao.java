package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.Advertisement;

@Repository
public interface AdvertisementDao extends EntityJpaDao<Advertisement, Integer> {
    @Query("select content from Advertisement where link_type = 'alliance'")
    public String getAllianceAd();
    
    @Modifying
    @Query("update Advertisement set content=:content where link_type = 'alliance'")
    public int updateAllianceAd(String content);
}
