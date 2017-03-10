package com.steelIndustry.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.dao.AdRelationDao;
import com.steelIndustry.dao.AdvertisementDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.AdRelation;
import com.steelIndustry.model.Advertisement;
import com.steelIndustry.service.AdRelationService;

@Service("adRelationService")
public class AdRelationServiceImpl extends DataServiceImpl<AdRelation, Integer> implements AdRelationService {

    @Resource
    private AdRelationDao adRelationDao;
    
    @Resource
    private AdvertisementDao advertisementDao;

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
            adRelationDao.delAdRelation(position, adId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
