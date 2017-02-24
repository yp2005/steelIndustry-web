package com.steelIndustry.service;

import javax.servlet.http.HttpServletRequest;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.User;

public interface UserService extends DataService<User, Integer> {
    public User getUser(HttpServletRequest request, AjaxResult result);
    
    public User getUser(long mobileNumber);
    
    public User save(User user);
    
    public int updateLatestLoginTime(HttpServletRequest request);
}
