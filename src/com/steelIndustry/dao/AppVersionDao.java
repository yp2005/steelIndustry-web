package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.AppVersion;

@Repository
public interface AppVersionDao extends EntityJpaDao<AppVersion, Integer> {
	@Query("select a from AppVersion a where a.versionTime = (select max(versionTime) from AppVersion)")
	public AppVersion getLatestAppVersion();
}
