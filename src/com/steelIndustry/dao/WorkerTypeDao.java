package com.steelIndustry.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.MasterCard;
import com.steelIndustry.model.WorkerType;

@Repository
public interface WorkerTypeDao extends EntityJpaDao<WorkerType, Integer> {
    @Query("select t from WorkerType t where t.id in (select relationSlaveId from RelationTable rt where rt.relationMasterId=:relationMasterId and rt.relationMasterTable=:relationMasterTable and rt.relationSlaveTable='worker_type')")
    public List<WorkerType> getWorkerTypesByRelation(@Param("relationMasterId") int relationMasterId, @Param("relationMasterTable") String relationMasterTable);
}