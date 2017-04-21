package com.steelIndustry.service;

import java.util.List;
import java.util.Map;

import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.Collection;

public interface CollectionService extends DataService<Collection, Integer> {
    public List<Map<String, Object>> getMasterCards(int userId);

    public List<Map<String, Object>> getEmploymentDemands(int userId);

    public List<Map<String, Object>> getProjects(int userId);

    public List<Map<String, Object>> getStores(int userId);

    //取消收藏type：card、project、work、store
    public int deleteCollection(int userId, String type, int collectId);
    
    //添加收藏type：card、project、work、store
    public Collection addCollection(int userId, String type, int collectId);
    
    public short isCollected(int userId, String type, int collectId);
}
