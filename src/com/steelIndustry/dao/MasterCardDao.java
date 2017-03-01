package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.MasterCard;

@Repository
public interface MasterCardDao extends EntityJpaDao<MasterCard, Integer> {
    @Query("select t from MasterCard t where t.userId=:userId")
    public MasterCard getMasterCardByUserId(@Param("userId") int userId);
    
    @Modifying
    @Query("update MasterCard set browseVolume = browseVolume + 1 where id=:id")
    public int updateMasterCardBv(@Param("id") int id);
    
    @Modifying
    @Query("update MasterCard set callTimes = callTimes + 1 where id=:id")
    public int updateMasterCardCt(@Param("id") int id);
    
    @Modifying
    @Query("update MasterCard set isWorking = :isWorking where id=:id")
    public int updateMasterCardWorkState(@Param("id") int id, @Param("isWorking") short isWorking);
}