package com.steelIndustry.service;

import java.util.List;

import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.Collection;
import com.steelIndustry.model.EmploymentDemand;
import com.steelIndustry.model.MasterCard;
import com.steelIndustry.model.Project;
import com.steelIndustry.model.Store;

public interface CollectionService extends DataService<Collection, Integer> {
    public List<MasterCard> getMasterCards(int userId);

    public List<EmploymentDemand> getEmploymentDemands(int userId);

    public List<Project> getProjects(int userId);

    public List<Store> getStores(int userId);

    //取消收藏type：card、project、work、store
    public int delCollection(int userId, String type, int collectId);
    
    //添加收藏type：card、project、work、store
    public Collection addCollection(int userId, String type, int collectId);
}
