package com.steelIndustry.service;

import java.util.List;
import java.util.Map;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.Device;
import com.steelIndustry.model.User;

public interface DeviceService extends DataService<Device, Integer> {
    public Device getDeviceById(int id);
    
    public List<Device> getUserDevice(User user);

    public int saveDevice(Device device);
    
    public List<Map<String, Object>> getDeviceList(Conditions conditions);
    
    public List<Map<String, Object>> getHotDevice();
    
    public int updateDeviceBv(int id);
    
    public int updateDeviceCt(int id);
    
    public int updateDeviceState(int id, short state);
}
