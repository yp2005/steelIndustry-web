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
import com.steelIndustry.dao.DeviceDao;
import com.steelIndustry.dao.DeviceTypeDao;
import com.steelIndustry.dao.RelationTableDao;
import com.steelIndustry.dao.SettingsDao;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.Device;
import com.steelIndustry.model.DeviceType;
import com.steelIndustry.model.RelationTable;
import com.steelIndustry.model.User;
import com.steelIndustry.service.DeviceService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;

@Service("deviceService")
public class DeviceServiceImpl extends DataServiceImpl<Device, Integer> implements DeviceService {

    @Resource
    private DeviceDao deviceDao;

    @Resource
    private RelationTableDao relationTableDao;
    
    @Resource
    private UserDao userDao;
    
    @Resource
    private SettingsDao settingsDao;
    
    @Resource
    private DeviceTypeDao deviceTypeDao;
    
    @Override
    public EntityJpaDao<Device, Integer> getRepository() {
        return deviceDao;
    }

    @Override
    public Device getDeviceById(int id) {
        Device device = deviceDao.findOne(id);
        String imgServer = CommonProperties.getInstance().getProperty("imgServer");
        if (device != null) {
            String sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'device' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name'";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("relationMasterId", device.getId());
            List<Map<String, Object>> pictureList = deviceDao.findAllMapBySQL(sql, params);
            List<String> pictures = new ArrayList();
            for (Iterator iterator = pictureList.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                pictures.add(imgServer + (String) map.get("imgName"));
            }
            device.setPictures(pictures);
            device.setImgServer(CommonProperties.getInstance().getProperty("imgServer"));
            device.setDeviceTypes(deviceTypeDao.getDeviceTypesByDeviceId(device.getId())); 
            User user = userDao.getOne(device.getUserId());
            device.setRealNameAuthentication(user.getRealNameAuthentication());
            device.setEnterpriseCertification(user.getEnterpriseCertification());
        }
        return device;
    }
    
    @Override
    public List<Device> getUserDevice(User user) {
        List<Device> devices = deviceDao.getUserDevice(user.getId());
        if (devices != null && devices.size() > 0) {
            for (Iterator iterator = devices.iterator(); iterator.hasNext();) {
                Device device = (Device) iterator.next();
                String sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'device' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name'";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("relationMasterId", device.getId());
                List<Map<String, Object>> pictureList = deviceDao.findAllMapBySQL(sql, params);
                List<String> pictures = new ArrayList();
                for (Iterator iterator1 = pictureList.iterator(); iterator1.hasNext();) {
                    Map<String, Object> map = (Map<String, Object>) iterator1.next();
                    pictures.add(CommonProperties.getInstance().getProperty("imgServer") + (String) map.get("imgName"));
                }
                device.setPictures(pictures);
                device.setImgServer(CommonProperties.getInstance().getProperty("imgServer"));
                device.setDeviceTypes(deviceTypeDao.getDeviceTypesByDeviceId(device.getId())); 
                device.setRealNameAuthentication(user.getRealNameAuthentication());
                device.setEnterpriseCertification(user.getEnterpriseCertification());
            }
        }
        return devices;
    }

    public List<Map<String, Object>> getDeviceList(Conditions conditions) {
        String sql = "SELECT s.id id,s.user_id userId,s.device_name deviceName,CONCAT_WS(' ',s.province_name,s.city_name,s.county_name) address,s.lng,s.lat,CAST(u.real_name_authentication AS CHAR) realNameAuthentication,CAST(u.enterprise_certification AS CHAR) enterpriseCertification,imgrt.img_name imgName";
        if (conditions.getSortType() != null && conditions.getSortType() == 2) {
            sql += ",(6378.138 * 2 * asin(sqrt(pow(sin((s.lat * pi() / 180 - " + conditions.getLat() + " * pi() / 180) / 2),2) + cos(s.lat * pi() / 180) * cos(" + conditions.getLat() + " * pi() / 180) * pow(sin((s.lng * pi() / 180 - " + conditions.getLng() + " * pi() / 180) / 2),2))) * 1000) distance";
        }
        sql += " FROM device s LEFT JOIN relation_table typert ON typert.relation_master_id = s.id AND typert.relation_master_table = 'device' AND typert.relation_slave_table = 'device_type' LEFT JOIN device_type dt ON dt.id = typert.relation_slave_id LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = s.id AND imgrt.relation_master_table = 'device' AND imgrt.relation_slave_table = 'img_name',`user` u WHERE u.id = s.user_id AND u.state = 1";
        Map<String, Object> params = new HashMap<String, Object>();
        if (!CommonUtil.isEmpty(conditions.getState())) {
            sql += " AND s.state=:state";
            params.put("state", conditions.getState());
        }
        else {
            sql += " AND s.state = 1";
        }
        if (!CommonUtil.isEmpty(conditions.getKeyword())) {
            sql += " and s.device_name like CONCAT('%',:deviceName,'%')";
            params.put("deviceName", conditions.getKeyword());
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
        List<Map<String, Object>> list = deviceDao.findAllMapBySQL(sql, params);
        if (CommonUtil.isNotEmpty(conditions.getLat()) && CommonUtil.isNotEmpty(conditions.getLng())) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                map.put("distance", CommonUtil.getDistance(conditions.getLat(), conditions.getLng(), (float)map.get("lat"), (float)map.get("lng")));
            }
        }
        return list;
    }
    
    public List<Map<String, Object>> getHotDevice() {
        String sql = "SELECT s.id id,s.user_id userId,s.device_name deviceName,CONCAT_WS(' ',s.province_name,s.city_name,s.county_name) address,s.lng,s.lat,CAST(u.real_name_authentication AS CHAR) realNameAuthentication,CAST(u.enterprise_certification AS CHAR) enterpriseCertification,imgrt.img_name imgName";
        sql += " FROM device s LEFT JOIN relation_table typert ON typert.relation_master_id = s.id AND typert.relation_master_table = 'device' AND typert.relation_slave_table = 'device_type' LEFT JOIN device_type dt ON dt.id = typert.relation_slave_id LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = s.id AND imgrt.relation_master_table = 'device' AND imgrt.relation_slave_table = 'img_name',`user` u WHERE u.id = s.user_id AND u.state = 1 AND s.state = 1";
        Map<String, Object> params = new HashMap<String, Object>();
        sql += " GROUP BY s.id";
        sql += " order by s.create_time desc";
        sql += " limit 0,5";
        List<Map<String, Object>> list = deviceDao.findAllMapBySQL(sql, params);
        return list;
    }
    
    public int saveDevice(Device device) {
        List<String> pictures = device.getPictures();
        List<DeviceType> deviceTypes = device.getDeviceTypes();
        if (device.getSort() == null) {
            device.setSort(99);
        }
        if (device.getState() == (short)2 && settingsDao.getSettings().getIsCheckDevice() == (short)0) {
            device.setState((short)1);
        }
        device.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        device = deviceDao.save(device);
        if (device == null) {
            return 0;
        }
        relationTableDao.deleteRelationTable("device", device.getId());
        if (pictures != null) {
            for (Iterator iterator = pictures.iterator(); iterator.hasNext();) {
                String picture = (String) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("device");
                rt.setRelationMasterId(device.getId());
                rt.setRelationSlaveTable("img_name");
                rt.setImgName(picture);
                relationTableDao.save(rt);
            }
        }
        if (deviceTypes != null) {
            for (Iterator iterator = deviceTypes.iterator(); iterator.hasNext();) {
                DeviceType deviceType = (DeviceType) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("device");
                rt.setRelationMasterId(device.getId());
                rt.setRelationSlaveTable("device_type");
                rt.setRelationSlaveId(deviceType.getId());;
                relationTableDao.save(rt);
            }
        }
        return 1;
    }
    
    public int updateDeviceBv(int id) {
        return deviceDao.updateDeviceBv(id);
    }
    
    public int updateDeviceCt(int id) {
        return deviceDao.updateDeviceCt(id);
    }

    @Override
    public int updateDeviceState(int id, short state) {
        if (state == (short)2 && settingsDao.getSettings().getIsCheckDevice() == (short)0) {
            state = 1;
        }
        return deviceDao.updateDeviceState(id, state);
    }

}
