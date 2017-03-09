package com.steelIndustry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.dao.AdvertisementDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.Advertisement;
import com.steelIndustry.service.AdvertisementService;

@Service("advertisementService")
public class AdvertisementServiceImpl extends DataServiceImpl<Advertisement, Integer> implements AdvertisementService {

    @Resource
    private AdvertisementDao advertisementDao;

    @Override
    public EntityJpaDao<Advertisement, Integer> getRepository() {
        return advertisementDao;
    }

    @Override
    public List<Advertisement> getAdvertisementList() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Advertisement saveAdvertisement(Advertisement advertisement) {
        return advertisementDao.save(advertisement);
    }

}
