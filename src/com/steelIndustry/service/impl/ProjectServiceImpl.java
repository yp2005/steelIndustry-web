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
import com.steelIndustry.dao.ProjectDao;
import com.steelIndustry.dao.RelationTableDao;
import com.steelIndustry.dao.SettingsDao;
import com.steelIndustry.dao.UserDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.Project;
import com.steelIndustry.model.RelationTable;
import com.steelIndustry.model.User;
import com.steelIndustry.service.ProjectService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;

@Service("projectService")
public class ProjectServiceImpl extends DataServiceImpl<Project, Integer> implements ProjectService {

    @Resource
    private ProjectDao projectDao;

    @Resource
    private RelationTableDao relationTableDao;
    
    @Resource
    private SettingsDao settingsDao;
    
    @Resource
    private UserDao userDao;
    
    @Override
    public EntityJpaDao<Project, Integer> getRepository() {
        return projectDao;
    }

    @Override
    public Project getProjectById(int id) {
        Project project = projectDao.getProjectById(id);
        if (project != null) {
            String sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'project' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name'";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("relationMasterId", project.getId());
            List<Map<String, Object>> pictureList = projectDao.findAllMapBySQL(sql, params);
            List<String> pictures = new ArrayList();
            for (Iterator iterator = pictureList.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                pictures.add(CommonProperties.getInstance().getProperty("imgServer") + (String) map.get("imgName"));
            }
            project.setPictures(pictures);
            User user = userDao.getOne(project.getUserId());
            project.setRealNameAuthentication(user.getRealNameAuthentication());
            project.setEnterpriseCertification(user.getEnterpriseCertification());
        }
        return project;
    }

    public List<Map<String, Object>> getProjectList(Conditions conditions) {
        String sql = "SELECT p.id id,p.user_id userId,p.project_name projectName,p.create_time createTime,CONCAT_WS(' ',p.province_name,p.city_name,p.county_name) address,p.lng,p.lat,u.real_name_authentication realNameAuthentication,u.enterprise_certification enterpriseCertification,imgrt.img_name imgName";
        if (conditions.getSortType() != null && conditions.getSortType() == 2) {
            sql += ",(6378.138 * 2 * asin(sqrt(pow(sin((p.lat * pi() / 180 - " + conditions.getLat() + " * pi() / 180) / 2),2) + cos(p.lat * pi() / 180) * cos(" + conditions.getLat() + " * pi() / 180) * pow(sin((p.lng * pi() / 180 - " + conditions.getLng() + " * pi() / 180) / 2),2))) * 1000) distance";
        }
        sql += " FROM project p LEFT JOIN relation_table imgrt ON imgrt.relation_master_id = p.id AND imgrt.relation_master_table = 'project' AND imgrt.relation_slave_table = 'img_name',`user` u WHERE u.id = p.user_id AND u.state = 1 AND p.due_time > CURRENT_TIMESTAMP";
        Map<String, Object> params = new HashMap<String, Object>();
        if (!CommonUtil.isEmpty(conditions.getState())) {
            sql += " AND p.state=:state";
            params.put("state", conditions.getState());
        }
        else {
            sql += " AND p.state = 1";
        }
        if (!CommonUtil.isEmpty(conditions.getKeyword())) {
            sql += " and p.project_name like CONCAT('%',:projectName,'%')";
            params.put("projectName", conditions.getKeyword());
        }
        if (conditions.getProvinceId() != null && conditions.getProvinceId() != -1) {
            sql += " and p.province_id = :provinceId";
            params.put("provinceId", conditions.getProvinceId());
        }
        if (conditions.getCityId() != null && conditions.getCityId() != -1) {
            sql += " and p.city_id = :cityId";
            params.put("cityId", conditions.getCityId());
        }
        if (conditions.getCountyId() != null && conditions.getCountyId() != -1) {
            sql += " and p.county_id = :countyId";
            params.put("countyId", conditions.getCountyId());
        }
        sql += " GROUP BY p.id";
        if (conditions.getSortType() != null) {
            if (conditions.getSortType() == 0) {
                sql += " order by p.sort asc";
            } else if (conditions.getSortType() == 1) {
                sql += " order by p.create_time desc";
            } else if (conditions.getSortType() == 2) {
                sql += " order by distance asc";
            }
        }
        sql += " limit " + (conditions.getRowStartNumber() == null ? 0
                : conditions.getRowStartNumber()) + "," + (conditions.getRowCount() == null ? 10
                        : conditions.getRowCount());
        List<Map<String, Object>> list = projectDao.findAllMapBySQL(sql, params);
        if (CommonUtil.isNotEmpty(conditions.getLat()) && CommonUtil.isNotEmpty(conditions.getLng())) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                map.put("distance", CommonUtil.getDistance(conditions.getLat(), conditions.getLng(), (float)map.get("lat"), (float)map.get("lng")));
            }
        }
        return list;
    }
    
    public List<Project> getUserProject(User user) {
        List<Project> projects = projectDao.getUserProject(user.getId());
        if (projects != null && projects.size() > 0) {
            for (Iterator iterator = projects.iterator(); iterator.hasNext();) {
                Project project = (Project) iterator.next();
                String sql = "select img_name as imgName from relation_table t where t.relation_master_table = 'project' and t.relation_master_id = :relationMasterId and t.relation_slave_table='img_name'";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("relationMasterId", project.getId());
                List<Map<String, Object>> pictureList = projectDao.findAllMapBySQL(sql, params);
                List<String> pictures = new ArrayList();
                for (Iterator iterator1 = pictureList.iterator(); iterator1.hasNext();) {
                    Map<String, Object> map = (Map<String, Object>) iterator1.next();
                    pictures.add(CommonProperties.getInstance().getProperty("imgServer") + (String) map.get("imgName"));
                }
                project.setPictures(pictures);
                project.setImgServer(CommonProperties.getInstance().getProperty("imgServer"));
                project.setRealNameAuthentication(user.getRealNameAuthentication());
                project.setEnterpriseCertification(user.getEnterpriseCertification());
            }
        }
        return projects;
    }

    public int saveProject(Project project) {
        List<String> pictures = project.getPictures();
        if (project.getSort() == null) {
            project.setSort(99);
        }
        if (project.getState() == (short)2 && settingsDao.getSettings().getIsCheckProject() == (short)0) {
            project.setState((short)1);
        }
        project.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        project = projectDao.save(project);
        if (project == null) {
            return 0;
        }
        relationTableDao.deleteRelationTable("project", project.getId());
        if (pictures != null) {
            for (Iterator iterator = pictures.iterator(); iterator.hasNext();) {
                String picture = (String) iterator.next();
                RelationTable rt = new RelationTable();
                rt.setRelationMasterTable("project");
                rt.setRelationMasterId(project.getId());
                rt.setRelationSlaveTable("img_name");
                rt.setImgName(picture);
                relationTableDao.save(rt);
            }
        }
        return 1;
    }
    
    public int updateProjectBv(int id) {
        return projectDao.updateProjectBv(id);
    }
    
    public int updateProjectCt(int id) {
        return projectDao.updateProjectCt(id);
    }

    @Override
    public int updateProjectState(int id, short state) {
        if (state == (short)2 && settingsDao.getSettings().getIsCheckProject() == (short)0) {
            state = 1;
        }
        return projectDao.updateProjectState(id, state);
    }

}
