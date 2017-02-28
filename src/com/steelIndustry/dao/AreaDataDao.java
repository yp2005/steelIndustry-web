package com.steelIndustry.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.AreaData;

@Repository
public interface AreaDataDao extends EntityJpaDao<AreaData, Integer> {
	@Query("select a from AreaData a where a.areaDeep = 1")
	public List<AreaData> allAreaData();
	
	@Query("select a from AreaData a where a.areaId in (select relationSlaveId from RelationTable rt where rt.relationMasterId = :relationMasterId and rt.relationMasterTable=:relationMasterTable and rt.relationSlaveTable='area_data')")
    public List<AreaData> getAreaDatasByRelation(@Param("relationMasterId") int relationMasterId, @Param("relationMasterTable") String relationMasterTable);
}
