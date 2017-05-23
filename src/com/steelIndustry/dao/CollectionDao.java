package com.steelIndustry.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.Collection;
import com.steelIndustry.model.EmploymentDemand;
import com.steelIndustry.model.MasterCard;
import com.steelIndustry.model.Project;
import com.steelIndustry.model.Device;

@Repository
public interface CollectionDao extends EntityJpaDao<Collection, Integer> {
	@Query("select t from MasterCard t where t.id in (select collectId from Collection where userId=:userId and type='card')")
	public List<MasterCard> getMasterCards(@Param("userId") int userId);
	
	@Query("select t from EmploymentDemand t where t.id in (select collectId from Collection where userId=:userId and type='work')")
    public List<EmploymentDemand> getEmploymentDemands(@Param("userId") int userId);

	@Query("select t from Project t where t.id in (select collectId from Collection where userId=:userId and type='project')")
    public List<Project> getProjects(@Param("userId") int userId);

	@Query("select t from Device t where t.id in (select collectId from Collection where userId=:userId and type='device')")
    public List<Device> getDevices(@Param("userId") int userId);
	
	@Modifying
	@Query("delete from Collection where userId=:userId and type=:type and collectId=:collectId")
	public int deleteCollection(@Param("userId") int userId, @Param("type") String type, @Param("collectId") int collectId);
}
