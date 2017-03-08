package com.steelIndustry.service;

import java.util.List;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.RealNameAuthentication;


public interface RealNameAuthenticationService extends DataService<RealNameAuthentication, Integer> {
    public RealNameAuthentication getRealNameAuthentication(int userId);
    
    public int updateRealNameAuthenticationState(int userId, short state);
    
    public List<RealNameAuthentication> getRealNameAuthenticationList(Conditions conditions);
}
