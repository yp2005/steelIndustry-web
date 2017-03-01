package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.Store;

@Repository
public interface StoreDao extends EntityJpaDao<Store, Integer> {
    @Query("select t from Store t where t.userId=:userId")
    public Store getStoreByUserId(@Param("userId") int userId);
    
    @Modifying
    @Query("update Store set browseVolume = browseVolume + 1 where id=:id")
    public int updateStoreBv(@Param("id") int id);
    
    @Modifying
    @Query("update Store set callTimes = callTimes + 1 where id=:id")
    public int updateStoreCt(@Param("id") int id);
}