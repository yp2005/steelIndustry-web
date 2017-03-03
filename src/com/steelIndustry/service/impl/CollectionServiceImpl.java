package com.steelIndustry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steelIndustry.dao.CollectionDao;
import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.framework.service.impl.DataServiceImpl;
import com.steelIndustry.model.Collection;
import com.steelIndustry.model.EmploymentDemand;
import com.steelIndustry.model.MasterCard;
import com.steelIndustry.model.Project;
import com.steelIndustry.model.Store;
import com.steelIndustry.service.CollectionService;

@Service("collectionService")
public class CollectionServiceImpl extends DataServiceImpl<Collection, Integer> implements CollectionService {

    @Resource
    private CollectionDao collectionDao;

    @Override
    public EntityJpaDao<Collection, Integer> getRepository() {
        return collectionDao;
    }

    public List<MasterCard> getMasterCards(int userId) {
        return collectionDao.getMasterCards(userId);
    }

    public List<EmploymentDemand> getEmploymentDemands(int userId) {
        return collectionDao.getEmploymentDemands(userId);
    }

    public List<Project> getProjects(int userId) {
        return collectionDao.getProjects(userId);
    }

    public List<Store> getStores(int userId) {
        return collectionDao.getStores(userId);
    }

    public int delCollection(int userId, String type, int collectId) {
        return collectionDao.delCollection(userId, type, collectId);
    }
    
    public Collection addCollection(int userId, String type, int collectId) {
        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setType(type);
        collection.setCollectId(collectId);
        return collectionDao.save(collection);
    }

}
