package com.steelIndustry.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.bo.Conditions;
import com.steelIndustry.dao.DeviceTypeDao;
import com.steelIndustry.dao.RelationTableDao;
import com.steelIndustry.dao.StoreDao;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.DeviceType;
import com.steelIndustry.model.RelationTable;
import com.steelIndustry.model.Store;
import com.steelIndustry.model.User;
import com.steelIndustry.service.StoreService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;

@Service("storeService")
public class StoreServiceImpl extends DataServiceImpl<Store, Integer> implements StoreService {

    @Resource
    private StoreDao storeDao;

    @Resource
    private RelationTableDao relationTableDao;
    
    @Resource
    private UserDao userDao;
    
    @Resource
    private DeviceTypeDao deviceTypeDao;
    
    @Override
    public EntityJpaDao<Store, Integer> getRepository() {
        return storeDao;
    }

    @Override
    public Store getStoreByUserId(int userId) {
        Store store = storeDao.getStoreByUserId(userId);
        if (store != null) {
            String sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'store' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name_environment'";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("relationMasterId", store.getId());
            List<Map<String, Object>> environmentPictureList = storeDao.findAllMapBySQL(sql, params);
            List<String> environmentPictures = new ArrayList();
            for (Iterator iterator = environmentPictureList.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                environmentPictures.add(CommonProperties.getInstance().getProperty("imgServer") + (String) map.get("imgName"));
            }
            store.setEnvironmentPictures(environmentPictures);
            sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'store' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name_product'";
            List<Map<String, Object>> productPictureList = storeDao.findAllMapBySQL(sql, params);
            List<String> productPictures = new ArrayList();
            for (Iterator iterator = productPictureList.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                productPictures.add(CommonProperties.getInstance().getProperty("imgServer") + (String) map.get("imgName"));
            }
            store.setProductPictures(productPictures);
            store.setDeviceTypes(deviceTypeDao.getDeviceTypesByStoreId(store.getId())); 
            User user = userDao.getOne(store.getUserId());
            store.setRealNameAuthentication(user.getRealNameAuthentication());
            store.setEnterpriseCertification(user.getEnterpriseCertification());
        }
        return store;
    }

    public List<Map<String, Object>> getStoreList(Conditions conditions) {
        String sql = "SELECT s.id id,s.user_id userId,s.store_name storeName,CONCAT_WS(' ',s.province_name,s.city_name,s.county_name) address,s.lng,s.lat,u.real_name_authentication realNameAuthentication,u.enterprise_certification enterpriseCertification,s.shop_sign_pictures shopSignPictures";
        if (conditions.getSortType() != null && conditions.getSortType() == 2) {
            sql += ",(6378.138 * 2 * asin(sqrt(pow(sin((s.lat * pi() / 180 - " + conditions.getLat() + " * pi() / 180) / 2),2) + cos(s.lat * pi() / 180) * cos(" + conditions.getLat() + " * pi() / 180) * pow(sin((s.lng * pi() / 180 - " + conditions.getLng() + " * pi() / 180) / 2),2))) * 1000) distance";
        }
        sql += " FROM store s LEFT JOIN relation_table typert ON typert.relation_master_id = s.id AND typert.relation_master_table = 'store' AND typert.relation_slave_table = 'device_type' LEFT JOIN device_type dt ON dt.id = typert.relation_slave_id,`user` u WHERE u.id = s.user_id AND u.state = 1 AND s.state = 1";
        Map<String, Object> params = new HashMap<String, Object>();
        if (!CommonUtil.isEmpty(conditions.getKeyword())) {
            sql += " and s.store_name like CONCAT('%',:storeName,'%')";
            params.put("storeName", conditions.getKeyword());
        }
        if (conditions.getProvinceId() != null && conditions.getProvinceId() != -1) {
            sql += " and s.province_id = :provinceId";
            params.put("provinceId", conditions.getProvinceId());
        }
        if (conditions.getCityId() != null && conditions.getCityId() != -1) {
            sql += " and s.city_id = :cityId";
            params.put("cityId", conditions.getCityId());
        }
        if (conditions.getCountyId() != null && conditions.getCountyId() != -1) {
            sql += " and s.county_id = :countyId";
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
            sql += " and dt.id in (" + ids + ")";
        }
        sql += " GROUP BY s.id";
        if (conditions.getSortType() != null) {
            if (conditions.getSortType() == 0) {
                sql += " order by s.sort asc";
            } else if (conditions.getSortType() == 1) {
                sql += " order by s.create_time desc";
            } else if (conditions.getSortType() == 2) {
                sql += " order by distance asc";
            }
        }
        sql += " limit " + (conditions.getRowStartNumber() == null ? 0
                : conditions.getRowStartNumber()) + "," + (conditions.getRowCount() == null ? 10
                        : conditions.getRowCount());
        List<Map<String, Object>> list = storeDao.findAllMapBySQL(sql, params);
        return list;
    }
    
    public int saveStore(Store store) {
        List<String> environmentPictures = store.getEnvironmentPictures();
        List<String> productPictures = store.getProductPictures();
        List<DeviceType> deviceTypes = store.getDeviceTypes();
        if (store.getSort() == null) {
            store.setSort(99);
        }
        store = storeDao.save(store);
        if (store == null) {
            return 0;
        }
        relationTableDao.deleteRelationTable("store", store.getId());
        if (environmentPictures != null) {
            for (Iterator iterator = environmentPictures.iterator(); iterator.hasNext();) {
                String environmentPicture = (String) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("store");
                rt.setRelationMasterId(store.getId());
                rt.setRelationSlaveTable("img_name_environment");
                rt.setImgName(environmentPicture);
                relationTableDao.save(rt);
            }
        }
        if (productPictures != null) {
            for (Iterator iterator = productPictures.iterator(); iterator.hasNext();) {
                String productPicture = (String) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("store");
                rt.setRelationMasterId(store.getId());
                rt.setRelationSlaveTable("img_name_product");
                rt.setImgName(productPicture);
                relationTableDao.save(rt);
            }
        }
        if (deviceTypes != null) {
            for (Iterator iterator = deviceTypes.iterator(); iterator.hasNext();) {
                DeviceType deviceType = (DeviceType) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("store");
                rt.setRelationMasterId(store.getId());
                rt.setRelationSlaveTable("device_type");
                rt.setRelationSlaveId(deviceType.getId());;
                relationTableDao.save(rt);
            }
        }
        return 1;
    }
    
    public int updateStoreBv(int id) {
        return storeDao.updateStoreBv(id);
    }
    
    public int updateStoreCt(int id) {
        return storeDao.updateStoreCt(id);
    }

    @Override
    public int updateStoreState(int id, short state) {
        return storeDao.updateStoreState(id, state);
    }

}
