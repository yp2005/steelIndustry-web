package com.steelIndustry.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.User;

public interface UserService extends DataService<User, Integer> {
    public User getUser(HttpServletRequest request, AjaxResult result);
    
    public User getUser(long mobileNumber);
    
    public List<User> getUserList(Conditions conditions);
    
    public User saveUser(User user);
    
    public int updateLatestLoginTime(HttpServletRequest request);
    
    public int updateUserState(int id, short state);
    
    public int updateShareState(int userId, short shareState);
}
