package com.steelIndustry.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.Conditions;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.User;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;
import com.steelIndustry.util.GeneratorUtil;

@Service("userService")
public class UserServiceImpl extends DataServiceImpl<User, Integer> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUser(HttpServletRequest request, AjaxResult result) {
        try {
            long currentTime = System.currentTimeMillis();
            String instanceId = request.getHeader("instance_id");
            String accessToken = request.getHeader("access_token");
            String reqstarttime = request.getHeader("req_start_time");
            String extratoken = request.getHeader("extra_token");
            if (CommonUtil.isEmpty(instanceId) || CommonUtil.isEmpty(accessToken)) {
                result.setErroCode(4000);
                result.setErroMsg("未登录或者登陆超时");
                return null;
            } else if (CommonUtil.isEmpty(reqstarttime) || CommonUtil.isEmpty(extratoken)) {
                result.setErroCode(7000);
                result.setErroMsg("非法访问");
                return null;
            } else if (Math.abs(currentTime - Long.valueOf(reqstarttime)) > CommonProperties.getInstance().getPropertyForInt("timeDifference") * 1000) {
                result.setErroCode(8000);
                result.setErroMsg("本地时间异常，请校准本地时间");
                return null;
            } else {
                int md5Times = Integer.valueOf((reqstarttime + "").substring((reqstarttime + "").length() - 1));
                if(md5Times == 0) {
                    md5Times = 1;
                }
                String extratokenComp = reqstarttime + "";
                for (int i = 0; i < md5Times; i++) {
                    extratokenComp = GeneratorUtil.md5(extratokenComp);
                }
                if (!extratokenComp.equals(extratoken)) {
                    result.setErroCode(7000);
                    result.setErroMsg("非法访问");
                    return null;
                }
                User user = userDao.getUserByAccessToken(accessToken);
                if (user == null || !user.getInstanceId().equals(instanceId)) {
                    result.setErroCode(6000);
                    result.setErroMsg("非法的访问或其他客户端登录了您的账户！");
                    return null;
                } else if (user.getState() == 0) {
                    result.setErroCode(3000);
                    result.setErroMsg("您的账号已被禁用！");
                    return null;
                }
                userDao.updateLatestLoginTime(user.getId(), new Timestamp(currentTime));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
            return null;
        }
    }

    public Map<String, Object> getUserList(Conditions conditions) {
        Map<String, Object> resultMap = new HashMap<>();
        String sql = "select * from user t where 1=1";
        Map<String, Object> params = new HashMap<String, Object>();
        if (CommonUtil.isNotEmpty(conditions.getKeyword())) {
            sql += " and t.user_name like CONCAT('%',:keyword,'%') or t.mobile_number like CONCAT('%',:keyword,'%')";
            params.put("keyword", conditions.getKeyword());
        }
        if (CommonUtil.isNotEmpty(conditions.getRealNameAuthentication())) {
            sql += " and t.real_name_authentication=:realNameAuthentication";
            params.put("realNameAuthentication", conditions.getRealNameAuthentication());
        }
        if (CommonUtil.isNotEmpty(conditions.getEnterpriseCertification())) {
            sql += " and t.enterprise_certification=:enterpriseCertification";
            params.put("enterpriseCertification", conditions.getEnterpriseCertification());
        }
        if (conditions.getSortType() != null) {
            if (conditions.getSortType() == 0) {
                sql += " order by t.latest_login_time desc";
            } else if (conditions.getSortType() == 1) {
                sql += " order by t.create_time desc";
            } else if (conditions.getSortType() == 2) {
                sql += " order by t.update_time desc";
            }
        }
        sql += " limit " + (conditions.getRowStartNumber() == null ? 0 : conditions.getRowStartNumber()) + ","
                + (conditions.getRowCount() == null ? 10 : conditions.getRowCount());
        List<User> userList = userDao.executeNativeSQL(sql, params, User.class);
        resultMap.put("userList", userList);
        resultMap.put("userNumber", userDao.count());
        resultMap.put("activeUserNumber",  userDao.getActiveUserNumber(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7)));
        return resultMap;
    }

    public int updateLatestLoginTime(HttpServletRequest request) {
        try {
            long currentTime = System.currentTimeMillis();
            String instanceId = request.getHeader("instance_id");
            String accessToken = request.getHeader("access_token");
            String reqstarttime = request.getHeader("req_start_time");
            String extratoken = request.getHeader("extra_token");
            System.out.println(Math.abs(currentTime - Long.valueOf(reqstarttime)));
            System.out.println(Math.abs(currentTime - Long.valueOf(reqstarttime)));
            if (CommonUtil.isEmpty(instanceId) || CommonUtil.isEmpty(accessToken) || CommonUtil.isEmpty(reqstarttime)
                    || CommonUtil.isEmpty(extratoken) || Math.abs(currentTime - Long.valueOf(reqstarttime)) > 10000) {
                return 0;
            } else {
                User user = userDao.getUserByAccessToken(accessToken);
                if (user == null || !user.getInstanceId().equals(instanceId) || user.getState() == 0) {
                    return 0;
                }
                return userDao.updateLatestLoginTime(user.getId(), new Timestamp(currentTime));
            }
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateUserState(int id, short state) {
        return userDao.updateUserState(id, state);
    }
    
    @Override
    public int updateShareState(int userId, short shareState) {
        return userDao.updateShareState(userId, shareState);
    }
    @Override
    public int updateUserAvatar(int id, String avatar) {
        return userDao.updateUserAvatar(id, avatar);
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
