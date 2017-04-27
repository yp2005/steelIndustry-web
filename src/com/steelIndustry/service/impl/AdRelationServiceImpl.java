package com.steelIndustry.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.steelIndustry.dao.AdRelationDao;
import com.steelIndustry.dao.AdvertisementDao;
import com.steelIndustry.dao.SettingsDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.AdRelation;
import com.steelIndustry.model.Advertisement;
import com.steelIndustry.model.Settings;
import com.steelIndustry.service.AdRelationService;

@Service("adRelationService")
public class AdRelationServiceImpl extends DataServiceImpl<AdRelation, Integer> implements AdRelationService {

    @Resource
    private AdRelationDao adRelationDao;
    
    @Resource
    private AdvertisementDao advertisementDao;
    
    @Resource
    private SettingsDao settingsDao;

    @Override
    public EntityJpaDao<AdRelation, Integer> getRepository() {
        return adRelationDao;
    }

    
    public AdRelation saveAdRelation(AdRelation adRelation) {
        return adRelationDao.save(adRelation);
    }


    @Override
    public List<Advertisement> getPositionAdList(String position) {
        String sql = "select * from advertisement where id in (select ad_id from ad_relation where postion=:postion)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("postion", position);
        return advertisementDao.executeNativeSQL(sql, params, Advertisement.class);
    }

    @Override
    public int deleteAdRelation(String position, int adId) {
        try {
            adRelationDao.deleteAdRelation(position, adId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int saveAppAdvertisement(JSONObject adSetting) {
        JSONArray homePage = adSetting.getJSONArray("homePage");
        JSONArray listPage = adSetting.getJSONArray("listPage");
        JSONArray detailPage = adSetting.getJSONArray("detailPage");
        Settings settings = settingsDao.getSettings();
        settings.setListPageAdType(adSetting.getString("listPageAdType"));
        settings.setDetailPageAdType(adSetting.getString("detailPageAdType"));
        settingsDao.save(settings);
        adRelationDao.deleteAll();
        for(int i = 0; i < homePage.size(); i++) {
            AdRelation adRelation = new AdRelation();
            adRelation.setPostion("homePage");
            adRelation.setAdId(homePage.getInteger(i));
            adRelation = adRelationDao.save(adRelation);
            if(adRelation == null) {
                return 0;
            }
        }
        for(int j = 0; j < listPage.size(); j++) {
            AdRelation adRelation = new AdRelation();
            adRelation.setPostion("listPage");
            adRelation.setAdId(listPage.getInteger(j));
            adRelation = adRelationDao.save(adRelation);
            if(adRelation == null) {
                return 0;
            }
        }
        for(int k = 0; k < detailPage.size(); k++) {
            AdRelation adRelation = new AdRelation();
            adRelation.setPostion("detailPage");
            adRelation.setAdId(detailPage.getInteger(k));
            adRelation = adRelationDao.save(adRelation);
            if(adRelation == null) {
                return 0;
            }
        }
        return 1;
    }
}
