package com.steelIndustry.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.EmploymentDemand;

@Repository
public interface EmploymentDemandDao extends EntityJpaDao<EmploymentDemand, Integer> {
    @Query("select t from EmploymentDemand t where t.id=:id")
    public EmploymentDemand getEmploymentDemandById(@Param("id") int id);
    
    @Query("select t from EmploymentDemand t where t.userId=:userId")
    public List<EmploymentDemand> getUserEmploymentDemand(int userId);
    
    @Modifying
    @Query("update EmploymentDemand set browseVolume = browseVolume + 1 where id=:id")
    public int updateEmploymentDemandBv(@Param("id") int id);
    
    @Modifying
    @Query("update EmploymentDemand set callTimes = callTimes + 1 where id=:id")
    public int updateEmploymentDemandCt(@Param("id") int id);
    
    @Modifying
    @Query("update EmploymentDemand set state=:state where id=:id")
    public int updateEmploymentDemandState(@Param("id") int id, @Param("state") short state);
}