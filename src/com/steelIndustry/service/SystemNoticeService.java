package com.steelIndustry.service;

import java.util.List;

import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.SystemNotice;


public interface SystemNoticeService extends DataService<SystemNotice, Integer> {
    public List<SystemNotice> getSystemNoticeList();
    
    public SystemNotice getSystemNotice(int id);
    
    public SystemNotice saveSystemNotice(SystemNotice systemNotice);
    
    public List<SystemNotice> getSystemNoticeListAll();
}
