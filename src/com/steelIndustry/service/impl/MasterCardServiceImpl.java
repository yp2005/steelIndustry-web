package com.steelIndustry.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.dao.AreaDataDao;
import com.steelIndustry.dao.MasterCardDao;
import com.steelIndustry.dao.RelationTableDao;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.dao.WorkerTypeDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.AreaData;
import com.steelIndustry.model.MasterCard;
import com.steelIndustry.model.RelationTable;
import com.steelIndustry.model.User;
import com.steelIndustry.model.WorkerType;
import com.steelIndustry.service.MasterCardService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;

@Service("masterCardService")
public class MasterCardServiceImpl extends DataServiceImpl<MasterCard, Integer> implements MasterCardService {

    @Resource
    private MasterCardDao masterCardDao;

    @Resource
    private RelationTableDao relationTableDao;
    
    @Resource
    private WorkerTypeDao workerTypeDao;
    
    @Resource
    private AreaDataDao areaDataDao;
    
    @Resource
    private UserDao userDao;
    
    @Override
    public EntityJpaDao<MasterCard, Integer> getRepository() {
        return masterCardDao;
    }

    @Override
    public MasterCard getMasterCardByUserId(int userId) {
        MasterCard masterCard = masterCardDao.getMasterCardByUserId(userId);
        if (masterCard != null) {
            String sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'master_card' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name'";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("relationMasterId", masterCard.getId());
            List<Map<String, Object>> pictureList = masterCardDao.findAllMapBySQL(sql, params);
            List<String> pictures = new ArrayList();
            for (Iterator iterator = pictureList.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                pictures.add(CommonProperties.getInstance().getProperty("imgServer") + (String) map.get("imgName"));
            }
            masterCard.setPictures(pictures);
            masterCard.setWorkerTypes(workerTypeDao.getWorkerTypesByRelation(masterCard.getId(), "master_card")); 
            masterCard.setServiceArea(areaDataDao.getAreaDatasByRelation(masterCard.getId(), "master_card"));
            User user = userDao.getOne(userId);
            masterCard.setRealNameAuthentication(user.getRealNameAuthentication());
        }
        return masterCard;
    }

    public List<Map<String, Object>> getMasterCardList(Conditions conditions) {
        String sql = "SELECT mc.id id,  mc.user_id userId,mc.card_title cardTitle,mc.lng,mc.lat,u.real_name_authentication realNameAuthentication,GROUP_CONCAT(DISTINCT wt.type_name SEPARATOR '、') workerTypes,GROUP_CONCAT(DISTINCT ad.area_name SEPARATOR '、') serviceArea,imgrt.img_name imgName";
        if (conditions.getSortType() != null && conditions.getSortType() == 2) {
            sql += ",(6378.138 * 2 * asin(sqrt(pow(sin((mc.lat * pi() / 180 - " + conditions.getLat() + " * pi() / 180) / 2),2) + cos(mc.lat * pi() / 180) * cos(" + conditions.getLat() + " * pi() / 180) * pow(sin((mc.lng * pi() / 180 - " + conditions.getLng() + " * pi() / 180) / 2),2))) * 1000) distance";
        }
        sql += " FROM master_card mc LEFT JOIN relation_table typert ON typert.relation_master_id = mc.id AND typert.relation_master_table = 'master_card' AND typert.relation_slave_table = 'worker_type' LEFT JOIN worker_type wt ON wt.id = typert.relation_slave_id LEFT JOIN relation_table adrt ON adrt.relation_master_id = mc.id AND adrt.relation_master_table = 'master_card' AND adrt.relation_slave_table = 'area_data' LEFT JOIN area_data ad ON ad.area_id = adrt.relation_slave_id LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = mc.id AND imgrt.relation_master_table = 'master_card' AND imgrt.relation_slave_table = 'img_name', `user` u WHERE u.id = mc.user_id AND u.state = 1 AND mc.state=1 AND mc.is_working=0";
        Map<String, Object> params = new HashMap<String, Object>();
        if (!CommonUtil.isEmpty(conditions.getKeyword())) {
            sql += " and mc.card_title like CONCAT('%',:cardTitle,'%')";
            params.put("cardTitle", conditions.getKeyword());
        }
        if (conditions.getProvinceId() != null && conditions.getProvinceId() != -1) {
            sql += " and mc.province_id = :provinceId";
            params.put("provinceId", conditions.getProvinceId());
        }
        if (conditions.getCityId() != null && conditions.getCityId() != -1) {
            sql += " and mc.city_id = :cityId";
            params.put("cityId", conditions.getCityId());
        }
        if (conditions.getCountyId() != null && conditions.getCountyId() != -1) {
            sql += " and mc.county_id = :countyId";
            params.put("countyId", conditions.getCountyId());
        }
        if(conditions.getTypeIds() != null && conditions.getTypeIds().size() > 0) {
            List<Integer> typeIds = conditions.getTypeIds();
            String ids = "";
            for (Iterator iterator = typeIds.iterator(); iterator.hasNext();) {
                Integer id = (Integer) iterator.next();
                if (ids.equals("")) {
                    ids = "" + id;
                }
                else {
                    ids += "," + id;
                }
            }
            sql += " and wt.id in (" + ids + ")";
        }
        sql += " GROUP BY mc.id";
        if (conditions.getSortType() != null) {
            if (conditions.getSortType() == 0) {
                sql += " order by mc.sort asc";
            } else if (conditions.getSortType() == 1) {
                sql += " order by mc.create_time desc";
            } else if (conditions.getSortType() == 2) {
                sql += " order by distance asc";
            }
        }
        sql += " limit " + (conditions.getRowStartNumber() == null ? 0
                : conditions.getRowStartNumber()) + "," + (conditions.getRowCount() == null ? 10
                        : conditions.getRowCount());
        List<Map<String, Object>> list = masterCardDao.findAllMapBySQL(sql, params);
        return list;
    }

    public int saveMasterCard(MasterCard masterCard) {
        List<String> pictures = masterCard.getPictures();
        List<WorkerType> workerTypes = masterCard.getWorkerTypes();
        List<AreaData> serviceArea = masterCard.getServiceArea();
        if (masterCard.getSort() == null) {
            masterCard.setSort(99);
        }
        masterCard = masterCardDao.save(masterCard);
        if (masterCard == null) {
            return 0;
        }
        relationTableDao.deleteRelationTable("master_card", masterCard.getId());
        if (pictures != null) {
            for (Iterator iterator = pictures.iterator(); iterator.hasNext();) {
                String picture = (String) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("master_card");
                rt.setRelationMasterId(masterCard.getId());
                rt.setRelationSlaveTable("img_name");
                rt.setImgName(picture);
                relationTableDao.save(rt);
            }
        }
        if (workerTypes != null) {
            for (Iterator iterator = workerTypes.iterator(); iterator.hasNext();) {
                WorkerType workerType = (WorkerType) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("master_card");
                rt.setRelationMasterId(masterCard.getId());
                rt.setRelationSlaveTable("worker_type");
                rt.setRelationSlaveId(workerType.getId());;
                relationTableDao.save(rt);
            }
        }
        if (serviceArea != null) {
            for (Iterator iterator = serviceArea.iterator(); iterator.hasNext();) {
                AreaData areaData = (AreaData) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("master_card");
                rt.setRelationMasterId(masterCard.getId());
                rt.setRelationSlaveTable("area_data");
                rt.setRelationSlaveId(areaData.getAreaId());
                relationTableDao.save(rt);
            }
        }
        return 1;
    }
    
    public int updateMasterCardBv(int id) {
        return masterCardDao.updateMasterCardBv(id);
    }
    
    public int updateMasterCardCt(int id) {
        return masterCardDao.updateMasterCardCt(id);
    }
    
    public int updateMasterCardWorkState(int id, short isWorking) {
        return masterCardDao.updateMasterCardWorkState(id, isWorking);
    }

}
