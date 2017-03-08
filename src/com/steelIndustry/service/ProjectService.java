package com.steelIndustry.service;

import java.util.List;
import java.util.Map;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.MasterCard;
import com.steelIndustry.model.Project;
import com.steelIndustry.model.User;

public interface ProjectService extends DataService<Project, Integer> {
    public Project getProjectById(int id);

    public int saveProject(Project project);
    
    public List<Map<String, Object>> getProjectList(Conditions conditions);
    
    public List<Project> getUserProject(User user);
    
    public int updateProjectBv(int id);
    
    public int updateProjectCt(int id);
    
    public int updateProjectState(int id, short state);
}
