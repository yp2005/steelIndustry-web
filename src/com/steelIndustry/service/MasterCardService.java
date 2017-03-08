package com.steelIndustry.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.framework.service.DataService;
import com.steelIndustry.model.MasterCard;

public interface MasterCardService extends DataService<MasterCard, Integer> {
    public MasterCard getMasterCardByUserId(int userId);

    public int saveMasterCard(MasterCard masterCard);
    
    public List<Map<String, Object>> getMasterCardList(Conditions conditions);
    
    public int updateMasterCardBv(int id);
    
    public int updateMasterCardCt(int id);
    
    public int updateMasterCardWorkState(int userId, short isWorking);
    
    public int updateMasterCardState(int id, short state);
}
