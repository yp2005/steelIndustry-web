package com.steelIndustry.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.dao.AreaDataDao;
import com.steelIndustry.dao.EmploymentDemandDao;
import com.steelIndustry.dao.RelationTableDao;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.dao.WorkerTypeDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.AreaData;
import com.steelIndustry.model.EmploymentDemand;
import com.steelIndustry.model.Project;
import com.steelIndustry.model.RelationTable;
import com.steelIndustry.model.User;
import com.steelIndustry.model.WorkerType;
import com.steelIndustry.service.EmploymentDemandService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;

@Service("employmentDemandService")
public class EmploymentDemandServiceImpl extends DataServiceImpl<EmploymentDemand, Integer> implements EmploymentDemandService {

    @Resource
    private EmploymentDemandDao employmentDemandDao;

    @Resource
    private RelationTableDao relationTableDao;
    
    @Resource
    private WorkerTypeDao workerTypeDao;
    
    @Resource
    private AreaDataDao areaDataDao;
    
    @Resource
    private UserDao userDao;
    
    @Override
    public EntityJpaDao<EmploymentDemand, Integer> getRepository() {
        return employmentDemandDao;
    }

    @Override
    public EmploymentDemand getEmploymentDemandById(int id) {
        EmploymentDemand employmentDemand = employmentDemandDao.getEmploymentDemandById(id);
        if (employmentDemand != null) {
            String sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'employment_demand' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name'";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("relationMasterId", employmentDemand.getId());
            List<Map<String, Object>> pictureList = employmentDemandDao.findAllMapBySQL(sql, params);
            List<String> pictures = new ArrayList();
            for (Iterator iterator = pictureList.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                pictures.add(CommonProperties.getInstance().getProperty("imgServer") + (String) map.get("imgName"));
            }
            employmentDemand.setPictures(pictures);
            employmentDemand.setWorkerTypes(workerTypeDao.getWorkerTypesByRelation(employmentDemand.getId(), "employment_demand")); 
            User user = userDao.getOne(employmentDemand.getUserId());
            employmentDemand.setRealNameAuthentication(user.getRealNameAuthentication());
            employmentDemand.setEnterpriseCertification(user.getEnterpriseCertification());
        }
        return employmentDemand;
    }

    public List<Map<String, Object>> getEmploymentDemandList(Conditions conditions) {
        String sql = "SELECT ed.id id,ed.user_id userId,ed.demand_title demandTitle,ed.create_time createTime,CONCAT_WS(' ',ed.province_name,ed.city_name,ed.county_name) address,ed.lng,ed.lat,u.real_name_authentication realNameAuthentication,u.enterprise_certification enterpriseCertification,imgrt.img_name imgName";
        if (conditions.getSortType() != null && conditions.getSortType() == 2) {
            sql += ",(6378.138 * 2 * asin(sqrt(pow(sin((ed.lat * pi() / 180 - " + conditions.getLat() + " * pi() / 180) / 2),2) + cos(ed.lat * pi() / 180) * cos(" + conditions.getLat() + " * pi() / 180) * pow(sin((ed.lng * pi() / 180 - " + conditions.getLng() + " * pi() / 180) / 2),2))) * 1000) distance";
        }
        sql += " FROM employment_demand ed LEFT JOIN relation_table typert ON typert.relation_master_id = ed.id AND typert.relation_master_table = 'employment_demand' AND typert.relation_slave_table = 'worker_type' LEFT JOIN worker_type wt ON wt.id = typert.relation_slave_id LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = ed.id AND imgrt.relation_master_table = 'employment_demand' AND imgrt.relation_slave_table = 'img_name',`user` u WHERE u.id = ed.user_id AND u.state = 1 AND ed.state = 1 AND ed.due_time > CURRENT_TIMESTAMP";
        Map<String, Object> params = new HashMap<String, Object>();
        if (!CommonUtil.isEmpty(conditions.getKeyword())) {
            sql += " and ed.demand_title like CONCAT('%',:demandTitle,'%')";
            params.put("demandTitle", conditions.getKeyword());
        }
        if (conditions.getProvinceId() != null && conditions.getProvinceId() != -1) {
            sql += " and ed.province_id = :provinceId";
            params.put("provinceId", conditions.getProvinceId());
        }
        if (conditions.getCityId() != null && conditions.getCityId() != -1) {
            sql += " and ed.city_id = :cityId";
            params.put("cityId", conditions.getCityId());
        }
        if (conditions.getCountyId() != null && conditions.getCountyId() != -1) {
            sql += " and ed.county_id = :countyId";
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
        sql += " GROUP BY ed.id";
        if (conditions.getSortType() != null) {
            if (conditions.getSortType() == 0) {
                sql += " order by ed.sort asc";
            } else if (conditions.getSortType() == 1) {
                sql += " order by ed.create_time desc";
            } else if (conditions.getSortType() == 2) {
                sql += " order by distance asc";
            }
        }
        sql += " limit " + (conditions.getRowStartNumber() == null ? 0
                : conditions.getRowStartNumber()) + "," + (conditions.getRowCount() == null ? 10
                        : conditions.getRowCount());
        List<Map<String, Object>> list = employmentDemandDao.findAllMapBySQL(sql, params);
        return list;
    }
    
    public List<Map<String, Object>> getHotWork() {

        String sql = "SELECT ed.id id,ed.user_id userId,ed.demand_title demandTitle,ed.create_time createTime,CONCAT_WS(' ',ed.province_name,ed.city_name,ed.county_name) address,ed.lng,ed.lat,u.real_name_authentication realNameAuthentication,u.enterprise_certification enterpriseCertification,imgrt.img_name imgName";
        sql += " FROM employment_demand ed LEFT JOIN relation_table typert ON typert.relation_master_id = ed.id AND typert.relation_master_table = 'employment_demand' AND typert.relation_slave_table = 'worker_type' LEFT JOIN worker_type wt ON wt.id = typert.relation_slave_id LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = ed.id AND imgrt.relation_master_table = 'employment_demand' AND imgrt.relation_slave_table = 'img_name',`user` u WHERE u.id = ed.user_id AND u.state = 1 AND ed.state = 1 AND ed.due_time > CURRENT_TIMESTAMP";
        Map<String, Object> params = new HashMap<String, Object>();
        sql += " GROUP BY ed.id";
        sql += " order by ed.browse_volume desc";
        sql += " limit 0,5";
        List<Map<String, Object>> list = employmentDemandDao.findAllMapBySQL(sql, params);
        return list;
    
    }
    
    public List<EmploymentDemand> getUserEmploymentDemand(User user) {
        List<EmploymentDemand> employmentDemands = employmentDemandDao.getUserEmploymentDemand(user.getId());
        if (employmentDemands != null && employmentDemands.size() > 0) {
            for (Iterator iterator = employmentDemands.iterator(); iterator.hasNext();) {
                EmploymentDemand employmentDemand = (EmploymentDemand) iterator.next();
                String sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'employment_demand' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name'";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("relationMasterId", employmentDemand.getId());
                List<Map<String, Object>> pictureList = employmentDemandDao.findAllMapBySQL(sql, params);
                List<String> pictures = new ArrayList();
                for (Iterator iterator1 = pictureList.iterator(); iterator1.hasNext();) {
                    Map<String, Object> map = (Map<String, Object>) iterator1.next();
                    pictures.add(CommonProperties.getInstance().getProperty("imgServer") + (String) map.get("imgName"));
                }
                employmentDemand.setPictures(pictures);
                employmentDemand.setWorkerTypes(workerTypeDao.getWorkerTypesByRelation(employmentDemand.getId(), "employment_demand")); 
                employmentDemand.setRealNameAuthentication(user.getRealNameAuthentication());
                employmentDemand.setEnterpriseCertification(user.getEnterpriseCertification());
            }
        }
        return employmentDemands;
    }

    public int saveEmploymentDemand(EmploymentDemand employmentDemand) {
        List<String> pictures = employmentDemand.getPictures();
        List<WorkerType> workerTypes = employmentDemand.getWorkerTypes();
        if (employmentDemand.getSort() == null) {
            employmentDemand.setSort(99);
        }
        employmentDemand.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        employmentDemand = employmentDemandDao.save(employmentDemand);
        if (employmentDemand == null) {
            return 0;
        }
        relationTableDao.deleteRelationTable("employment_demand", employmentDemand.getId());
        if (pictures != null) {
            for (Iterator iterator = pictures.iterator(); iterator.hasNext();) {
                String picture = (String) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("employment_demand");
                rt.setRelationMasterId(employmentDemand.getId());
                rt.setRelationSlaveTable("img_name");
                rt.setImgName(picture);
                relationTableDao.save(rt);
            }
        }
        if (workerTypes != null) {
            for (Iterator iterator = workerTypes.iterator(); iterator.hasNext();) {
                WorkerType workerType = (WorkerType) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("employment_demand");
                rt.setRelationMasterId(employmentDemand.getId());
                rt.setRelationSlaveTable("worker_type");
                rt.setRelationSlaveId(workerType.getId());;
                relationTableDao.save(rt);
            }
        }
        return 1;
    }
    
    public int updateEmploymentDemandBv(int id) {
        return employmentDemandDao.updateEmploymentDemandBv(id);
    }
    
    public int updateEmploymentDemandCt(int id) {
        return employmentDemandDao.updateEmploymentDemandCt(id);
    }
    
    @Override
    public int updateEmploymentDemandState(int id, short state) {
        return employmentDemandDao.updateEmploymentDemandState(id, state);
    }

}
