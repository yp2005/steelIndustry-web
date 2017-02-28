package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.RelationTable;

@Repository
public interface RelationTableDao extends EntityJpaDao<RelationTable, Integer> {
    @Modifying
    @Query("delete from RelationTable where relationMasterTable=:relationMasterTable and relationMasterId=:relationMasterId")
    public int deleteRelationTable(@Param("relationMasterTable") String relationMasterTable,@Param("relationMasterId") int relationMasterId);
}