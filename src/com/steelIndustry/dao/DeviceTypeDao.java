package com.steelIndustry.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.DeviceType;

@Repository
public interface DeviceTypeDao extends EntityJpaDao<DeviceType, Integer> {
    @Query("select t from DeviceType t where t.id in (select relationSlaveId from RelationTable rt where rt.relationMasterId=:relationMasterId and rt.relationMasterTable='device' and rt.relationSlaveTable='device_type')")
    public List<DeviceType> getDeviceTypesByDeviceId(@Param("relationMasterId") int deviceId);
}