package com.steelIndustry.service;

import java.util.List;
import java.util.Map;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.EmploymentDemand;
import com.steelIndustry.model.User;

public interface EmploymentDemandService extends DataService<EmploymentDemand, Integer> {
    public EmploymentDemand getEmploymentDemandById(int id);
    
    public List<EmploymentDemand> getUserEmploymentDemand(User user);

    public int saveEmploymentDemand(EmploymentDemand employmentDemand);
    
    public List<Map<String, Object>> getEmploymentDemandList(Conditions conditions);
    
    public int updateEmploymentDemandBv(int id);
    
    public int updateEmploymentDemandCt(int id);
}
