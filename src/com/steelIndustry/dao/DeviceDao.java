package com.steelIndustry.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.Device;

@Repository
public interface DeviceDao extends EntityJpaDao<Device, Integer> {
    @Modifying
    @Query("update Device set browseVolume = browseVolume + 1 where id=:id")
    public int updateDeviceBv(@Param("id") int id);
    
    @Modifying
    @Query("update Device set callTimes = callTimes + 1 where id=:id")
    public int updateDeviceCt(@Param("id") int id);
    
    @Modifying
    @Query("update Device set state=:state where id=:id")
    public int updateDeviceState(@Param("id") int id,@Param("state") short state);
    
    @Query("select t from Device t where t.userId=:userId")
    public List<Device> getUserDevice(@Param("userId") int userId);
}