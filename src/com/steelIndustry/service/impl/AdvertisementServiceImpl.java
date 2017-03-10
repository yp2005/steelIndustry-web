package com.steelIndustry.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.dao.AdvertisementDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.Advertisement;
import com.steelIndustry.service.AdvertisementService;
import com.steelIndustry.util.CommonUtil;

@Service("advertisementService")
public class AdvertisementServiceImpl extends DataServiceImpl<Advertisement, Integer> implements AdvertisementService {

    @Resource
    private AdvertisementDao advertisementDao;

    @Override
    public EntityJpaDao<Advertisement, Integer> getRepository() {
        return advertisementDao;
    }

    
    public Advertisement saveAdvertisement(Advertisement advertisement) {
        return advertisementDao.save(advertisement);
    }


    @Override
    public List<Advertisement> getAdvertisementList(Conditions conditions) {
        String sql = "select * from advertisement where link_type = 'innerLink' or link_type = 'outerLink'";
        Map<String, Object> params = new HashMap<String, Object>();
        if (!CommonUtil.isEmpty(conditions.getKeyword())) {
            sql += " and title like CONCAT('%',:title,'%')";
            params.put("title", conditions.getKeyword());
        }
        sql += " limit " + (conditions.getRowStartNumber() == null ? 0
                : conditions.getRowStartNumber()) + "," + (conditions.getRowCount() == null ? 10
                        : conditions.getRowCount());
        List<Advertisement> list = advertisementDao.executeNativeSQL(sql, params, Advertisement.class);
        return list;
    }


    @Override
    public int deleteAdvertisement(int id) {
        try {
            advertisementDao.delete(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public Advertisement getAdvertisement(int id) {
        return advertisementDao.getOne(id);
    }


    @Override
    public String getAllianceAd() {
        return advertisementDao.getAllianceAd();
    }
    
    public int updateAllianceAd(String content) {
        return advertisementDao.updateAllianceAd(content);
    }

}
