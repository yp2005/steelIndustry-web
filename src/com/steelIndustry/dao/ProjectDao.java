package com.steelIndustry.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.Project;

@Repository
public interface ProjectDao extends EntityJpaDao<Project, Integer> {
    @Query("select t from Project t where t.id=:id")
    public Project getProjectById(@Param("id") int id);
    
    @Modifying
    @Query("update Project set browseVolume = browseVolume + 1 where id=:id")
    public int updateProjectBv(@Param("id") int id);
    
    @Modifying
    @Query("update Project set callTimes = callTimes + 1 where id=:id")
    public int updateProjectCt(@Param("id") int id);
    
    @Modifying
    @Query("update Project set state = :state where id=:id")
    public int updateProjectState(@Param("id") int id,@Param("state") short state);
    
    @Query("select t from Project t where t.userId=:userId")
    public List<Project> getUserProject(@Param("userId") int userId);
}