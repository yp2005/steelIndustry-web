package com.steelIndustry.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.dao.RealNameAuthenticationDao;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.RealNameAuthentication;
import com.steelIndustry.service.RealNameAuthenticationService;

@Service("realNameAuthenticationService")
public class RealNameAuthenticationServiceImpl extends DataServiceImpl<RealNameAuthentication, Integer>
        implements RealNameAuthenticationService {

    @Resource
    private RealNameAuthenticationDao realNameAuthenticationDao;
    
    @Resource
    private UserDao userDao;

    @Override
    public RealNameAuthentication getRealNameAuthentication(int userId) {
        return realNameAuthenticationDao.getRealNameAuthentication(userId);
    }

    @Override
    public int updateRealNameAuthenticationState(int userId, short state) {
        int isSuccess = realNameAuthenticationDao.updateRealNameAuthenticationState(userId, state);
        if (isSuccess == 0) {
            return isSuccess;
        }
        isSuccess = userDao.updateUserRnaState(userId, state);
        return isSuccess;
    }

    @Override
    public List<RealNameAuthentication> getRealNameAuthenticationList(Conditions conditions) {
        String sql = "select * from real_name_authentication t where t.state=0 order by t.create_time desc";
        Map<String, Object> params = new HashMap<String, Object>();
        sql += " limit " + (conditions.getRowStartNumber() == null ? 0
                : conditions.getRowStartNumber()) + "," + (conditions.getRowCount() == null ? 10
                        : conditions.getRowCount());
        List<RealNameAuthentication> realNameAuthenticationList = realNameAuthenticationDao.executeNativeSQL(sql, params, RealNameAuthentication.class);
        return realNameAuthenticationList;
    
    }

    @Override
    public EntityJpaDao<RealNameAuthentication, Integer> getRepository() {
        return realNameAuthenticationDao;
    }

    @Override
    public RealNameAuthentication saveRealNameAuthentication(RealNameAuthentication realNameAuthentication) {
        return realNameAuthenticationDao.save(realNameAuthentication);
    }

}
