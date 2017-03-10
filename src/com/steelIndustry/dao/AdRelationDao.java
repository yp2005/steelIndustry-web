package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.AdRelation;

@Repository
public interface AdRelationDao extends EntityJpaDao<AdRelation, Integer> {
    @Modifying
    @Query("delete from AdRelation where position=:position and adId=:adId")
    public int delAdRelation(@Param("position") String position, @Param("adId") int adId);
}
