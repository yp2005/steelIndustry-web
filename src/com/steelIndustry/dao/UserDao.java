package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.User;

@Repository
public interface UserDao extends EntityJpaDao<User, Integer> {
    @Query("select t from User t where t.accessToken=:accessToken")
    public User getUserByAccessToken(@Param("accessToken") String accessToken);
    
    @Query("select t from User t where t.mobileNumber=:mobileNumber")
    public User getUserByMobileNumber(@Param("mobileNumber") long mobileNumber);
}