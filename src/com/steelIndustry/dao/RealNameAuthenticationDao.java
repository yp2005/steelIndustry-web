package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.RealNameAuthentication;

@Repository
public interface RealNameAuthenticationDao extends EntityJpaDao<RealNameAuthentication, Integer> {
	@Query("select t from RealNameAuthentication t where t.userId=:userId")
	public RealNameAuthentication getRealNameAuthentication(@Param("userId") int userId);
	
	@Modifying
	@Query("update RealNameAuthentication set state=:state where userId=:userId")
	public int updateRealNameAuthenticationState( @Param("userId") int userId, @Param("state") short state);
}
