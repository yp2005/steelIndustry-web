package com.steelIndustry.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.User;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonUtil;
import com.steelIndustry.util.GeneratorUtil;

@Service("userService")
public class UserServiceImpl extends DataServiceImpl<User, Integer> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User save(User user) {
        return userDao.save(user);
    }
    
    @Override
    public User getUser(HttpServletRequest request, AjaxResult result) {
        try {
            long currentTime = System.currentTimeMillis();
            String instanceId = request.getHeader("instance_id");
            String accessToken = request.getHeader("access_token");
            String reqstarttime = request.getHeader("reqstarttime");
            String extratoken = request.getHeader("extratoken");
            if (CommonUtil.isEmpty(instanceId) || CommonUtil.isEmpty(accessToken)) {
                result.setErroCode(4000);
                result.setErroMsg("未登录或者登陆超时");
                return null;
            }
            else if (CommonUtil.isEmpty(reqstarttime) || CommonUtil.isEmpty(extratoken)) {
                result.setErroCode(7000);
                result.setErroMsg("非法访问");
                return null;
            }
            else if (Math.abs(currentTime - Long.valueOf(reqstarttime)) > 10000 || Math.abs(currentTime - Long.valueOf(reqstarttime)) < 10000) {
                result.setErroCode(8000);
                result.setErroMsg("本地时间异常，请校准本地时间");
                return null;
            }
            else {
                int md5Times = Integer.valueOf((reqstarttime + "").substring((reqstarttime + "").length() + 1));
                String extratokenComp = reqstarttime + "";
                for(int i = 0; i < md5Times; i++) {
                    extratokenComp = GeneratorUtil.md5(extratokenComp);
                }
                if(!extratokenComp.equals(extratoken)) {
                    result.setErroCode(7000);
                    result.setErroMsg("非法访问");
                    return null;
                }
                User user = userDao.getUserByAccessToken(accessToken);
                if (user == null || !user.getInstanceId().equals(instanceId) ) {
                    result.setErroCode(6000);
                    result.setErroMsg("非法的访问或其他客户端登录了您的账户！");
                    return null;
                }
                else if (user.getState() == 0) {
                    result.setErroCode(3000);
                    result.setErroMsg("您的账号已被禁用！");
                    return null;
                }
                
                return user;
            }
        } catch (Exception e) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
            return null;
        }
    }
    
    @Override
    public User getUser(long mobileNumber) {
        return userDao.getUserByMobileNumber(mobileNumber);
    }
    
    @Override
    public EntityJpaDao<User, Integer> getRepository() {
        return userDao;
    }

}
