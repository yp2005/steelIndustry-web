package com.steelIndustry.dao;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Modifying;
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
    
    @Modifying
    @Query("update User set latestLoginTime=:latestLoginTime where id=:id ")
    public int updateLatestLoginTime(@Param("id") int id, @Param("latestLoginTime") Timestamp latestLoginTime);
    
    @Modifying
    @Query("update User set state=:state where id=:id")
    public int updateUserState(@Param("id") int id, @Param("state") short state);
    
    @Modifying
    @Query("update User set isShared=:shareState where id=:userId")
    public int updateShareState(@Param("userId") int userId, @Param("shareState") short shareState);
    
    @Modifying
    @Query("update User set realNameAuthentication=:state where id=:id")
    public int updateUserRnaState(@Param("id") int id, @Param("state") short state);
    
    @Modifying
    @Query("update User set enterpriseCertification=:state where id=:id")
    public int updateUserEcState(@Param("id") int id, @Param("state") short state);
}