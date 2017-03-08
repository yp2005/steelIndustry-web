package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.EnterpriseCertification;

@Repository
public interface EnterpriseCertificationDao extends EntityJpaDao<EnterpriseCertification, Integer> {
	@Query("select t from EnterpriseCertification t where t.userId=:userId")
	public EnterpriseCertification getEnterpriseCertification(@Param("userId") int userId);
	
	@Modifying
	@Query("update EnterpriseCertification set state=:state where userId=:userId")
	public int updateEnterpriseCertificationState( @Param("userId") int userId, @Param("state") short state);
}
