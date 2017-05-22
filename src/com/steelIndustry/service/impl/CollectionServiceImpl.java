package com.steelIndustry.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.dao.CollectionDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.Collection;
import com.steelIndustry.service.CollectionService;

@Service("collectionService")
public class CollectionServiceImpl extends DataServiceImpl<Collection, Integer> implements CollectionService {

    @Resource
    private CollectionDao collectionDao;

    @Override
    public EntityJpaDao<Collection, Integer> getRepository() {
        return collectionDao;
    }

    public List<Map<String, Object>> getMasterCards(int userId) {
        String sql = "SELECT mc.id id,mc.user_id userId,mc.card_title cardTitle,mc.lng,mc.lat,u.real_name_authentication realNameAuthentication,GROUP_CONCAT(DISTINCT wt.type_name SEPARATOR '、') workerTypes,GROUP_CONCAT(DISTINCT ad.area_name SEPARATOR '、') serviceArea,imgrt.img_name imgName";
        sql += " FROM master_card mc LEFT JOIN relation_table typert ON typert.relation_master_id = mc.id AND typert.relation_master_table = 'master_card' AND typert.relation_slave_table = 'worker_type' LEFT JOIN worker_type wt ON wt.id = typert.relation_slave_id LEFT JOIN relation_table adrt ON adrt.relation_master_id = mc.id AND adrt.relation_master_table = 'master_card' AND adrt.relation_slave_table = 'area_data' LEFT JOIN area_data ad ON ad.area_id = adrt.relation_slave_id LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = mc.id AND imgrt.relation_master_table = 'master_card' AND imgrt.relation_slave_table = 'img_name', `user` u,collection cl WHERE u.id = mc.user_id AND u.state = 1 AND mc.state=1 AND mc.is_working=0";
        Map<String, Object> params = new HashMap<String, Object>();
        sql += " and cl.user_id=:userId and cl.type='card' and cl.collect_id=mc.id";
        params.put("userId", userId);
        sql += " GROUP BY mc.id order by cl.collect_time desc";
        List<Map<String, Object>> list = collectionDao.findAllMapBySQL(sql, params);
        return list;
    }

    public List<Map<String, Object>> getEmploymentDemands(int userId) {
        String sql = "SELECT ed.id id,ed.user_id userId,ed.demand_title demandTitle,ed.create_time createTime,CONCAT_WS(' ',ed.province_name,ed.city_name,ed.county_name) address,ed.lng,ed.lat,u.real_name_authentication realNameAuthentication,u.enterprise_certification enterpriseCertification,imgrt.img_name imgName";
        sql += " FROM employment_demand ed LEFT JOIN relation_table typert ON typert.relation_master_id = ed.id AND typert.relation_master_table = 'employment_demand' AND typert.relation_slave_table = 'worker_type' LEFT JOIN worker_type wt ON wt.id = typert.relation_slave_id LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = ed.id AND imgrt.relation_master_table = 'employment_demand' AND imgrt.relation_slave_table = 'img_name',`user` u,collection cl WHERE u.id = ed.user_id AND u.state = 1 AND ed.state = 1 AND ed.due_time > CURRENT_TIMESTAMP";
        Map<String, Object> params = new HashMap<String, Object>();
        sql += " and cl.user_id=:userId and cl.type='work' and cl.collect_id=ed.id";
        params.put("userId", userId);
        sql += " GROUP BY ed.id order by cl.collect_time desc";
        List<Map<String, Object>> list = collectionDao.findAllMapBySQL(sql, params);
        return list;
    }

    public List<Map<String, Object>> getProjects(int userId) {
        String sql = "SELECT p.id id,p.user_id userId,p.project_name projectName,p.create_time createTime,CONCAT_WS(' ',p.province_name,p.city_name,p.county_name) address,p.lng,p.lat,u.real_name_authentication realNameAuthentication,u.enterprise_certification enterpriseCertification,imgrt.img_name imgName";
        sql += " FROM project p LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = p.id AND imgrt.relation_master_table = 'project' AND imgrt.relation_slave_table = 'img_name',`user` u,collection cl WHERE u.id = p.user_id AND u.state = 1 AND p.state = 1 AND p.due_time > CURRENT_TIMESTAMP";
        Map<String, Object> params = new HashMap<String, Object>();
        sql += " and cl.user_id=:userId and cl.type='project' and cl.collect_id=p.id";
        params.put("userId", userId);
        sql += " GROUP BY p.id order by cl.collect_time desc";
        List<Map<String, Object>> list = collectionDao.findAllMapBySQL(sql, params);
        return list;
    }

    public List<Map<String, Object>> getStores(int userId) {
        String sql = "SELECT s.id id,s.user_id userId,s.store_name storeName,CONCAT_WS(' ',s.province_name,s.city_name,s.county_name) address,s.lng,s.lat,u.real_name_authentication realNameAuthentication,u.enterprise_certification enterpriseCertification,s.shop_sign_pictures shopSignPictures";
        sql += " FROM store s LEFT JOIN relation_table typert ON typert.relation_master_id = s.id AND typert.relation_master_table = 'store' AND typert.relation_slave_table = 'device_type' LEFT JOIN device_type dt ON dt.id = typert.relation_slave_id,`user` u,collection cl WHERE u.id = s.user_id AND u.state = 1 AND s.state = 1";
        Map<String, Object> params = new HashMap<String, Object>();
        sql += " and cl.user_id=:userId and cl.type='store' and cl.collect_id=s.id";
        params.put("userId", userId);
        sql += " GROUP BY s.id order by cl.collect_time desc";
        List<Map<String, Object>> list = collectionDao.findAllMapBySQL(sql, params);
        return list;
    }

    public int deleteCollection(int userId, String type, int collectId) {
        return collectionDao.deleteCollection(userId, type, collectId);
    }
    
    public Collection addCollection(int userId, String type, int collectId) {
        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setType(type);
        collection.setCollectId(collectId);
        return collectionDao.save(collection);
    }
    
    public short isCollected(int userId, String type, int collectId) {
        String sql = "select id from collection where user_id=:userId and type=:type and collect_id=:collectId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("type", type);
        params.put("collectId", collectId);
        List result = collectionDao.executeNativeSQL(sql, params);
        if (result != null && result.size() > 0) {
            return 1;
        }
        else {
            return 0;
        }
    }

}
