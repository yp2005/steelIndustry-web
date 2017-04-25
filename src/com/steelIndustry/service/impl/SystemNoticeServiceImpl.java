package com.steelIndustry.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.dao.SystemNoticeDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.SystemNotice;
import com.steelIndustry.service.SystemNoticeService;

@Service("systemNoticeService")
public class SystemNoticeServiceImpl extends DataServiceImpl<SystemNotice, Integer> implements SystemNoticeService {

    @Resource
    private SystemNoticeDao systemNoticeDao;

    @Override
    public EntityJpaDao<SystemNotice, Integer> getRepository() {
        return systemNoticeDao;
    }

    public List<SystemNotice> getSystemNoticeList() {
        String sql = "select * from system_notice order by create_time desc limit 0,5";
        return systemNoticeDao.executeNativeSQL(sql, new HashMap(), SystemNotice.class);
    }
    
    public List<SystemNotice> getSystemNoticeListAll() {
        String sql = "select * from system_notice order by create_time desc";
        return systemNoticeDao.executeNativeSQL(sql, new HashMap(), SystemNotice.class);
    }

    public SystemNotice getSystemNotice(int id) {
        return systemNoticeDao.getSystemNotice(id);
    }

    public SystemNotice saveSystemNotice(SystemNotice systemNotice) {
        return systemNoticeDao.save(systemNotice);
    }

}
