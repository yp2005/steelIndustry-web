package com.steelIndustry.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.dao.AppVersionDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.AppVersion;
import com.steelIndustry.service.AppVersionService;

@Service("appVersionService")
public class AppVersionServiceImpl extends DataServiceImpl<AppVersion, Integer> implements AppVersionService {

	@Resource
	private AppVersionDao appVersionDao;
	
	@Override
	public AppVersion getLatestAppVersion() {
	    return appVersionDao.getLatestAppVersion();
	}

    @Override
    public EntityJpaDao<AppVersion, Integer> getRepository() {
        return  appVersionDao;
    }

}
