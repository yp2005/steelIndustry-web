package com.steelIndustry.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.dao.EnterpriseCertificationDao;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.EnterpriseCertification;
import com.steelIndustry.service.EnterpriseCertificationService;

@Service("enterpriseCertificationService")
public class EnterpriseCertificationServiceImpl extends DataServiceImpl<EnterpriseCertification, Integer>
        implements EnterpriseCertificationService {

    @Resource
    private EnterpriseCertificationDao enterpriseCertificationDao;
    
    @Resource
    private UserDao userDao;

    @Override
    public EnterpriseCertification getEnterpriseCertification(int userId) {
        return enterpriseCertificationDao.getEnterpriseCertification(userId);
    }

    @Override
    public int updateEnterpriseCertificationState(int userId, short state) {
        int isSuccess = enterpriseCertificationDao.updateEnterpriseCertificationState(userId, state);
        if (isSuccess == 0) {
            return isSuccess;
        }
        isSuccess = userDao.updateUserEcState(userId, state);
        return isSuccess;
    }

    @Override
    public List<EnterpriseCertification> getEnterpriseCertificationList(Conditions conditions) {
        String sql = "select * from enterprise_certification t where t.state=0 order by t.create_time desc";
        Map<String, Object> params = new HashMap<String, Object>();
        sql += " limit " + (conditions.getRowStartNumber() == null ? 0
                : conditions.getRowStartNumber()) + "," + (conditions.getRowCount() == null ? 10
                        : conditions.getRowCount());
        List<EnterpriseCertification> enterpriseCertificationList = enterpriseCertificationDao.executeNativeSQL(sql, params, EnterpriseCertification.class);
        return enterpriseCertificationList;
    
    }

    @Override
    public EntityJpaDao<EnterpriseCertification, Integer> getRepository() {
        return enterpriseCertificationDao;
    }

}
