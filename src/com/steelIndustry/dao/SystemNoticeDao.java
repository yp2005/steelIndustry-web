package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.SystemNotice;

@Repository
public interface SystemNoticeDao extends EntityJpaDao<SystemNotice, Integer> {
	@Query("select t from SystemNotice t where t.id=:id")
	public SystemNotice getSystemNotice(@Param("id") int id);
}
