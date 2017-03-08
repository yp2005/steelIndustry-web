package com.steelIndustry.service;

import java.util.List;
import java.util.Map;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.Store;

public interface StoreService extends DataService<Store, Integer> {
    public Store getStoreByUserId(int userId);

    public int saveStore(Store store);
    
    public List<Map<String, Object>> getStoreList(Conditions conditions);
    
    public int updateStoreBv(int id);
    
    public int updateStoreCt(int id);
    
    public int updateStoreState(int id, short state);
}
