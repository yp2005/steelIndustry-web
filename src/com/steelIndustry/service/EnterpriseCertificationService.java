package com.steelIndustry.service;

import java.util.List;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.EnterpriseCertification;


public interface EnterpriseCertificationService extends DataService<EnterpriseCertification, Integer> {
    public EnterpriseCertification getEnterpriseCertification(int userId);
    
    public int updateEnterpriseCertificationState(int userId, short state);
    
    public List<EnterpriseCertification> getEnterpriseCertificationList(Conditions conditions);
}
