package com.steelIndustry.dao;

import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.Advertisement;

@Repository
public interface AdvertisementDao extends EntityJpaDao<Advertisement, Integer> {
	
}
